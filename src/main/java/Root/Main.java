package Root;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        SceneController.setMainStage(stage);
        SceneController.switchScene("login");
    }

    public static void main(String[] args) {
        launch();
    }
}
