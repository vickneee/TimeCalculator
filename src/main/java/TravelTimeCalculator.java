import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TravelTimeCalculator extends Application {

    private TextField speedField;
    private TextField distanceField;
    private TextField timeField;
    private Button calculateButton;
    private Button clearButton;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Travel Time Calculator - JavaFX");

        // Create input fields
        speedField = new TextField();
        speedField.setPromptText("Enter speed in km/h");

        distanceField = new TextField();
        distanceField.setPromptText("Enter distance in km");

        timeField = new TextField();
        timeField.setPromptText("Time will be calculated here");
        timeField.setEditable(false);
        timeField.setStyle("-fx-background-color: #f0f0f0;");

        // Create buttons
        calculateButton = new Button("Calculate Time");
        clearButton = new Button("Clear");

        // Create labels
        Label speedLabel = new Label("Speed (km/h):");
        Label distanceLabel = new Label("Distance (km):");
        Label timeLabel = new Label("Time to reach destination:");

        // Create layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Add components to grid
        grid.add(speedLabel, 0, 0);
        grid.add(speedField, 1, 0);

        grid.add(distanceLabel, 0, 1);
        grid.add(distanceField, 1, 1);

        grid.add(timeLabel, 0, 2);
        grid.add(timeField, 1, 2);

        // Button panel
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(calculateButton, clearButton);

        grid.add(buttonBox, 0, 3, 2, 1);

        // Set up event handlers
        setupEventHandlers();

        // Create scene and show stage
        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setupEventHandlers() {
        calculateButton.setOnAction(e -> calculateTime());

        clearButton.setOnAction(e -> clearFields());

        // Add Enter key support
        speedField.setOnAction(e -> calculateTime());
        distanceField.setOnAction(e -> calculateTime());
    }

    private void calculateTime() {
        try {
            // Get input values
            double speed = Double.parseDouble(speedField.getText().trim());
            double distance = Double.parseDouble(distanceField.getText().trim());

            // Validate input
            if (speed <= 0) {
                showAlert("Input Error", "Speed must be greater than zero!");
                return;
            }

            if (distance <= 0) {
                showAlert("Input Error", "Distance must be greater than zero!");
                return;
            }

            // Calculate time (time = distance / speed)
            double timeInHours = distance / speed;

            // Format the result
            String timeString = formatTime(timeInHours);
            timeField.setText(timeString);

        } catch (NumberFormatException ex) {
            showAlert("Input Error", "Please enter valid numbers for speed and distance!");
        } catch (Exception ex) {
            showAlert("Error", "An error occurred: " + ex.getMessage());
        }
    }

    private String formatTime(double timeInHours) {
        int hours = (int) timeInHours;
        int minutes = (int) ((timeInHours - hours) * 60);
        int seconds = (int) (((timeInHours - hours) * 60 - minutes) * 60);

        if (hours > 0) {
            return String.format("%d hours, %d minutes, %d seconds", hours, minutes, seconds);
        } else if (minutes > 0) {
            return String.format("%d minutes, %d seconds", minutes, seconds);
        } else {
            return String.format("%d seconds", seconds);
        }
    }

    private void clearFields() {
        speedField.clear();
        distanceField.clear();
        timeField.clear();
        speedField.requestFocus();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}