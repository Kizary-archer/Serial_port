package SerialPort;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;




public class ComSelecterController implements Initializable {

    @FXML
    ListView<String> ListView1;
    @FXML
    Label title;
    @FXML
    Button button;

   private ComPortServise comPortServise = new ComPortServise();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //создание сервиса со списком COM портов
        comPortServise.setOnSucceeded(event -> {
            ListView1.setItems(comPortServise.getName());
        });
        comPortServise.setPeriod(Duration.seconds(1));//обновление списка каждую секунду
        comPortServise.start();
    }

    @FXML
    private void ClickListView() {
        if(ListView1.getSelectionModel().getSelectedItem() == null) return;
        comPortServise.cancel();
        SerialmonitorController.setComPortName(ListView1.getSelectionModel().getSelectedItem());
        Stage stage = (Stage)ListView1.getScene().getWindow();
        stage.close();
    }
}



