package Root;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Handles removing subjects
 */
public class RemoveSubjectController extends SubjectController {
    @FXML
    private TextField subject;
    @FXML
    private Label message;

    /**
     * Deletes inputted subject - case-insensitive
     */
    public void onRemoveSubjectClick(){
        String s = subject.getText();

        try{
            Connection conn = getDBConn();
            String newGpa = "DELETE FROM grades WHERE username = ? AND S_Name = ?";
            PreparedStatement statement = conn.prepareStatement(newGpa);
            statement.setString(1, SceneController.getUser());
            statement.setString(2, s);
            if(statement.executeUpdate() == 0) message.setText("Subject was not found, check for typos");
            else{
                subject.setText("");
                message.setText("Subject successfully removed");
            }
        } catch (SQLException e){
            message.setText(e.getMessage());
        }

    }

}
