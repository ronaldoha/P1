package code;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

class Panels extends AnchorPane {
    public double mouseX, mouseY, deltaX, deltaY, posX, posY;
    public Panels() {
        this.setStyle("-fx-background-color:#28559c");
        this.setWidth(100);
        this.setHeight(100);
        setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.SECONDARY){
                setVisible(false);
            }
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


    }
}