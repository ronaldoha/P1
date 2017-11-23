package sample;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.*;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;


public class Main extends Application {

      //MUSIC

        String musicFile = "BGMusic.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();

        //LOOP

        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    
    //SCENE

    Stage window1;
    Button buttonWorld, buttonSettings, buttonExit, buttonBack, mainMenu, buttonExit2;
    Scene scene1, scene2, scene3;
    CheckBox buttonSetFS;
    Slider buttonSetVol;
    Label label;

    @Override
    public void start(Stage primaryStage){

        //STAGE 1

        window1 = primaryStage;
        window1.setTitle("Journally");
        window1.setResizable(false);

        //SCENE1 (MAIN MENU)
        scene1 = new Scene(s1CentralMenu(), 996, 499);


        //SCENE 2 (SETTINGS)

        scene2 = new Scene(s2CentralMenu(), 996, 499);

        //SCENE3 (WORLD)

        AnchorPane anchorPane = new AnchorPane();
        BorderPane borderPane = new BorderPane();

        anchorPane.getChildren().addAll(borderPane);
        borderPane.setRight(sidePane());
        borderPane.setPrefSize(996,499);

        scene3 = new Scene(anchorPane, 996, 499);

        //STAGE 1 INITIAL SETTINGS
        window1.setScene(scene1);
        window1.show();
    }

    //BUTTONS WORLD SIDE BAR STYLE
    private static final String IDLE_BUTTON_STYLE = "-fx-background-color: transparent; -fx-text-fill: #ffffff";
    private static final String HOVERED_BUTTON_STYLE = "-fx-background-color:#4682b4; -fx-shadow-highlight-color: none;" +
            "-fx-outer-border: none; -fx-inner-border: none;  -fx-text-fill: #ffffff";

    //CENTRAL MENU (SCENE 1)
    private VBox s1CentralMenu(){

        Image menu1 = new Image("http://ontheworldmap.com/world/world-political-map-with-countries.jpg"); // change URL for actual img.


        //BACKGROUND SETTINGS
        BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO,
                true, true, true, false);

        BackgroundImage backgroundImage = new BackgroundImage(menu1, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);

        Background background = new Background(backgroundImage);

        //VBOX CENTRAL MENU
        VBox vBoxMenu = new VBox();
        buttonWorld = new Button("World");
        buttonWorld.setOnAction(e ->{
            window1.setScene(scene3);
        });

        buttonSettings = new Button("Settings");
        buttonSettings.setOnAction(e -> window1.setScene(scene2));

        buttonExit = new Button("Exit");
        buttonExit.setOnAction(e -> {
            boolean result = ConfirmBox.display("Exit", "Are you sure You want to Exit?");
            if (result == true) {
                window1.close();
            }
        });

        vBoxMenu.setBackground(background);
        vBoxMenu.setSpacing(20);
        vBoxMenu.setAlignment(Pos.CENTER);
        vBoxMenu.getChildren().addAll(buttonWorld, buttonSettings, buttonExit);

     return vBoxMenu;
    }

    //CENTRAL MENU (SCENE 2)
    private VBox s2CentralMenu(){

        VBox vBoxSettings = new VBox();

        buttonSetFS = new CheckBox("Full Screen");
        buttonSetFS.setOnAction(e -> {
            window1.setFullScreen(true);
        });

        label = new Label("Volume");

        buttonSetVol = new Slider(0, 10, 1);
        buttonSetVol.setMaxSize(200,10);

        buttonBack = new Button("BACK");
        buttonBack.setOnAction(event -> {
            window1.setScene(scene1);
        });

        vBoxSettings.setSpacing(20);
        vBoxSettings.setAlignment(Pos.CENTER);
        vBoxSettings.getChildren().addAll(label, buttonSetVol, buttonSetFS, buttonBack);
        return vBoxSettings;
    }

    //SIDE BAR IN WORLD (SCENE 3)
    private VBox sidePane(){
        VBox vBoxBar = new VBox(){
            @Override
            protected void updateBounds() {
                super.updateBounds();
                super.setStyle("-fx-background-color:#28559c");
                super.setSpacing(15);
                super.setAlignment(Pos.TOP_CENTER);
            }
        };

        mainMenu = new Button("Main Menu"){
            @Override
            protected void setHeight(double value) {
                super.setWidth(100);
                super.setHeight(40);
                super.setOnMouseEntered(e -> super.setStyle(HOVERED_BUTTON_STYLE));
                super.setOnMouseExited(e -> super.setStyle(IDLE_BUTTON_STYLE));
                super.setOnAction(e -> window1.setScene(scene1));

            }
        };
        buttonExit2 = new Button("Exit"){
            @Override
            protected void setHeight(double value) {
                super.setWidth(100);
                super.setHeight(40);
                super.setOnMouseEntered(e -> super.setStyle(HOVERED_BUTTON_STYLE));
                super.setOnMouseExited(e -> super.setStyle("-fx-background-color: transparent; -fx-text-fill: #ffffff"));
                super.setOnAction(e -> {
                    boolean result = ConfirmBox.display("Exit", "Are you sure You want to Exit?");
                    if(result == true) {
                        window1.close();
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

    public static void main(String[] args) {
        launch(args);
    }
}

