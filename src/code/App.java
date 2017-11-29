package code;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import resources.Sound;

public class App extends Application {

    //GUI Options
    private boolean fullscreen = false;
    private double volume = 5;

    //Persistent GUI controls
    private Stage window1;
    private Scene scene1, scene2, scene3;

    //Sound controls
    private Sound clickSound = new Sound("ClickSound.wav");
    private Sound backgroundSound = new Sound("BackgroundMusic.mp3");

    @Override
    public void start(Stage primaryStage) {

        //STAGE 1
        window1 = primaryStage;
        window1.setTitle("Journally");
        window1.setResizable(false);

        //SCENE1 (MAIN MENU)
        scene1 = new Scene(s1CentralMenu(), 996, 499);

        //SCENE 2 (SETTINGS)
        scene2 = new Scene(s2CentralMenu(), 996, 499);

        //SCENE3 (WORLD)
        scene3 = new Scene(s3Pane(), 996, 499);

        //STAGE 1 INITIAL SETTINGS
        window1.setScene(scene1);
        window1.show();

        backgroundSound.play();
    }

    //BUTTONS WORLD SIDE BAR STYLE
    private static final String IDLE_BUTTON_STYLE = "-fx-background-color: transparent; -fx-text-fill: #ffffff";
    private static final String HOVERED_BUTTON_STYLE = "-fx-background-color:#4682b4; -fx-shadow-highlight-color: none;" +
            "-fx-outer-border: none; -fx-inner-border: none;  -fx-text-fill: #ffffff";

    //CENTRAL MENU (SCENE 1)
    private VBox s1CentralMenu() {
        Image menu1 = new Image("http://ontheworldmap.com/world/world-political-map-with-countries.jpg"); // change URL for actual img.

        //BACKGROUND SETTINGS
        BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO,
                true, true, true, false);

        BackgroundImage backgroundImage = new BackgroundImage(menu1, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);

        Background background = new Background(backgroundImage);

        //VBOX CENTRAL MENU
        VBox vBoxMenu = new VBox();
        Button buttonWorld = new Button("World");
        buttonWorld.setOnAction(e -> {
            clickSound.play();
            window1.setScene(scene3);
        });

        Button buttonSettings = new Button("Settings");
        buttonSettings.setOnAction(e -> {
            clickSound.play();
            window1.setScene(scene2);
        });

        Button buttonExit = new Button("Exit");
        buttonExit.setOnAction(e -> {
            clickSound.play();
            boolean result = ConfirmBox.display("Exit", "Are you sure You want to Exit?");
            if (result) window1.close();
        });

        vBoxMenu.setBackground(background);
        vBoxMenu.setSpacing(20);
        vBoxMenu.setAlignment(Pos.CENTER);
        vBoxMenu.getChildren().addAll(buttonWorld, buttonSettings, buttonExit);

        return vBoxMenu;
    }

    //CENTRAL MENU (SCENE 2)
    private VBox s2CentralMenu() {

        VBox vBoxSettings = new VBox();

        CheckBox buttonSetFS = new CheckBox("Full Screen");
        buttonSetFS.setOnAction(e -> {
            window1.setFullScreen(((CheckBox) e.getSource()).isSelected());
            clickSound.play();

        });

        Label label = new Label("Volume");

        Slider buttonSetVol = new Slider(0, 1, 1);
        buttonSetVol.setMaxSize(200, 10);

        buttonSetVol.valueProperty().addListener((observable, oldValue, newValue) -> {
            backgroundSound.setVolume(newValue.doubleValue());
            clickSound.setVolume(newValue.doubleValue());
        });

        Button buttonBack = new Button("BACK");
        buttonBack.setOnAction(event -> {
            window1.setScene(scene1);
            clickSound.play();
        });

        vBoxSettings.setSpacing(20);
        vBoxSettings.setAlignment(Pos.CENTER);
        vBoxSettings.getChildren().addAll(label, buttonSetVol, buttonSetFS, buttonBack);
        return vBoxSettings;
    }

    //SIDE BAR IN WORLD (SCENE 3)
    private VBox sidePane() {

        VBox vBoxBar = new VBox() {
            @Override
            protected void updateBounds() {
                super.updateBounds();
                super.setStyle("-fx-background-color:#28559c");
                super.setSpacing(15);
                super.setAlignment(Pos.TOP_CENTER);
            }
        };

        Button mainMenu = new Button("App Menu") {
            @Override
            protected void setHeight(double value) {
                super.setWidth(100);
                super.setHeight(40);
                super.setOnMouseEntered(e -> super.setStyle(HOVERED_BUTTON_STYLE));
                super.setOnMouseExited(e -> super.setStyle(IDLE_BUTTON_STYLE));
                super.setOnAction(e -> {
                    window1.setScene(scene1);
                    clickSound.play();
                });
            }
        };
        Button buttonExit2 = new Button("Exit") {
            @Override
            protected void setHeight(double value) {
                super.setWidth(100);
                super.setHeight(40);
                super.setOnMouseEntered(e -> super.setStyle(HOVERED_BUTTON_STYLE));
                super.setOnMouseExited(e -> super.setStyle("-fx-background-color: transparent; -fx-text-fill: #ffffff"));
                super.setOnAction(e -> {
                    boolean result = ConfirmBox.display("Exit", "Are you sure You want to Exit?");
                    if (result) {
                        window1.close();
                        clickSound.play();
                    }
                });
            }
        };
        buttonExit2.setStyle(IDLE_BUTTON_STYLE);
        mainMenu.setStyle(IDLE_BUTTON_STYLE);
        vBoxBar.setPrefWidth(100);
        vBoxBar.getChildren().addAll(mainMenu, buttonExit2);

        return vBoxBar;
    }

    //PANE (SCENE 3)
    private AnchorPane s3Pane() {
        AnchorPane anchorPane = new AnchorPane();
        BorderPane borderPane = new BorderPane();

        anchorPane.getChildren().addAll(borderPane);
        borderPane.setRight(sidePane());
        borderPane.setPrefSize(996, 499);

        return anchorPane;
    }

    public static void main(String[] args) {
        launch(args);
    }
}