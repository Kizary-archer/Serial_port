package SerialPort;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;




public class ComSelecterController implements Initializable {

    @FXML
    ListView<String> ListView1;
    @FXML
    Label title;

   private SerialmonitorController serialmonitorController;
   private ComPortListServise comPortListServise = new ComPortListServise();
   ComSelecterController(SerialmonitorController serialmonitorController){
    this.serialmonitorController = serialmonitorController;
    Stage stage = new Stage();
       // Load the FXML file
       try {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("comselecter.fxml"));
           // Set this class as the controller
          // loader.setController(this);
           // Load the scene
           Scene scene = new Scene(App.loadFXML("comselecter"));
           stage.setScene(scene);
       } catch (IOException e) {
           e.printStackTrace();
       }
       // Specifies the modality for new window.
       stage.initModality(Modality.WINDOW_MODAL);
       stage.initStyle(StageStyle.UNDECORATED);
       // Specifies the owner Window (parent) for new window
       stage.initOwner(App.getStage());
   }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //создание сервиса со списком COM портов
        comPortListServise.setOnSucceeded(event -> {
            ListView1.setItems(comPortListServise.getName());
        });
        comPortListServise.setPeriod(Duration.seconds(1));//обновление списка каждую секунду
        comPortListServise.start();

    }

    @FXML
    private void ClickListView() {

        if(ListView1.getSelectionModel().getSelectedItem() == null) return;
        comPortListServise.cancel();
        serialmonitorController.setComPort(ListView1.getSelectionModel().getSelectedItem());
        Stage stage = (Stage)ListView1.getScene().getWindow();
        stage.close();
    }
}



