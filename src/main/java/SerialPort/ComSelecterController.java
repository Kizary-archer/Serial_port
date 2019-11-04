package SerialPort;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.net.URL;
import java.util.ResourceBundle;




public class ComSelecterController implements Initializable {

    @FXML
    ListView<String> ListView1;
    @FXML
    Label title;

   private SerialmonitorController serialmonitorController;
   private ComPortListServise comPortListServise = new ComPortListServise();

    void setParentController(SerialmonitorController serialmonitorController) {
        this.serialmonitorController = serialmonitorController;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //создание сервиса со списком COM портов
        comPortListServise.setRestartOnFailure(false);
        comPortListServise.setOnSucceeded(event -> {
            ListView1.setItems(comPortListServise.getName());
        });
        comPortListServise.setOnFailed(e->{
            System.out.println("qqqqqqqqqqqqq");
        });
        comPortListServise.setPeriod(Duration.seconds(1));//обновление списка каждую секунду
        comPortListServise.start();

    }

    @FXML
    private void ClickListView() {
        if(ListView1.getSelectionModel().getSelectedItem() == null) return;
        comPortListServise.stopServise(true);
        serialmonitorController.setComPort(ListView1.getSelectionModel().getSelectedItem());
        Stage stage = (Stage)ListView1.getScene().getWindow();
        stage.close();
    }
}



