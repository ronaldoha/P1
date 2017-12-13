package code;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.ScrollEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import resources.Sound;
import javafx.scene.control.ScrollPane;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class App extends Application {

    // USER INTERFACE controls
    private static List<String> list = new ArrayList<>();
    private static Map<String, String> map = new HashMap<String, String>();
    private User db = new User();

    //zoom

    Group zoomGroup;
    Node content;

   //COUNTRY ENTRIES
    public static ArrayList<String> countryNameList = new ArrayList<>();
    public static ArrayList<Country> countryList = new ArrayList<>();

    //GUI Options
    private boolean fullscreen = false;
    private double volume = 1;

    //Persistent GUI controls
    private Stage window1, loginWindow;
    private Scene scene1, scene2, scene3, sceneLogin, sceneLogin1;

    //Sound controls
    private Sound clickSound = new Sound("ClickSound.wav");
    private Sound backgroundSound = new Sound("BackgroundMusic.mp3");

    @Override
    public void start(Stage primaryStage) {

        Parser parser = new Parser();
        countryList = parser.getObjects();
        countryNameList = parser.getNames();

        //STAGE 1
        window1 = primaryStage;
        window1.setTitle("Journally");
        window1.setResizable(false);

        //STAGE LOGIN

        loginWindow = new Stage();
        loginWindow.setTitle("Log in");
        loginWindow.setResizable(false);

        //SCENE1 (MAIN MENU)
        scene1 = new Scene(s1CentralMenu(), 800, 466);

        //SCENE 2 (SETTINGS)
        scene2 = new Scene(s2CentralMenu(), 800, 466);

        //SCENE3 (WORLD)
        scene3 = new Scene(s3Pane(), 800, 466);

        //SCENE LOGIN
        sceneLogin1 = new Scene(display1());
        //SCENE LOGIN 1
        sceneLogin = new Scene(display2(), 700, 500);

        //STAGE 1 INITIAL SETTINGS
        window1.setScene(scene1);
        window1.show();

        //Stage login
        loginWindow.setScene(sceneLogin);
        loginWindow.initModality(Modality.APPLICATION_MODAL);
        loginWindow.setMinHeight(700);
        loginWindow.setMinWidth(500);
        Label label = new Label();
        label.setText("message");

        backgroundSound.play();
    }

    //BUTTON SETTINGS FOR "WORLD" SIDE BAR STYLE
    private static final String IDLE_BUTTON_STYLE = "-fx-background-color: transparent; -fx-text-fill: #ffffff";
    private static final String HOVERED_BUTTON_STYLE = "-fx-background-color:#4682b4; -fx-shadow-highlight-color: none;" +
            "-fx-outer-border: none; -fx-inner-border: none;  -fx-text-fill: #ffffff; -fx-outer-CornerRadii: none";

    //CENTRAL MENU (SCENE 1)
    private VBox s1CentralMenu() {

        //VBOX CENTRAL MENU
        VBox vBoxMenu = new VBox();
        Button buttonWorld = new Button("World");
        buttonWorld.setOnAction(e -> {
            clickSound.play();
            window1.setScene(scene3);
            window1.setFullScreen(fullscreen);
        });

        Button buttonSettings = new Button("Settings");
        buttonSettings.setOnAction(e -> {
            clickSound.play();
            window1.setScene(scene2);
            window1.setFullScreen(fullscreen);
        });

        Button buttonExit = new Button("Exit");
        buttonExit.setOnAction(e -> {
            clickSound.play();
            boolean result = ConfirmBox.display("Exit", "Are you sure You want to Exit?");
            if (result) window1.close();
        });

        Button users = new Button("User");
        users.setOnAction(e-> {
            loginWindow.show();
        });
        vBoxMenu.setBackground(background());
        vBoxMenu.setSpacing(20);
        vBoxMenu.setAlignment(Pos.CENTER);
        vBoxMenu.getChildren().addAll(buttonWorld, buttonSettings, buttonExit,users);

        return vBoxMenu;
    }

    //CENTRAL MENU (SCENE 2)
    private VBox s2CentralMenu() {

        VBox vBoxSettings = new VBox();

        CheckBox buttonSetFS = new CheckBox("Full Screen");
        buttonSetFS.setOnAction(e -> {
            fullscreen = ((CheckBox) e.getSource()).isSelected();
            window1.setFullScreen(fullscreen);
            clickSound.play();

        });

        Label label = new Label("Volume");

        Slider buttonSetVol = new Slider(0, 1, 1);
        buttonSetVol.setMaxSize(200, 10);

        buttonSetVol.valueProperty().addListener((observable, oldValue, newValue) -> {
            volume = newValue.doubleValue();
            backgroundSound.setVolume(volume);
            clickSound.setVolume(volume);
        });

        Button buttonBack = new Button("BACK");
        buttonBack.setOnAction(event -> {
            window1.setScene(scene1);
            window1.setFullScreen(fullscreen);
            clickSound.play();
        });

        Button user = new Button("Users");
        user.setOnAction(e->{
            loginWindow.show();
        });

        vBoxSettings.setSpacing(20);
        vBoxSettings.setAlignment(Pos.CENTER);
        vBoxSettings.getChildren().addAll(label, buttonSetVol, buttonSetFS, buttonBack);
        return vBoxSettings;
    }

    //SIDE BAR IN WORLD (SCENE 3)
    private VBox sidePane() {

        VBox vBoxBar = new VBox() {
            @Override
            protected void updateBounds() {
                super.updateBounds();
                super.setStyle("-fx-background-color:#28559c");
                super.setSpacing(15);
                super.setAlignment(Pos.TOP_CENTER);
            }
        };
        Button mainMenu = new Button("App Menu") {
            @Override
            protected void setHeight(double value) {
                super.setWidth(100);
                super.setHeight(40);
                super.setOnMouseEntered(e -> super.setStyle(HOVERED_BUTTON_STYLE));
                super.setOnMouseExited(e -> super.setStyle(IDLE_BUTTON_STYLE));
                super.setOnAction(e -> {
                    window1.setScene(scene1);
                    window1.setFullScreen(fullscreen);
                    clickSound.play();
                });
            }
        };
        Button buttonExit2 = new Button("Exit") {
            @Override
            protected void setHeight(double value) {
                super.setWidth(100);
                super.setHeight(40);
                super.setOnMouseEntered(e -> super.setStyle(HOVERED_BUTTON_STYLE));
                super.setOnMouseExited(e -> super.setStyle("-fx-background-color: transparent; -fx-text-fill: #ffffff"));
                super.setOnAction(e -> {
                    boolean result = ConfirmBox.display("Exit", "Are you sure You want to Exit?");
                    if (result) {
                        window1.close();
                        clickSound.play();
                    }
                });
            }
        };
        Image image = new Image(App.class.getResource("/resources/Logo.png").toExternalForm());
        ImageView imageView= new ImageView(image);

        imageView.setFitHeight(100);
        imageView.setFitWidth(100);

        buttonExit2.setStyle(IDLE_BUTTON_STYLE);
        mainMenu.setStyle(IDLE_BUTTON_STYLE);

        vBoxBar.setPrefWidth(100);

        AutoCompleteTextField autoCompleteTextField = new AutoCompleteTextField();
        vBoxBar.getChildren().addAll(imageView, mainMenu, buttonExit2, autoCompleteTextField);

        autoCompleteTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            int id = countryNameList.indexOf(newValue);
            if (id > -1) {
                Country country = countryList.get(id);
                System.out.println(country.getName() + ": " + country.getX() + " | " + country.getY());
            }
        });

        return vBoxBar;
    }

    //PANE (SCENE 3)
    private AnchorPane s3Pane() {
        AnchorPane anchorPane = new AnchorPane();
        BorderPane borderPane = new BorderPane();

        anchorPane.getChildren().addAll(borderPane);
        borderPane.setRight(sidePane());
        borderPane.setLeft(world());
        borderPane.setPrefSize(800, 466);

        return anchorPane;
    }

    //BACKGROUNDS
    private Background background() {

        Image image = new Image(App.class.getResource("/resources/Back.jpg").toExternalForm()); // change URL for actual img.

        //BACKGROUND SETTINGS
        BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO,
                true, true, true, false);

        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.ROUND,
                BackgroundRepeat.ROUND, BackgroundPosition.DEFAULT, backgroundSize);

        return new Background(backgroundImage);
    }

    //WORLD MAP

    private ScrollPane world() {

        AnchorPane layout = new AnchorPane();
        Image backgroundImage = new Image(App.class.getResource("/resources/Europe.png").toExternalForm());

        layout.setOnMouseClicked(e ->{
            if(e.getButton() == MouseButton.PRIMARY) {

                if(e.getClickCount() == 2) {
                    layout.getChildren().addAll(new Pins());
                }
            }

         });

        layout.getChildren().setAll(new ImageView(backgroundImage));

        ScrollPane scroll = zoomableScrollPane(layout);

        scroll.setHvalue(scroll.getHmin() + (scroll.getHmax() - scroll.getHmin()) / 2);
        scroll.setVvalue(scroll.getVmin() + (scroll.getVmax() - scroll.getVmin()) / 2);

        return scroll;
    }

    private ScrollPane zoomableScrollPane(Node content) {

        final double SCALE_DELTA = 1.1;

        Group contentGroup = new Group();
        zoomGroup = new Group();
        contentGroup.getChildren().add(zoomGroup);

        ScrollPane scroll = new ScrollPane();
        scroll.setPrefSize(700, 466);
        scroll.setPannable(true);
        scroll.setContent(contentGroup);


        content.setOnScroll( (ScrollEvent event) -> {
            event.consume();
            if (event.getDeltaY() == 0) {
                return;
            }
            double scaleFactor = (event.getDeltaY() > 0) ? SCALE_DELTA : 1 / SCALE_DELTA;

            content.setScaleX(content.getScaleX() * scaleFactor);
            content.setScaleY(content.getScaleY() * scaleFactor);
        });

        zoomGroup.getChildren().add(content);

        return scroll;
    }

    /** USER INTERFACE */

    private GridPane display2() {

        int integer = 0;

        String FileName = "Users.txt";

        // grid creation

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));

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
            loginWindow.setScene(sceneLogin1);
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
        return grid;
    }


    private GridPane display1() {
        String FileName = "Users.txt";

        // grid creation
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

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
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password: ");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        Label pwagain = new Label("Repeat Password: ");
        grid.add(pwagain, 0, 3);

        PasswordField pwBoxagain = new PasswordField();
        grid.add(pwBoxagain, 1, 3);

        buttonSignIn.setOnAction(e -> {
            if (pwBox.getText().equals(pwagain.getText())) {
                list.add(userTextField.getText());
                map.put(pwBox.getText(), userTextField.getText());
                db.setList(list);
                db.setMap(map);
                try {
                    // Save object
                    try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(FileName))) {
                        objectOutputStream.writeObject(db);
                    }
                    db = null;

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        return grid;
    }

    public static void main(String[] args) {
        launch(args);
    }
}