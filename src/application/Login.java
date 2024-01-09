package application;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Login extends Application {

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        AnchorPane mainForm = new AnchorPane();
        mainForm.setPrefHeight(200.0);
        mainForm.setPrefWidth(200.0);

        AnchorPane leftForm = new AnchorPane();
        leftForm.setPrefHeight(400.0);
        leftForm.setPrefWidth(300.0);
        leftForm.getStyleClass().add("left-form");
        leftForm.getStylesheets().add(getClass().getResource("loginDesign.css").toExternalForm());

        // Use ImageView instead of FontAwesomeIcon
        Image loginImage = new Image(getClass().getResourceAsStream("login.png"));
        ImageView loginImageView = new ImageView(loginImage);
        loginImageView.setFitWidth(150);
        loginImageView.setFitHeight(150);
        loginImageView.setLayoutX(75.0);
        loginImageView.setLayoutY(105.0);

        Label titleLabel = new Label("Employee Management System");
        titleLabel.setLayoutX(26.0);
        titleLabel.setLayoutY(250.0);
        titleLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        titleLabel.setFont(new Font("Tahoma", 18.0));

        leftForm.getChildren().addAll(loginImageView, titleLabel);

        AnchorPane rightForm = new AnchorPane();
        rightForm.setLayoutX(300.0);
        rightForm.setPrefHeight(400.0);
        rightForm.setPrefWidth(300.0);
        rightForm.getStyleClass().add("right-form");
        rightForm.getStylesheets().add(getClass().getResource("loginDesign.css").toExternalForm());

        Label welcomeLabel = new Label("Welcome back Admin!");
        welcomeLabel.setLayoutX(20.0);
        welcomeLabel.setLayoutY(99.0);
        welcomeLabel.setTextFill(javafx.scene.paint.Color.valueOf("#368981"));
        welcomeLabel.setFont(new Font("Tahoma Bold", 17.0));

        TextField usernameField = new TextField();
        usernameField.setLayoutX(40.0);
        usernameField.setLayoutY(144.0);
        usernameField.setPrefHeight(30.0);
        usernameField.setPrefWidth(220.0);
        usernameField.setPromptText("Username");
        usernameField.getStyleClass().add("textfield");

        PasswordField passwordField = new PasswordField();
        passwordField.setLayoutX(40.0);
        passwordField.setLayoutY(185.0);
        passwordField.setPrefHeight(30.0);
        passwordField.setPrefWidth(220.0);
        passwordField.setPromptText("Password");
        passwordField.getStyleClass().add("textfield");

        Button loginButton = new Button("Login");
        loginButton.setLayoutX(40.0);
        loginButton.setLayoutY(238.0);
        loginButton.setPrefHeight(40.0);
        loginButton.setPrefWidth(220.0);
        loginButton.getStyleClass().add("login-btn");
        loginButton.setOnAction(e -> {
            String enteredUsername = usernameField.getText();
            String enteredPassword = passwordField.getText();

            if (authenticateUser(enteredUsername, enteredPassword)) {
                System.out.println("Login successful!");
                Main adminApp = new Main();
                adminApp.start(new Stage());
                primaryStage.close();
            } else {
            	Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Invalid admin.");
                alert.setHeaderText(null);
                alert.setContentText("Username/Password incorrect");
             // Show the alert
                alert.showAndWait();
            }
        });
        Button closeButton = new Button();
        closeButton.setLayoutX(248.0);
        closeButton.setLayoutY(14.0);
        closeButton.setPrefHeight(24.0);
        closeButton.setPrefWidth(58.0);
        closeButton.getStyleClass().add("close");
        closeButton.setOnAction(e -> close());

        

        rightForm.getChildren().addAll(welcomeLabel, usernameField, passwordField, loginButton, closeButton);

        mainForm.getChildren().addAll(leftForm, rightForm);
        root.getChildren().add(mainForm);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Login Page");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void close() {
        // Add logic to handle close button action
        System.exit(0);
    }
    private boolean authenticateUser(String username, String password) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/entreprise";
        String dbUsername = "root";
        String dbPassword = "";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
            String query = "SELECT * FROM admin WHERE username = ? AND password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next(); // Return true if a matching user is found.
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle database connection or query errors.
            return false;
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
