package Root;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;

/**
 * Handles most scene and stage changes, keeps track of the current user
*/
public class SceneController {

    private static String user = "";
    private static Stage mainStage;

    public static void setMainStage(Stage mainStage) {
        SceneController.mainStage = mainStage;
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public static void setUser(String user) {
        SceneController.user = user;
    }

    public static String getUser() {
        return user;
    }

    /**
     * Loads a fxml scene with name that matches title, case-sensitive.
     *
     * @param title Used to load the scene and set the name for the stage
     * @throws IOException In case loader fails to load the scene
     */
    public static void switchScene(String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource(title+"-view.fxml"));
        Scene scene = new Scene(loader.load());
        mainStage.setScene(scene);
        mainStage.setTitle(title);
        mainStage.show();
    }

    /**
     * Changes the mainStage's scene with another scene
     *
     * @param title Title of the modified stage
     * @param scene Scene to be inserted
     */
    public static void switchScene(String title, Scene scene) {
        mainStage.setScene(scene);
        mainStage.setTitle(title);
        mainStage.show();
    }

    /**
     * Creates a new window with a scene
     * @param scene Scene of the new window
     * @param title Title of the new window
     */
    public static void addStage(Scene scene, String title){
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }

    /**
     * Creates a new Window with a scene that is loaded from the title using fxml, case-sensitive.
     * @param title Name of the fxml file
     * @throws IOException In case fxml doesn't load
     */
    public static void addStage(String title) throws IOException{
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource(title+"-view.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setTitle(title);

        stage.show();
    }

    /**
     * Closes the stage from which a button was clicked
     * @param event Button event that triggered the closing
     */
    public static void closeStage(ActionEvent event){
        ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    }


}
