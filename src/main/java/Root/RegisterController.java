package Root;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles user registration.
 * Displays error messages accordingly.
 */
public class RegisterController extends UserController {

    /**
     * User can register an account after validation and checking if duplicate
     */
    @FXML
    @Override
    protected void onRegisterButtonClick(ActionEvent event) {
        String user = username.getText();
        String pass = password.getText();

        // Validating input.
        if(user.isEmpty()) username_error.setText("Username is empty!");
        else if(user.length() > 20) username_error.setText("Username must be less than 20 characters");
        else username_error.setText("");
        if(pass.isEmpty())password_error.setText("Password is empty!");
        else if(pass.length() > 20) password_error.setText("Password must be less than 20 characters");
        else password_error.setText("");

        if(user.isEmpty() || user.length() > 20 || pass.isEmpty() || pass.length() > 20) return;

        // Checking if inputted username is already taken

        String usernameAvailable = "SELECT username from users where username = ?";

        try{
            Connection conn = getDBConn();
            PreparedStatement statement = conn.prepareStatement(usernameAvailable);
            statement.setString(1, user);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                username_error.setText("Username already exists...");
            }
            else{
                // New user is created
                String createUser = "INSERT INTO users(`username`, `password`) VALUES (?, ?)";
                statement = conn.prepareStatement(createUser);
                statement.setString(1, user);
                statement.setString(2, pass);
                statement.executeUpdate();

                username_error.setText("Successfully Registered User: "+user+"\tPlease login now");
            }



        } catch (SQLException e){
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Switches to login view after registering
     */
    @FXML
    @Override
    protected void onLoginButtonClick(ActionEvent event) {
        try {
            SceneController.switchScene("login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
