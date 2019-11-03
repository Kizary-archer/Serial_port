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

    private static Scene scene;
     private static Stage primaryStage;
     static  Stage getStage(){return primaryStage;}
    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        scene = new Scene(loadFXML("serialmonitor"));
       // scene.getStylesheets().add(getClass().getResource("monitorStyle.css").toExternalForm());
        stage.setScene(scene);
       // stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        comSelecter();
        }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getClassLoader().getResource (fxml + ".fxml"));
        return fxmlLoader.load();
    }

     static void comSelecter(){
             Stage rootStage = getStage();
             // New window (Stage)
             Stage comSelectWindow = new Stage();
             Scene scene = null;
             try {
                 scene = new Scene(loadFXML("comselecter"));
             } catch (IOException e) {
                 e.printStackTrace();
             }
             comSelectWindow.setScene(scene);
         // Specifies the modality for new window.
             comSelectWindow.initModality(Modality.WINDOW_MODAL);
             comSelectWindow.initStyle(StageStyle.UNDECORATED);
             // Specifies the owner Window (parent) for new window
             comSelectWindow.initOwner(rootStage);
             comSelectWindow.show();

    }
    public static void main(String[] args) {
        launch();
    }

}