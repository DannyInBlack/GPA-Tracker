package Root;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.io.IOException;


/**
 * Used in the Home view for user navigation.
 * Handles the graph drawing and calculation.
 */
public class HomeController extends DB {

    private final String user = SceneController.getUser();

    /**
     * Opens the add subject view in a new window
     */
    public void onAddSubjectClick(){
        try {
            SceneController.addStage("addSubject");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the remove subject view in a new window
     */
    public void removeSubject(){
        try {
            SceneController.addStage("removeSubject");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
    * Creates a new graph from user gpa history.
    * At the same time, adds any new changes to the gpa into the history.
    *
     */
    public void loadGraph(){
        // Creating the graph
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis(0, 4.0, 0.5);

        LineChart<String, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle("GPA Tracker");

        // Series are used to add data values of point
        Series<String, Number> series =  new Series<>();
        series.setName("My Progress");

        // Inserting any new GPA value before retrieving the history
        calculateNewGpa();

        String getHistory = "SELECT date_calculated, gpa FROM gpa_history WHERE username = ?";


        try {
            Connection conn = getDBConn();
            PreparedStatement statement = conn.prepareStatement(getHistory);
            statement.setString(1, user);
            ResultSet result = statement.executeQuery();

            while(result.next()){ // Each gpa history value is inserted as a new point in the series
                String date = result.getDate(1).toString();
                double gpa = result.getDouble(2);
                series.getData().add(new Data<>(date, gpa));
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        Scene scene = new Scene(chart);
        chart.getData().add(series); // Adding the series would add all the points as well

        // Display the chart in a new window
        SceneController.addStage(scene, "Chart");
    }

    /**
     * Calculates new GPA value.
     * If the new GPA is different, it is inserted into the history for tracking.
     * History keeps track of different GPA values in different dates
    */
    private void calculateNewGpa(){
        Connection conn;
        PreparedStatement statement;
        ResultSet result;

        try {
            conn = getDBConn();

            // Calculating current GPA;
            String getGrades = "SELECT S_Name, Grade FROM grades WHERE username = ? ";
            statement = conn.prepareStatement(getGrades);
            statement.setString(1, user);
            result = statement.executeQuery();

            double count = 0, grades = 0;
            while(result.next()){
                grades += result.getDouble("Grade");
                count += 1;
            }
            double gpa;
            gpa = grades / count;

            // If there are no subjects, grades / count would equal 0 / 0, giving a NaN value
            // This also ensures that the graph shows a 0 value in the beginning
            if(Double.isNaN(gpa)){
                gpa = 0;
            }
            // Avoid duplicate insertion
            String query_last_gpa = "SELECT * from gpa_history where username = ?";
            statement = conn.prepareStatement(query_last_gpa);
            statement.setString(1, user);
            result = statement.executeQuery();
            if(result.next()){
                result.last();
                if(result.getDouble("gpa") == gpa) return;
            }

            /*
             Inserting GPA that was confirmed to be different from last GPA value
             No need to insert the date as the MySQL DB is configured to insert the current date automatically
             If the user already has a calculated gpa value for today, we update the value instead of adding a new record
             */
            try{
                String newGpa = "INSERT INTO gpa_history(username, gpa) VALUES (?, ?)";
                statement = conn.prepareStatement(newGpa);
                statement.setString(1, user);
                statement.setDouble(2, gpa);
                statement.executeUpdate();
            } catch (SQLException e){
                String updateGpa = "UPDATE gpa_history SET gpa = ? WHERE username = ? AND date_calculated = ?";
                statement = conn.prepareStatement(updateGpa);
                statement.setDouble(1, gpa);
                statement.setString(2, user);
                statement.setDate(3, new java.sql.Date(new java.util.Date().getTime()));
                statement.executeUpdate();
            }


        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
    * Retrieve subjects that had been inserted by user.
    */
    public void getSubjectList(){

        VBox pane = new VBox();
        pane.setAlignment(Pos.CENTER);
        pane.setPrefWidth(300);

        try{
            Connection conn = getDBConn();
            String getSubjects = "SELECT * FROM grades WHERE username = ?";
            PreparedStatement statement = conn.prepareStatement(getSubjects);
            statement.setString(1, user);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                String subject = result.getString("S_Name");
                double grade = result.getDouble("Grade");
                Label label = new Label( subject + " " + grade);
                label.setFont(new Font(20));
                pane.getChildren().add(label);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(pane);

        SceneController.addStage(scene, "Subject List");
    }

    /**
     * Opens confirmation window
     */
    public void onResetClick(){
        try {
            SceneController.addStage("confirmDelete");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
