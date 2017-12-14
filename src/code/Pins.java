package code;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

class Pins extends Button {
    public double mouseX, mouseY, deltaX, deltaY, posX, posY;

    public Pins() {
        Image image = new Image(getClass().getResourceAsStream("/resources/map-pin.png"));
        setGraphic(new ImageView(image));
        setStyle("-fx-background-color: transparent");

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

        setOnMouseClicked( e ->{
            AnchorPane anchorPane = new Panels();
            if(e.getButton() == MouseButton.PRIMARY) {
                if (e.getClickCount() == 2) {

                    anchorPane.isResizable();
                    anchorPane.setTranslateY(e.getY() - 150);
                    anchorPane.setTranslateX(e.getX() - 90);

                    getChildren().add(anchorPane);
                    e.consume();
                }
            }
            if(e.getButton() == MouseButton.SECONDARY){
                setVisible(false);
            }
        });
    }
}
