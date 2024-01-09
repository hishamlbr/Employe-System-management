package application;
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main extends Application {

    private TableView<User> tableView;
    private User selectedUser;

    @Override
    public void start(Stage primaryStage) {
        // Set the title for the project
        String projectTitle = "Conception Entreprise";
        primaryStage.setTitle(projectTitle);
   
        // Create a label for the project title
        Label titleLabel = new Label(projectTitle);
        titleLabel.setFont(new Font("Arial", 20));
        titleLabel.setStyle("-fx-text-fill: #3C493F;");

        // Create a label for the current date
        Label dateLabel = new Label(getCurrentDate());
        dateLabel.setFont(new Font("Arial", 14));
        dateLabel.setStyle("-fx-text-fill: white;");

     // Create a HBox for the title and date labels
        HBox titleBox = new HBox(50);
        titleBox.setAlignment(Pos.CENTER);  // Set alignment to center
        titleBox.setSpacing(20);  // Adjust the spacing between cards
        titleBox.setPadding(new Insets(20, 0, 20, 0));  // Add padding to the top and bottom
        
        
     // Calculate the number of users
        int users = calculateUserCount();
        int att =calculateatt();
        int salarys=calculsalarysum();
        String sl;
        if (salarys < 1000) {
            sl= String.valueOf(salarys);
        } else if (salarys < 1000000) {
           sl= String.format("%.1fk $", (double) salarys / 1000);
        } else {
            sl= String.format("%.1fM $", (double) salarys / 1000000);
        }
        
        // Create first card
     // Create first card with the user count
        AnchorPane card1 = createCard("Users", String.valueOf(users),"login");

        // Create second card
        AnchorPane card2 = createCard("Total Salary", sl,"salary");

        // Create third card
        AnchorPane card3 = createCard("attendance", String.valueOf(att),"att");
        
        titleBox.setStyle(
        	    "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #A2E3C4, aqua);"
        	);
        // Add cards to the inner anchor pane
        titleBox.getChildren().addAll(card1,card2,card3);


        // Set Style
        titleBox.getStylesheets().add(getClass().getResource("dashboardDesign.css").toExternalForm());

        // Create the sidebar with buttons
        VBox sidebar = createSidebar();

        // Create the main content area with a TableView
        tableView = createTableView();
        BorderPane mainPane = new BorderPane(tableView);

        // Combine the titleBox and main content in the CENTER position
        BorderPane centerPane = new BorderPane();
        centerPane.setTop(titleBox);
        centerPane.setCenter(mainPane);

        // Combine the sidebar and main content in the LEFT position
        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(sidebar);
        borderPane.setCenter(centerPane);

        // Create the scene
        Scene scene = new Scene(borderPane, 900, 500);
        scene.getStylesheets().add(getClass().getResource("dashboardDesign.css").toExternalForm());
        // Set the scene and show the stage
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private String getCurrentDate() {
        // Get the current date and format it
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(new Date());
    }

    private VBox createSidebar() {
        VBox sidebar = new VBox(15);
       
        sidebar.setStyle(
        	    "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #F0F7F4, #A2E3C4);"
        	);
        
        Label titleLabel = new Label("Conception Entreprise");
        titleLabel.setFont(new Font("Arial", 20));
        titleLabel.setStyle("-fx-text-fill: #3C493F;");
        
     // Create a label for the current date
        Label dateLabel = new Label(getCurrentDate());
        dateLabel.setFont(new Font("Arial", 14));
        dateLabel.setStyle("-fx-text-fill: blue;");

     // Use ImageView 
        Image loginImage = new Image(getClass().getResourceAsStream("admin.png"));
        ImageView loginImageView = new ImageView(loginImage);
        loginImageView.setFitWidth(100);
        loginImageView.setFitHeight(100);
        loginImageView.setLayoutX(2);
        loginImageView.setLayoutY(-50);
        
     // Add a line (horizontal)
        Line line = new Line();
        line.setStartX(0.0);
        line.setStartY(300.0);
        line.setEndX(200);
        line.setEndY(300);
        line.setStroke(Color.WHITE);
        // Add buttons
        Button addButton = new Button("Add");
        addButton.setPrefWidth(100);
        addButton.getStyleClass().add("login-btn");
        Button deleteButton = new Button("Delete");
        deleteButton.setPrefWidth(100);
        deleteButton.getStyleClass().add("login-btn");
        Button updateButton = new Button("Update");
        updateButton.setPrefWidth(100);
        updateButton.getStyleClass().add("login-btn");
        Button attButton = new Button("Attendance");
        attButton.setPrefWidth(100);
        attButton.getStyleClass().add("login-btn");
        Button quitButton = new Button("Quit");
        quitButton.setPrefWidth(100);
        quitButton.getStyleClass().add("login-btn");

     // Add actions to buttons
        addButton.setOnAction(e -> showAddEmployeeForm());
        deleteButton.setOnAction(e -> deleteSelectedEmployee());
        updateButton.setOnAction(e -> showUpdateEmployeeForm()); // Updated this line
        attButton.setOnAction(e -> showAttendancePage());
        quitButton.setOnAction(e -> System.exit(0));

        // Add elements to the sidebar
        sidebar.getChildren().addAll(titleLabel,dateLabel,loginImageView,line,addButton, deleteButton, updateButton, attButton, quitButton);
        sidebar.setAlignment(Pos.CENTER);
        sidebar.getStylesheets().add(getClass().getResource("loginDesign.css").toExternalForm());
        sidebar.setPrefWidth(150);

        return sidebar;
    }

    private AnchorPane createCard(String labelText, String labelId,String img) {
        AnchorPane card = new AnchorPane();

        // Adjust card styles
        card.getStyleClass().addAll("card", "white-bg", "shadow");

        Label label1 = new Label(labelText);
        label1.setLayoutX(10); 
        label1.setLayoutY(100); 
        label1.setTextFill(javafx.scene.paint.Color.WHITE);
        label1.setFont(Font.font("Tahoma", FontWeight.BOLD, 17));

        Label label2 = new Label(labelId);
        label2.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);
        label2.setLayoutX(55); 
        label2.setLayoutY(20); 
        label2.setPrefHeight(12);
        label2.setPrefWidth(148);
        label2.setTextFill(javafx.scene.paint.Color.WHITE);
        label2.setFont(Font.font("Arial", 25));
        
     // Use ImageView 
        Image getImage = new Image(getClass().getResourceAsStream(img+".png"));
        ImageView cardImage = new ImageView(getImage);
        cardImage.setFitWidth(80);
        cardImage.setFitHeight(80);
        cardImage.setLayoutX(10);
        cardImage.setLayoutY(10);
        
        

        card.getChildren().addAll(label1, label2,cardImage);

        return card;
    }

    private int calculateUserCount() {
        int userCount = 0;

        try {
            Connection connection = getConnection();

            // Query to count the number of rows in the 'employe' table
            String query = "SELECT COUNT(*) AS count FROM employe";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Get the count value
            if (resultSet.next()) {
                userCount = resultSet.getInt("count");
            }

            // Close resources
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }

        return userCount;
    }
    private int calculsalarysum() {
    	try {
            Connection connection = getConnection();

            // Query to count the sum of rows contain salary
            String query = "SELECT SUM(salary) AS totalSalary FROM employe";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            int totalSalary = 0;
            if (resultSet.next()) {
                totalSalary = resultSet.getInt("totalSalary");
            }

            // Close resources
            resultSet.close();
            statement.close();
            connection.close();

            return totalSalary;


    	} catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed
            return 0;
        }
    }
    private int calculateatt() {
        int att = 0;

        try {
            Connection connection = getConnection();

            // Query to count the number of rows in the 'employe' table
            String query = "SELECT COUNT(*) AS count FROM attendance";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Get the count value
            if (resultSet.next()) {
                att = resultSet.getInt("count");
            }

            // Close resources
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }

        return att;
    }
    @SuppressWarnings({ "deprecation", "unchecked" })
	private TableView<User> createTableView() {
        TableView<User> tableView = new TableView<>();
        
     // Add a new TableColumn for checkboxes
        TableColumn<User, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(data -> data.getValue().idProperty().asObject());
        

        TableColumn<User, Boolean> selectCol = new TableColumn<>("Attendance");
        selectCol.setCellValueFactory(data -> data.getValue().selectedProperty().asObject());

        TableColumn<User, String> nomCol = new TableColumn<>("Last name");
        nomCol.setCellValueFactory(data -> data.getValue().nomProperty());

        TableColumn<User, String> prenomCol = new TableColumn<>("First name");
        prenomCol.setCellValueFactory(data -> data.getValue().prenomProperty());

        TableColumn<User, Integer> ageCol = new TableColumn<>("Age");
        ageCol.setCellValueFactory(data -> data.getValue().ageProperty().asObject());

        TableColumn<User, String> jobCol = new TableColumn<>("Job");
        jobCol.setCellValueFactory(data -> data.getValue().jobProperty());

        TableColumn<User, Integer> salaryCol = new TableColumn<>("Salary");
        salaryCol.setCellValueFactory(data -> data.getValue().salaryProperty().asObject());

     // Add the checkbox column to the table
        tableView.getColumns().addAll(idCol, nomCol, prenomCol, ageCol, jobCol, salaryCol, selectCol);

        // Set column resize policy to fill the width without empty space
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        try {
            Connection connection = getConnection();

            // Query to select all rows from the 'employe' table
            String query = "SELECT * FROM employe";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Populate the TableView with data from the database
            ObservableList<User> data = FXCollections.observableArrayList();
            while (resultSet.next()) {
            	int id = resultSet.getInt("id");
                String nom = resultSet.getString("fname");
                String prenom = resultSet.getString("lname");
                int age = resultSet.getInt("age");
                String job = resultSet.getString("job");
                int salary = resultSet.getInt("salary");
                
                
                data.add(new User(id,nom, prenom, age, job, salary));
            }

            tableView.setItems(data);

            // Close resources
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
        Node deleteButton = null;
        // Add listener to enable/disable delete button based on selection
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {

            if (newSelection != null) {
                // Employee is selected
                deleteButton.setDisable(false);
            } else {
                // No employee selected
                deleteButton.setDisable(true);
            }
        });
        // Add listener to handle checkbox actions if needed
        selectCol.setCellFactory(col -> new TableCell<User, Boolean>() {
            private final CheckBox checkBox = new CheckBox();

            {
                checkBox.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
                    user.setSelected(checkBox.isSelected());

                    // Insert attendance information into the database if checkbox is selected
                    if (checkBox.isSelected()) {
                        insertAttendance(user.getId(), user.getNom(), user.getPrenom(),getCurrentDate(),user.getSalary());
                    }

                    // Update the salary when the checkbox is clicked
                    //int newSalary = user.getSalary() - 100;
                    //user.setSalary(newSalary);

                    // If you want to update the TableView, you may need to refresh it
                    getTableView().refresh();
                });
            }

            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    checkBox.setSelected(item);
                    setGraphic(checkBox);
                }
            }
        });



        return tableView;
    }

    private void insertAttendance(int userId, String nom, String prenom, String date,int usersalary) {
        try {
            Connection connection = getConnection();

            // Convert the String date to java.sql.Date
            java.sql.Date sqlDate = java.sql.Date.valueOf(date);

            // Check if the record already exists for the given user ID and date
            if (!attendanceExists(connection, userId, sqlDate)) {
	                // Insert query for attendance
	                	String query = "INSERT INTO attendance (id, employe_name, employe_prenom, date) VALUES (?, ?, ?, ?)";
	                	PreparedStatement preparedStatement = connection.prepareStatement(query);
	                    preparedStatement.setInt(1, userId);
	                    preparedStatement.setString(2, nom);
	                    preparedStatement.setString(3, prenom);
	                    preparedStatement.setDate(4, sqlDate);
	                    preparedStatement.executeUpdate(); 
	                    
	                // update the salary of the user 
	                    

                        // Update query
                        String query2 = "UPDATE employe SET salary=? WHERE id=?";
                        PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
                           
                        preparedStatement2.setInt(1, usersalary-100);
                        preparedStatement2.setInt(2,userId );
                        // Execute the update statement
                        preparedStatement2.executeUpdate();
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText(null);
                        alert.setContentText("Attendance taked succesefuly, the Employe will lose 100 $ from her salary");
                     // Show the alert
                        alert.showAndWait();
                        refreshTableView();
                        }else {
                        	Alert alert = new Alert(AlertType.WARNING);
                            alert.setTitle("can't take attendance.");
                            alert.setHeaderText(null);
                            alert.setContentText("This Employe is already absent in this day");
                         // Show the alert
                            alert.showAndWait();
                        }

                        // Close the connection
                        connection.close();
       
        } catch (SQLException e) {
            e.printStackTrace();
            } // Print the stack trace for debugging
   }

    // Helper method to check if attendance record already exists for the given user ID and date
    private boolean attendanceExists(Connection connection, int userId, java.sql.Date date) throws SQLException {
        String query = "SELECT 1 FROM attendance WHERE id = ? AND date = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setDate(2, date);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // Returns true if a record is found, indicating attendance exists
            }
        }
    }

    private void showAddEmployeeForm() {
        Stage addEmployeeStage = new Stage();
        addEmployeeStage.initModality(Modality.APPLICATION_MODAL);
        addEmployeeStage.setTitle("Add Employee");
        

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 20, 20));
        gridPane.setStyle(
        	    "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #F0F7F4, #B3BFB8);"
        	);
        TextField nomField = new TextField();
        TextField prenomField = new TextField();
        TextField ageField = new TextField();

        // Use a ComboBox for the "Job" field
        ComboBox<String> jobComboBox = new ComboBox<>();
        jobComboBox.getItems().addAll("Developer", "Designer", "Manager", "Other");

        TextField salaryField = new TextField();

        gridPane.add(new Label("Nom:"), 0, 0);
        gridPane.add(nomField, 1, 0);
        gridPane.add(new Label("Prenom:"), 0, 1);
        gridPane.add(prenomField, 1, 1);
        gridPane.add(new Label("Age:"), 0, 2);
        gridPane.add(ageField, 1, 2);
        gridPane.add(new Label("Job:"), 0, 3);
        gridPane.add(jobComboBox, 1, 3);
        gridPane.add(new Label("Salary:"), 0, 4);
        gridPane.add(salaryField, 1, 4);
        gridPane.getStylesheets().add(getClass().getResource("loginDesign.css").toExternalForm());

        Button addButton = new Button("Add Employee");
        addButton.getStyleClass().add("login-btn");
        addButton.setOnAction(e -> {
            // Validate and add the employee
            if (validateInput(nomField.getText(), prenomField.getText(), ageField.getText(),
                    jobComboBox.getValue(), salaryField.getText())) {
                // Insert the employee to the database
                insertUser(nomField.getText(), prenomField.getText(),
                        Integer.parseInt(ageField.getText()), jobComboBox.getValue(),
                        Integer.parseInt(salaryField.getText()));
                

                addEmployeeStage.close();
            } else {
                // Show an error message or handle invalid input
                System.out.println("Invalid input. Please check the values.");
                // Create a simple alert
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Invalid input.");
                alert.setHeaderText(null);
                alert.setContentText("Please check the values");

                // Show the alert
                alert.showAndWait();
            }
        });

        gridPane.add(addButton, 0, 5, 2, 1);
        

        Scene scene = new Scene(gridPane, 400, 300);
        addEmployeeStage.setScene(scene);
        addEmployeeStage.showAndWait();
    }
    private void refreshTableView() {
        try {
            Connection connection = getConnection();

            // Query to select all rows from the 'employe' table
            String query = "SELECT * FROM employe";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Populate the TableView with the retrieved data
            ObservableList<User> data = FXCollections.observableArrayList();
            while (resultSet.next()) {
            	int id = resultSet.getInt("id");
                String nom = resultSet.getString("fname");
                String prenom = resultSet.getString("lname");
                int age = resultSet.getInt("age");
                String job = resultSet.getString("job");
                int salary = resultSet.getInt("salary");

                data.add(new User(id,nom, prenom, age, job, salary));
            }

            // Update the TableView with the new data
            tableView.setItems(data);

            // Close resources
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }
    private void deleteSelectedEmployee() {
        User selectedUser = tableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Are you sure you want to delete the selected employee?");

            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // User clicked OK, proceed with deletion

                // Database delete operation
                deleteUser(selectedUser.getId());

                // Remove the selected user from the TableView
                tableView.getItems().remove(selectedUser);
            } else {
                // User clicked Cancel or closed the dialog, do nothing
            }
        } else {
            // Create a simple alert
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No employee selected.");
            alert.setHeaderText(null);
            alert.setContentText("Please select an employee to delete");

            // Show the alert
            alert.showAndWait();
        }
    }
 // Method to delete user data from the database
    private void deleteUser(int userId) {
        try {
            Connection connection = getConnection();

            // Delete query
            String query = "DELETE FROM employe WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, userId);

                // Execute the delete statement
                preparedStatement.executeUpdate();
            }

            // Close the connection
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }
    private boolean validateInput(String nom, String prenom, String age, String job, String salary) {
        
    	// Function to return if the input fields are in correct form
        return !nom.isEmpty() && !prenom.isEmpty() && age.matches("\\d+") && !job.isEmpty() && salary.matches("\\d+");
    }

 // Method to insert user data into the database
    private void insertUser(String nom, String prenom, int age, String job, int salary) {
        try {
            Connection connection = getConnection();

            // Insert query
            String query = "INSERT INTO employe (fname, lname, age, job, salary) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, nom);
                preparedStatement.setString(2, prenom);
                preparedStatement.setInt(3, age);
                preparedStatement.setString(4, job);
                preparedStatement.setInt(5, salary);

                // Execute the insert statement
                preparedStatement.executeUpdate();
                refreshTableView();
            }

            // Close the connection
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }
   
    private static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Replace "your_database_url", "your_username", and "your_password" with your actual database information
            String url = "jdbc:mysql://localhost:3306/entreprise";
            String username = "root";
            String password = "";

            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the database");
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
            return null;
        }
    }
   
    public static void main(String[] args) {
        Connection connection = getConnection();
        if (connection != null) {
            launch(args);
        } else {
            System.err.println("Unable to connect to the database. Exiting.");
        }
    }
    
    private void showUpdateEmployeeForm() {
        selectedUser = tableView.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {
            Stage updateEmployeeStage = new Stage();
            updateEmployeeStage.initModality(Modality.APPLICATION_MODAL);
            updateEmployeeStage.setTitle("Update Employee");

            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            gridPane.setPadding(new Insets(20, 150, 20, 20));
            
            

            TextField nomField = new TextField(selectedUser.getNom());
            TextField prenomField = new TextField(selectedUser.getPrenom());
            TextField ageField = new TextField(String.valueOf(selectedUser.getAge()));

            ComboBox<String> jobComboBox = new ComboBox<>();
            jobComboBox.getItems().addAll("Developer", "Designer", "Manager", "Other");
            jobComboBox.setValue(selectedUser.getJob());

            TextField salaryField = new TextField(String.valueOf(selectedUser.getSalary()));

            gridPane.add(new Label("Nom:"), 0, 0);
            gridPane.add(nomField, 1, 0);
            gridPane.add(new Label("Prenom:"), 0, 1);
            gridPane.add(prenomField, 1, 1);
            gridPane.add(new Label("Age:"), 0, 2);
            gridPane.add(ageField, 1, 2);
            gridPane.add(new Label("Job:"), 0, 3);
            gridPane.add(jobComboBox, 1, 3);
            gridPane.add(new Label("Salary:"), 0, 4);
            gridPane.add(salaryField, 1, 4);
            gridPane.getStylesheets().add(getClass().getResource("loginDesign.css").toExternalForm());

            Button updateButton = new Button("Update Employee");
            updateButton.getStyleClass().add("login-btn");
            updateButton.setOnAction(event -> {
                if (validateInput(nomField.getText(), prenomField.getText(), ageField.getText(),
                        jobComboBox.getValue(), salaryField.getText())) {
                    updateUser(selectedUser, nomField.getText(), prenomField.getText(),
                            Integer.parseInt(ageField.getText()), jobComboBox.getValue(),
                            Integer.parseInt(salaryField.getText()));

                    // Refresh the TableView with the updated data from the database
                    refreshTableView();

                    updateEmployeeStage.close();
                } else {
                    System.out.println("Invalid input. Please check the values.");
                    // Create a simple alert
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Invalid input.");
                    alert.setHeaderText(null);
                    alert.setContentText("Please check the values");

                    // Show the alert
                    alert.showAndWait();
                }
            });

            gridPane.add(updateButton, 0, 5, 2, 1);
            updateButton.setStyle("-fx-background-color: #A2E3C4; -fx-text-fill: white;");
            gridPane.getStylesheets().add(getClass().getResource("dashboardDesign.css").toExternalForm());
            Scene scene = new Scene(gridPane, 400, 300);
            updateEmployeeStage.setScene(scene);
            updateEmployeeStage.showAndWait();
            scene.getStylesheets().add(getClass().getResource("dashboardDesign.css").toExternalForm());
        } else {
            System.out.println("No employee selected for update.");
            // Create a simple alert
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No employee selected.");
            alert.setHeaderText(null);
            alert.setContentText("Please select an employee to update");

            // Show the alert
            alert.showAndWait();
        }
    }
    
    private TableView<userAtt> attendanceTableView = new TableView<>();


    private void showAttendancePage() {
	    Stage attendanceStage = new Stage();
	    attendanceStage.initModality(Modality.APPLICATION_MODAL);
	    attendanceStage.setTitle("Employee Attendance");
	
	    // Create a VBox for the attendance page
	    VBox attendanceVBox = new VBox(10);
	    attendanceVBox.setAlignment(Pos.CENTER);
	    attendanceVBox.setPadding(new Insets(20));
	
	    // Add a label for the attendance page
	    Label attendanceLabel = new Label("Employee Attendance");
	    attendanceLabel.setFont(new Font("Arial", 20));
	    attendanceLabel.setStyle("-fx-text-fill: #3C493F;");
	
	    // Add a DatePicker for selecting the date
	    DatePicker datePicker = new DatePicker();
	    datePicker.setValue(LocalDate.now());
	
	    // Add a button to fetch attendance for the selected date
	    Button fetchButton = new Button("Fetch Attendance");
	    fetchButton.getStyleClass().add("login-btn");
	    fetchButton.setOnAction(e -> fetchAttendance(datePicker.getValue()));
	
	    // Add a TableView for displaying attendance data
	    TableColumn<userAtt, Integer> idColumn = new TableColumn<>("ID");
	    idColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());

	   
	
	    TableColumn<userAtt, String> nomColumn = new TableColumn<>("Nom");
	    nomColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));

	    TableColumn<userAtt, String> prenomColumn = new TableColumn<>("Prenom");
	    prenomColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPrenom()));

	    TableColumn<userAtt, String> dateColumn = new TableColumn<>("Date");
	    dateColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDate()));

	
	    attendanceTableView.getColumns().addAll(idColumn, nomColumn, prenomColumn, dateColumn);
	    attendanceTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	
	    // Add elements to the attendance VBox
	    attendanceVBox.getChildren().addAll(attendanceLabel, datePicker, fetchButton, attendanceTableView);
	    attendanceVBox.getStylesheets().add(getClass().getResource("loginDesign.css").toExternalForm());
	    attendanceVBox.setStyle(
        	    "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #F0F7F4, #B3BFB8);"
        	);
	    // Create the scene
	    Scene attendanceScene = new Scene(attendanceVBox, 600, 400);
	    attendanceScene.getStylesheets().add(getClass().getResource("dashboardDesign.css").toExternalForm());
	
	    // Set the scene and show the stage
	    attendanceStage.setScene(attendanceScene);
	    attendanceStage.showAndWait();
	}
    private void fetchAttendance(LocalDate selectedDate) {
    System.out.println(selectedDate);
    try {
        Connection connection = getConnection();

        // Query to select all rows from the 'attendance' table for the given date
        String query = "SELECT * FROM attendance WHERE date=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDate(1, java.sql.Date.valueOf(selectedDate));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                ObservableList<userAtt> data = FXCollections.observableArrayList();

                while (resultSet.next()) {
                    // Retrieve data from the result set
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("employe_name");
                    String prenom = resultSet.getString("employe_prenom");
                    Date date = resultSet.getDate("date");

                    // Convert the Date to a String using a formatter
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String dateString = dateFormat.format(date);

                    // Create a userAtt object and add it to the ObservableList
                    data.add(new userAtt(id, name, prenom, dateString));
                }

                // Clear the previous data in the TableView
                attendanceTableView.getItems().clear();

                // Set the new data to the TableView
                attendanceTableView.setItems(data);

                // Close resources
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    } finally {
        // Close the connection if needed
    }
}
    
    private void updateUser(User user, String nom, String prenom, int age, String job, int salary) {
        try {
            Connection connection = getConnection();

            // Update query
            String query = "UPDATE employe SET fname=?, lname=?, age=?, job=?, salary=? WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, nom);
                preparedStatement.setString(2, prenom);
                preparedStatement.setInt(3, age);
                preparedStatement.setString(4, job);
                preparedStatement.setInt(5, salary);
                preparedStatement.setInt(6, user.getId());

                // Execute the update statement
                preparedStatement.executeUpdate();
            }

            // Close the connection
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }

}
