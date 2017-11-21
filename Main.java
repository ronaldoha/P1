package sample;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;


public class Main extends Application {

    //SCENE

    Stage window1;
    Button buttonWorld, buttonSettings, buttonExit, buttonBack;
    Scene scene1, scene2;
    CheckBox buttonSetFS;
    Slider buttonSetVol;

    @Override
    public void start(Stage primaryStage){

        Image menu1 = new Image("http://ontheworldmap.com/world/world-political-map-with-countries.jpg"); // change URL for actual img.

        BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(menu1, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
        Background background = new Background(backgroundImage);

        window1 = primaryStage;
        window1.setTitle("Journally");
        window1.setResizable(false);

        //SCENE1

        VBox centralMenu = new VBox();

        buttonWorld = new Button("World");

        buttonSettings = new Button("Settings");
        buttonSettings.setOnAction(e -> {
            window1.setScene(scene2);
        });

        buttonExit = new Button("Exit");
        buttonExit.setOnAction(e -> {
            boolean result = ConfirmBox.display("Exit", "Are you sure do you want to Exit?");
            if (result == true) {
                window1.close();
            }
        });

        centralMenu.setSpacing(20);
        centralMenu.setBackground(background);
        centralMenu.setAlignment(Pos.CENTER);
        centralMenu.getChildren().addAll(buttonWorld, buttonSettings, buttonExit);

        scene1 = new Scene(centralMenu, 996, 499);


        //SCENE 2

        VBox layout = new VBox();

        buttonBack = new Button("BACK");
        buttonBack.setOnAction(event -> {
            window1.setScene(scene1);
        });
        buttonSetFS = new CheckBox("Full Screen");

        buttonSetFS.setOnAction(e -> {
            window1.setFullScreen(true);
        });

        buttonSetVol = new Slider(0, 10, 1);

        layout.setSpacing(20);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(buttonBack, buttonSetFS, buttonSetVol);

        scene2 = new Scene(layout, 996, 499);

        window1.setScene(scene1);
        window1.show();

    }
    public static void main(String[] args) {
        launch(args);
    }
}

