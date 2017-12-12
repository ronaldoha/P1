package code;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserScene extends App
{

    Stage stage = new Stage();
    List<String> list = new ArrayList<>();
    Map<String, String> map = new HashMap<String, String>();
    User db = new User();

    public Scene display (String title, String message) {

        Scene scene1 = new Scene(display1());
        Scene scene;

        int integer = 0;


        String FileName = "Users.txt";

        // Stage information
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setMinHeight(700);
        stage.setMinWidth(500);
        Label label = new Label();
        label.setText(message);

        // grid creation
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));

        // Setting the scene
        scene = new Scene(grid, 700, 500);
        stage.setScene(scene);
        // Adding labels and text fields to the grid
        Label userName = new Label("User Name: ");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1,1);

        Label pw = new Label("Password: ");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        // Buttons
        Button buttonNewUser = new Button("New User");
        Button buttonLogIn = new Button("Log in");
        HBox hbButton = new HBox(10);
        hbButton.setAlignment(Pos.BOTTOM_RIGHT);
        hbButton.getChildren().addAll(buttonNewUser, buttonLogIn);
        grid.add(hbButton, 1, 4);

        // Add alert text
        Text alertText = new Text();
        grid.add(alertText, 1,6);

        // Adding code to buttons
        buttonNewUser.setOnAction(e -> {
            stage.setScene(scene1);
        });

        buttonLogIn.setOnAction(e -> {
            // Read object
            try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(FileName))){
                boolean a = false;
                for (int i = 0; i < list.size(); i++)
                {
                    if (userTextField.getText().equals(db.getMap().get(i)) || pwBox.getText().equals(db.getMap().get(i)))
                    {
                        db = (User) objectInputStream.readObject();
                        a = true;
                    }
                    if (a == true)
                    {
                        break;
                    }
                }
                if (a == false)
                {
                    Label label2 = new Label("The password or username is incorrect");
                    grid.add(label2, 1, 1);
                }

            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            System.out.println(db.getList());
        });

        stage.showAndWait();
        return scene;
    }
    public GridPane display1()
    {
        String FileName = "Users.txt";

        // grid creation
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));

        //Button
        Button buttonSignIn = new Button("Sign in");
        HBox hbButton = new HBox(10);
        hbButton.setAlignment(Pos.BOTTOM_RIGHT);
        hbButton.getChildren().addAll(buttonSignIn);
        grid.add(hbButton, 1, 4);

        // Adding labels and text fields to the grid
        Label userName = new Label("User Name: ");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1,1);

        Label pw = new Label("Password: ");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        Label pwagain = new Label("Repeat Password: ");
        grid.add(pwagain, 0, 2);

        PasswordField pwBoxagain = new PasswordField();
        grid.add(pwBoxagain, 1, 2);

        buttonSignIn.setOnAction(e -> {
            if (pwBox.getText().equals(pwagain.getText()))
            {
                list.add(userTextField.getText());
                map.put(pwBox.getText(), userTextField.getText());
                db.setList(list);
                db.setMap(map);
                try
                {
                    // Save object
                    try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(FileName))){
                        objectOutputStream.writeObject(db);
                    }
                    db = null;

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        stage.showAndWait();
        return grid;
    }
}
