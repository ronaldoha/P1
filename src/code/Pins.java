package code;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


class Pins extends Button {
    private double mouseX, mouseY, deltaX, deltaY, posX, posY;

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

    }
}
