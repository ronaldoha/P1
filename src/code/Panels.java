package code;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;

class Panels extends HBox {

    ImageView myImageView;

    public double mouseX, mouseY, deltaX, deltaY, posX, posY;
    public Panels() {

        this.setStyle("-fx-background-color:#28559c");
        this.setPrefHeight(200);
        this.setPrefWidth(200);


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

        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
            toFront();
        });
        setOnMouseDragged(e -> {
            deltaX = e.getSceneX() - mouseX + posX;
            deltaY = e.getSceneY() - mouseY + posY;
            setLayoutX(deltaX);
            setLayoutY(deltaY);
        });
        setOnMouseReleased((MouseEvent event) -> {
            posX = getLayoutX();
            posY = getLayoutY();
        });

        this.getChildren().add(btnLoad);
    }
}