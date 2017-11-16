package sample;

import com.sun.tools.javadoc.Start;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {

    //SCENE

    Stage window1;
    Button button1, button2, button3;



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Image menu1 = new Image("http://ontheworldmap.com/world/world-political-map-with-countries.jpg"); // change URL for actual img.

        BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(menu1, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
        Background background = new Background(backgroundImage);

        window1 = primaryStage;
        window1.setTitle("Journally");


        VBox centralMenu = new VBox();

        button1 = new Button("World");
        button2 = new Button("Settings");
        button2.setOnAction(e -> {
            window1.setScene(Settings.start1());
        });
        button3 = new Button("Exit");

        //button1.setPrefSize(100, 20);
        //button2.setPrefSize(100, 20);
        //button3.setPrefSize(100, 20);

        button3.setOnAction(e -> {
            boolean result = ConfirmBox.display("Exit", "Are you sure do you want to Exit?");
            if (result == true) {
                window1.close();
            }
        });

        centralMenu.setSpacing(20);
        centralMenu.setBackground(background);
        centralMenu.setAlignment(Pos.CENTER);
        centralMenu.getChildren().addAll(button1, button2, button3);
        centralMenu.heightProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                double height = (double) newValue;
                button1.setPrefHeight(height / 20);
                button2.setPrefHeight(height / 20);
                button3.setPrefHeight(height / 20);
            }
        });
        centralMenu.widthProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                double width = (double) newValue;
                button1.setPrefWidth(width / 5);
                button2.setPrefWidth(width / 5);
                button3.setPrefWidth(width / 5);
            }
        });
        Scene scene1 = new Scene(centralMenu, 996, 499);
        Scene scene2;

        window1.setScene(scene1);
        window1.show();


        //SCENE 2


        //Scene scene2 = new Scene(scene2);

    }
}

