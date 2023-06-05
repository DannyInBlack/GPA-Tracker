package Root;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Locale;

/**
 * Handles adding subjects and their grades
 */
public class AddSubjectController extends SubjectController {
    @FXML
    private TextField subject, grade;
    @FXML
    private Label message;

    // Used for input validation
    private boolean ok = true;

    /**
     * Adds subjects with their grade
     */
    public void onAddSubjectClick(){
        String s = subject.getText();
        String g = grade.getText();

        ok = true;
        double gradeValue = getGradePoint(g);

        // Validating Input
        if(!ok) message.setText("Enter a valid grade!");
        if(s.length() > 30) message.setText("Subject length must be lower or equal to 30");
        if(!ok || s.isEmpty() || s.length() > 30) return;
        try{
            Connection conn = getDBConn();
            String newGpa = "INSERT INTO grades(S_Name, username, Grade) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(newGpa);
            statement.setString(1, s.toUpperCase()); // subjects inserted uppercase for standardization
            statement.setString(2, SceneController.getUser());
            statement.setDouble(3, gradeValue);
            statement.executeUpdate();

            message.setText("Subject added!");
            grade.setText("");
            subject.setText("");
        } catch (SQLException e){
            // TODO: I hate this. Cannot distinguish between actual errors in the database and duplicated input.
            //  This warning could be wrong if the database had an issue instead. Might be fixable if the method were
            //  to update the subjects as well.
            message.setText("Subject already inserted, please remove it first if you want to update it");
        }
    }

    /**
     * Gets the corresponding grade value for a letter grade
     * @param grade The letter grade, for e.g.: A, A+, B, ...
     * @return A double value that represents the grade value.
     * If the grade inserted is not a valid grade sets up a flag for validation.
     */
    private double getGradePoint(String grade) {
        switch (grade) {
            case "A+":
            case "A":
                return 4;
            case "A-":
                return 3.7;
            case "B+":
                return 3.3;
            case "B":
                return 3;
            case "B-":
                return 2.7;
            case "C+":
                return 2.3;
            case "C":
                return 2;
            case "C-":
                return 1.7;
            case "D+":
                return 1.3;
            case "D":
                return 1;
            case "D-":
                return 0.7;
            case "F":
                return 0;
            default:
                ok = false;
                return 0;

        }
    }

}
