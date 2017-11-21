package sample;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.*;

public class Main extends Application {

    //SCENE

    Stage window1;
    Button buttonWorld, buttonSettings, buttonExit, buttonBack, mainMenu, buttonExit2;
    Scene scene1, scene2, scene3;
    CheckBox buttonSetFS;
    Slider buttonSetVol;
    Label label;

    @Override
    public void start(Stage primaryStage){

        Image menu1 = new Image("http://ontheworldmap.com/world/world-political-map-with-countries.jpg"); // change URL for actual img.

        BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO,
                true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(menu1, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
        Background background = new Background(backgroundImage);

        window1 = primaryStage;
        window1.setTitle("Journally");
        window1.setResizable(false);

        //SCENE1

        VBox centralMenu = new VBox();

        buttonWorld = new Button("World");
        buttonWorld.setOnAction(e ->{
            primaryStage.setScene(scene3);
        });

        buttonSettings = new Button("Settings");
        buttonSettings.setOnAction(e -> {
            window1.setScene(scene2);
        });

        buttonExit = new Button("Exit");
        buttonExit.setOnAction(e -> {
            boolean result = ConfirmBox.display("Exit", "Are you sure You want to Exit?");
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

        scene2 = new Scene(vBoxSettings, 996, 499);

        //SCENE3

        AnchorPane anchorPane = new AnchorPane();
        BorderPane borderPane = new BorderPane();

        VBox vBoxBar = new VBox(){
            @Override
            protected void updateBounds() {
                super.updateBounds();
                super.setPrefWidth(100);
                super.setStyle("-fx-background-color:black");
                super.setSpacing(100);
            }
        };

        mainMenu = new Button("Main Menu"){
            @Override
            protected void setHeight(double value) {

                super.setHeight(100);
                super.setWidth(100);
                super.setStyle("-fx-background-color:red");
                super.setOnAction(e -> window1.setScene(scene1));
            }
        };

        buttonExit2 = new Button("Exit"){
            @Override
            protected void setHeight(double value) {
                super.setHeight(100);
                super.setWidth(100);
                super.setStyle("-fx-background-color:white");
                super.setOnAction( event -> {
                    boolean result = ConfirmBox.display("Exit", "Are you sure You want to Exit?");
                    if(result == true) {
                        window1.close();
                    }
                });
            }
        };



        anchorPane.getChildren().addAll(borderPane);
        borderPane.setRight(vBoxBar);
        borderPane.setPrefSize(996,499);
        vBoxBar.getChildren().addAll(mainMenu, buttonExit2);

        scene3 = new Scene(anchorPane, 996, 499);

        window1.setScene(scene1);
        window1.show();



    }



    public static void main(String[] args) {
        launch(args);
    }
}

