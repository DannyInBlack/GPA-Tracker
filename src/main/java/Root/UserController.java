package Root;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public abstract class UserController extends DB {
    @FXML
    protected TextField username, password;
    @FXML
    protected Label username_error, password_error;

    @FXML
    abstract protected void onLoginButtonClick(ActionEvent event);

    @FXML
    abstract protected void onRegisterButtonClick(ActionEvent event);
}
