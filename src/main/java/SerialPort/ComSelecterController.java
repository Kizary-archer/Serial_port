package SerialPort;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

import java.net.URL;
import java.util.ResourceBundle;




public class ComSelecterController implements Initializable {

    @FXML
    ListView<String> ListView1;
    @FXML
    Label title;
    @FXML
    Button button;

    protected static String ComPort = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // получаем модель выбора элементов
        ListView1.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    }

    @FXML
    private void ClickListView() {

        ComPort = ListView1.getSelectionModel().getSelectedItem().toString();
        //System.out.println(ComPort);
    }
}



