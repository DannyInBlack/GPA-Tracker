package Root;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Abstract class containing the cancel button - saves time for coding
 */
abstract class SubjectController extends DB{
    @FXML
    protected void onCancelClick(ActionEvent event){
        SceneController.closeStage(event);
    }
}
