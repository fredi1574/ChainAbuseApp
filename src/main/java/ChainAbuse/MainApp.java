package ChainAbuse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ChainAbuse/MainView.fxml"));
        Parent root = loader.load();

        MainController controller = loader.getController();
        controller.setHostServices(getHostServices());

        primaryStage.setTitle("Bitcoin Address Scanner");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }
}
