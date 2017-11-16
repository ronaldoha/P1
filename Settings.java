package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.util.logging.Handler;

public class Settings extends Main {


    public static Scene start1() {

        String start1;
        VBox layout = new VBox();

        final Button buttonSetBack = new Button("BACK");

        CheckBox buttonSetFS = new CheckBox("Full Screen");
        Slider buttonSetVol = new Slider(0, 10, 1);//Volume

        layout.setSpacing(20);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(buttonSetBack, buttonSetFS, buttonSetVol);

        Scene scene2 = new Scene(layout, 200, 200);

        return scene2;
    }
}
