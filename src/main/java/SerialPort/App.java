package SerialPort;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Stage primaryStage;
    static Stage getStage(){return primaryStage;}

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getClassLoader().getResource ("serialmonitor.fxml"));
        Scene scene = new Scene((Parent) fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
        SerialmonitorController serialmonitorController = fxmlLoader.<SerialmonitorController>getController();
        comSelecter(serialmonitorController);
        }

    static void comSelecter(SerialmonitorController serialmonitorController) throws IOException {
         Stage rootStage = getStage();
         // New window (Stage)
         Stage comSelectWindow = new Stage();
         FXMLLoader fxmlLoader = new FXMLLoader(App.class.getClassLoader().getResource ("comselecter.fxml"));
         Scene scene = new Scene(fxmlLoader.load());
         comSelectWindow.setScene(scene);
         // Specifies the modality for new window.
         comSelectWindow.initModality(Modality.WINDOW_MODAL);
         comSelectWindow.initStyle(StageStyle.UNDECORATED);
         // Specifies the owner Window (parent) for new window
         comSelectWindow.initOwner(rootStage);
         comSelectWindow.show();
         ComSelecterController comSelecterController = fxmlLoader.<ComSelecterController>getController();
         comSelecterController.setParentController(serialmonitorController);
    }

    public static void main(String[] args) {
        launch();
    }

}