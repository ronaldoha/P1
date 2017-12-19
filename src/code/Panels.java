package code;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

class Panels extends AnchorPane {

    ImageView myImageView;

    public double mouseX, mouseY, deltaX, deltaY, posX, posY;
    public Panels() {
        this.setStyle("-fx-background-color:#28559c");
        this.setWidth(200);
        this.setHeight(100);

        Button btnLoad = new Button("Load");
        btnLoad.setOnAction(t ->{
            FileChooser fileChooser = new FileChooser();

            myImageView = new ImageView();
            myImageView.setFitWidth(50);
            myImageView.setFitHeight(50);


            //Set extension filter
            FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
            fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

            //Show open file dialog
            File file = fileChooser.showOpenDialog(null);

            try {
                BufferedImage bufferedImage = ImageIO.read(file);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                myImageView.setImage(image);
            } catch (IOException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }

            this.getChildren().addAll(myImageView);
        });

        this.getChildren().add(btnLoad);

        setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.SECONDARY){
                    setVisible(false);
            }
        });


    }


}