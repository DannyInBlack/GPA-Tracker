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


public class LoginController extends UserController {

    /**
     * Logs in user.
     * @param event Used to switch to home view.
     */
    @FXML
    @Override
    public void onLoginButtonClick(ActionEvent event) {
        String user = username.getText();
        String pass = password.getText();

        // Input validation.
        if(user.isEmpty()) username_error.setText("Username is empty!");
        else username_error.setText("");
        if(pass.isEmpty())password_error.setText("Password is empty!");
        else password_error.setText("");

        if(user.isEmpty() || pass.isEmpty()) return;

        // Checks if there exists a username with a matching password
        String sql_query = "SELECT * FROM `users` WHERE username = ? and password = ?";

        try{
            Connection conn = getDBConn();
            PreparedStatement statement = conn.prepareStatement(sql_query);
            statement.setString(1, user);
            statement.setString(2, pass);
            ResultSet result = statement.executeQuery();
            if(!result.next()) { // No user found
                username_error.setText("Username or password is incorrect...");
                return;
            }
            try{
                SceneController.setUser(user); // Now user can be accessed throughout the app
                SceneController.switchScene("home");
            } catch (IOException e){
                e.printStackTrace();
            }

        } catch (SQLException e){
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Switches to register view
     */
    @FXML
    @Override
    public void onRegisterButtonClick(ActionEvent event) {
        try {
            SceneController.switchScene("register");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}