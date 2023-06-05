package Root;

import javafx.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteSubjectsController extends SubjectController{

    /**
     * Deletes every grade that belongs to the user as well as their history.
     */
    public void resetData(ActionEvent event){
        try {
            Connection conn = getDBConn();
            String deleteGrades = "DELETE FROM grades WHERE username = ?";
            String deleteHistory = "DELETE FROM gpa_history WHERE username = ?";
            PreparedStatement statement1 = conn.prepareStatement(deleteGrades);
            PreparedStatement statement2 = conn.prepareStatement(deleteHistory);
            statement1.setString(1, SceneController.getUser());
            statement2.setString(1, SceneController.getUser());
            statement1.executeUpdate();
            statement2.executeUpdate();
            onCancelClick(event);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
