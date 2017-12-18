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
import javafx.scene.transform.Scale;
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
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class App extends Application {

    //Scroll
    private Group contentGroup = new Group();
    private ScrollPane scroll = new ScrollPane();

    //Properties file
    private Properties configFile = new Properties();
    private InputStream inputStream;

    // USER INTERFACE controls
    private static List<String> list = new ArrayList<>();
    private static Map<String, String> map = new HashMap<String, String>();

    //COUNTRY ENTRIES
    static ArrayList<String> countryNameList = new ArrayList<>();
    static ArrayList<Country> countryList = new ArrayList<>();

    //USERS
    static ArrayList<String> userNameList = new ArrayList<>();
    static ArrayList<User> userList = new ArrayList<>();

    Parser countryParser = new Parser();
    Parser userParser = new Parser();

    //GUI Options
    private boolean fullscreen = false;
    private double volume = 1;

    //Persistent GUI controls
    private Stage window1, loginWindow;
    private Scene scene1, scene2, scene3, sceneLogin, sceneSignIn;

    //Sound controls
    private Sound clickSound = new Sound("ClickSound.wav");
    private Sound backgroundSound = new Sound("BackgroundMusic.mp3");
    private Node content;

    //

    @Override
    public void start(Stage primaryStage) {


        countryParser.parseCountries();
        userParser.parseUsers();

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
        sceneSignIn = new Scene(signIn());
        //SCENE LOGIN 1
        sceneLogin = new Scene(logIn(), 700, 500);

        //STAGE 1 INITIAL SETTINGS
        window1.show();
        window1.setScene(scene1);

        //Stage login
        loginWindow.setScene(sceneLogin);
        loginWindow.initModality(Modality.APPLICATION_MODAL);
        loginWindow.setMinHeight(500);
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
        users.setOnAction(e-> loginWindow.show());

        vBoxMenu.setBackground(background());
        vBoxMenu.setSpacing(20);
        vBoxMenu.setAlignment(Pos.CENTER);
        vBoxMenu.getChildren().addAll(buttonWorld, buttonSettings, buttonExit,users);

        return vBoxMenu;
    }

    //SETTINGS (SCENE 2)
    private VBox s2CentralMenu() {

        VBox vBoxSettings = new VBox();

        CheckBox buttonSetFS = new CheckBox("Full Screen");
        buttonSetFS.setOnAction(e -> {
            fullscreen = ((CheckBox) e.getSource()).isSelected();
            buttonSetFS.selectedProperty();
            window1.setFullScreen(fullscreen);
            clickSound.play();
        });

        Label label = new Label("Volume");

        Slider buttonSetVol = new Slider(0, 1, 1);
        buttonSetVol.setMaxSize(200, 10);

        buttonSetVol.valueProperty().addListener((observable, oldValue, newValue) -> {
            volume = newValue.doubleValue();
            configFile.setProperty("volume", "newValue.doubleValue()");
            backgroundSound.setVolume(volume);
            clickSound.setVolume(volume);
        });

        Button buttonBack = new Button("BACK");
        buttonBack.setOnAction(event -> {
            window1.setScene(scene1);
            window1.setFullScreen(fullscreen);
            clickSound.play();
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

        autoCompleteTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            int id = countryNameList.indexOf(newValue);
            if (id > -1) {
                Country country = countryList.get(id);
                System.out.println(country.getName() + ": " + country.getX() + " | " + country.getY());

                contentGroup.setTranslateX(country.getX());
                contentGroup.setTranslateY(country.getY());
                contentGroup.setScaleX(1.4);
                contentGroup.setScaleY(1.4);
            }
            else {
                contentGroup.setTranslateY(0);
                contentGroup.setTranslateX(0);
                contentGroup.setScaleY(1);
                contentGroup.setScaleX(1);
            }
        });

        vBoxBar.getChildren().addAll(imageView, mainMenu, buttonExit2, autoCompleteTextField);
        return vBoxBar;
    }

    //PANE (SCENE 3)
    private AnchorPane s3Pane() {
        AnchorPane anchorPane = new AnchorPane();
        BorderPane borderPane = new BorderPane();

        anchorPane.getChildren().addAll(borderPane);
        borderPane.setRight(sidePane());
        borderPane.setLeft(zoomableScrollPane(contentGroup));
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


    //GROUP THAT CONTAINS THE MAP, PINS...

    private ScrollPane zoomableScrollPane(Node content) {

        this.content = content;

        final double SCALE_DELTA = 1.1;

        contentGroup = new Group();
        Image backgroundImage = new Image(App.class.getResource("/resources/Map.png").toExternalForm());

        scroll = new ScrollPane();
        scroll.setPrefSize(700, 466);
        scroll.setPannable(true);
        contentGroup.setOnMouseClicked(e ->{

            Button button = new Pins();

            if(e.getButton() == MouseButton.PRIMARY) {
                if(e.getClickCount() == 2) {


                    button.setTranslateX(e.getX()-30);
                    button.setTranslateY(e.getY()-50);

                    contentGroup.getChildren().addAll(button);
                    System.out.println(e.getX());
                    System.out.println(e.getY());
                }
            }
        });

        content.setOnScroll( (ScrollEvent event) -> {

            if (event.getDeltaY() == 0) {
                return;
            }
            double scaleFactor = (event.getDeltaY() > 0) ? SCALE_DELTA : 1 / SCALE_DELTA;

            contentGroup.setScaleX(contentGroup.getScaleX() * scaleFactor);
            contentGroup.setScaleY(contentGroup.getScaleY() * scaleFactor);

            event.consume();
        });

        scroll.setHvalue(scroll.getHmin() + (scroll.getHmax() - scroll.getHmin()) / 2);
        scroll.setVvalue(scroll.getVmin() + (scroll.getVmax() - scroll.getVmin()) / 2);

        contentGroup.getChildren().setAll(new ImageView(backgroundImage));
        contentGroup.getChildren().add(content);
        scroll.setContent(contentGroup);
        return scroll;
    }

    /** USER INTERFACE */

    //SCENE LOG IN

    private GridPane logIn() {
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
            loginWindow.setScene(sceneSignIn);
        });

        buttonLogIn.setOnAction(e -> {

            String username = userTextField.getText();
            String password = pwBox.getText();

            int index = userNameList.indexOf(username);
            if(index > -1) {

                User user = userList.get(index);

                if (Objects.equals(user.getPassword(), password)) {

                    try {
                        File file = new File("users/" + username + ".cfg");
                        inputStream = new FileInputStream(file);
                    } catch (Exception eta) {
                        inputStream = null;
                        System.out.println("Settings not found for user: " + username);
                    }

                    try {
                        if (inputStream == null) {
                            inputStream.getClass().getResourceAsStream("users/" + username + ".cfg");
                        }
                        configFile.load(inputStream);
                    } catch (Exception eta) {
                        System.out.println("Settings loaded");
                    }

                    System.out.println("Logged in");
                    loginWindow.close();

                } else {
                    System.out.println("Not logged in");
                }
            }
        });
        return grid;
    }

    //SCENE SIGN IN

    private GridPane signIn() {

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
            String passwordOne = pwBox.getText();
            pwBox.setText("");
            String passwordTwo = pwBoxagain.getText();
            pwBoxagain.setText("");
            String username = userTextField.getText();
            userTextField.setText("");

            if (userNameList.indexOf(username)==-1){
                if (passwordOne.equals(passwordTwo)) {

                    ArrayList rawData = new ArrayList();
                    for (User user : userList) {

                        rawData.add(user.getUsername() + ";" + user.getPassword());
                    }

                    rawData.add(username + ";" + passwordOne);
                    String fileName = username;

                    try {

                        Files.write(Paths.get("UserList.txt"), rawData, Charset.forName("UTF-8"));

                        System.out.println("User created");

                        Files.write(Paths.get("users/"+ fileName +".cfg"), new ArrayList<>(), Charset.forName("UTF-8"));

                        userParser.parseUsers();
                        loginWindow.setScene(sceneLogin);
                        window1.show();
                        System.out.println(userNameList.indexOf(username)+1);

                    } catch (IOException e1) {
                        System.out.println("File not found");
                    }
                }

            }else{
                System.out.println("Username exists");
            }
        });
        return grid;
    }

    public static void main(String[] args) {
        launch(args);
    }
}