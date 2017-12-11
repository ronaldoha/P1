package code;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import resources.Sound;
import javafx.scene.control.ScrollPane;
import java.util.ArrayList;


public class App extends Application {
    Group zoomGroup;
    Scale scaleTransform;
    Node content;
   //COUNTRY ENTRIES
    public static ArrayList<String> countryNameList = new ArrayList<>();
    public static ArrayList<Country> countryList = new ArrayList<>();

    //GUI Options
    private boolean fullscreen = false;
    private double volume = 1;

    //Persistent GUI controls
    private Stage window1;
    private Scene scene1, scene2, scene3;

    //Sound controls
    private Sound clickSound = new Sound("ClickSound.wav");
    private Sound backgroundSound = new Sound("BackgroundMusic.mp3");

    @Override
    public void start(Stage primaryStage) {

        Parser parser = new Parser();
        countryList = parser.getObjects();
        countryNameList = parser.getNames();

        //STAGE 1
        window1 = primaryStage;
        window1.setTitle("Journally");
        window1.setResizable(false);

        //SCENE1 (MAIN MENU)
        scene1 = new Scene(s1CentralMenu(), 800, 466);

        //SCENE 2 (SETTINGS)
        scene2 = new Scene(s2CentralMenu(), 800, 466);

        //SCENE3 (WORLD)
        scene3 = new Scene(s3Pane(), 800, 466);

        //STAGE 1 INITIAL SETTINGS
        window1.setScene(scene1);
        window1.show();

        backgroundSound.play();
    }

    //BUTTON SETTINGS FOR "WORLD" SIDE BAR STYLE
    private static final String IDLE_BUTTON_STYLE = "-fx-background-color: transparent; -fx-text-fill: #ffffff";
    private static final String HOVERED_BUTTON_STYLE = "-fx-background-color:#4682b4; -fx-shadow-highlight-color: none;" +
            "-fx-outer-border: none; -fx-inner-border: none;  -fx-text-fill: #ffffff; -fx-outer-CornerRadii: none";

    //CENTRAL MENU (SCENE 1)
    private VBox s1CentralMenu() {

        //VBOX CENTRAL MENU
        VBox vBoxMenu = new VBox();
        Button buttonWorld = new Button("World");
        buttonWorld.setOnAction(e -> {
            clickSound.play();
            window1.setScene(scene3);
            window1.setFullScreen(fullscreen);
        });

        Button buttonSettings = new Button("Settings");
        buttonSettings.setOnAction(e -> {
            clickSound.play();
            window1.setScene(scene2);
            window1.setFullScreen(fullscreen);
        });

        Button buttonExit = new Button("Exit");
        buttonExit.setOnAction(e -> {
            clickSound.play();
            boolean result = ConfirmBox.display("Exit", "Are you sure You want to Exit?");
            if (result) window1.close();
        });

        vBoxMenu.setBackground(background());
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
            fullscreen = ((CheckBox) e.getSource()).isSelected();
            window1.setFullScreen(fullscreen);
            clickSound.play();

        });

        Label label = new Label("Volume");

        Slider buttonSetVol = new Slider(0, 1, 1);
        buttonSetVol.setMaxSize(200, 10);

        buttonSetVol.valueProperty().addListener((observable, oldValue, newValue) -> {
            volume = newValue.doubleValue();
            backgroundSound.setVolume(volume);
            clickSound.setVolume(volume);
        });

        Button buttonBack = new Button("BACK");
        buttonBack.setOnAction(event -> {
            window1.setScene(scene1);
            window1.setFullScreen(fullscreen);
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
                    window1.setFullScreen(fullscreen);
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
        AutoCompleteTextField autoCompleteTextField = new AutoCompleteTextField();
        vBoxBar.getChildren().addAll(mainMenu, buttonExit2, autoCompleteTextField);

        autoCompleteTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            int id = countryNameList.indexOf(newValue);
            if (id > -1) {
                Country country = countryList.get(id);
                System.out.println(country.getName() + ": " + country.getX() + " | " + country.getY());

                // ZOOM IN CODE GOES HERE
            }
        });

        return vBoxBar;
    }

    //PANE (SCENE 3)
    private AnchorPane s3Pane() {
        AnchorPane anchorPane = new AnchorPane();
        BorderPane borderPane = new BorderPane();

        anchorPane.getChildren().addAll(borderPane);
        borderPane.setRight(sidePane());
        borderPane.setLeft(world());
        borderPane.setPrefSize(800, 466);

        return anchorPane;
    }

    //BACKGROUNDS
    private Background background() {

        Image image = new Image(App.class.getResource("/resources/Back.jpg").toExternalForm()); // change URL for actual img.

        //BACKGROUND SETTINGS
        BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO,
                true, true, true, false);

        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);

        Background background = new Background(backgroundImage);

        return background;
    }

    //WORLD :DDDDD

    private ScrollPane world() {

        StackPane layout = new StackPane();

        Image backgroundImage = new Image(App.class.getResource("/resources/Europe.png").toExternalForm());

        ScrollPane scroll = createScrollPane(layout);

        scroll.setHvalue(scroll.getHmin() + (scroll.getHmax() - scroll.getHmin()) / 2);
        scroll.setVvalue(scroll.getVmin() + (scroll.getVmax() - scroll.getVmin()) / 2);

        layout.getChildren().setAll(new ImageView(backgroundImage));

        return scroll;
    }

    private ScrollPane createScrollPane(Pane layout) {

        ScrollPane scroll = new ScrollPane();
        scroll.setPannable(true);
        scroll.setPrefSize(700, 466);
        scroll.setContent(layout);

        return scroll;
    }
    private ScrollPane zoomableScrollPane(Node content) {

        ScrollPane scroll = new ScrollPane();
        this.content = content;
        Group contentGroup = new Group();
        zoomGroup = new Group();
        contentGroup.getChildren().add(zoomGroup);
        zoomGroup.getChildren().add(content);
        scroll.setContent(contentGroup);
        scaleTransform = new Scale(1, 1, 0, 0);
        zoomGroup.getTransforms().add(scaleTransform);

        return scroll;
    }

    public static void main(String[] args) {
        launch(args);
    }
}