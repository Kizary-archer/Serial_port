package SerialPort;


import com.fazecast.jSerialComm.SerialPort;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SerialmonitorController implements Initializable {
    @FXML
    VBox VBoxMainMonitor;
    @FXML
    TextField TextField1 = new TextField();
    @FXML
    volatile TextArea TextArea1 = new TextArea();
    @FXML
    Parent root;

    private static String comPortName;

    public static void setComPortName(String name){comPortName = name;}

    @Override
    public void initialize(URL location, ResourceBundle resources) {

       // SerialPort comPort = SerialPort.getCommPort("COM4");
          //  comPort.openPort();
          //  comPort.setComPortParameters(115200, 8, 1, SerialPort.NO_PARITY);
            //showAlertWithoutHeaderText();

    }

    public void enterButtonClick(ActionEvent actionEvent) throws IOException {//отправка данных
       // TextArea1.clear();
        //byte[] byteData = TextField1.getText().getBytes();
       // comPort.writeBytes(byteData, byteData.length);
        TextArea1.appendText(comPortName);

    }
    public void settingClick(MouseEvent actionEvent) throws IOException, InterruptedException {
        ComPortServise comPortServise = new ComPortServise();
        if (VBoxMainMonitor.getChildren().size()>2) {
            VBoxMainMonitor.getChildren().remove(2);
            comPortServise.cancel();
            return;
        }
        ComboBox<String> ComboBox1 = new ComboBox<String>();
        ComboBox1.setValue(comPortName);
        ComboBox<String> ComboBox2 = new ComboBox<String>();
        HBox hBox = new HBox(ComboBox1,ComboBox2);

        VBoxMainMonitor.getChildren().add(hBox);
      comPortServise.setPeriod(Duration.seconds(1));
        comPortServise.setOnSucceeded(e -> {
            ComboBox1.setItems(comPortServise.getName());
        });
        comPortServise.start();
        ObservableList<String> comList = FXCollections.observableArrayList("Java", "JavaScript", "C#", "Python");
        ComboBox2.setItems(comList);
           }
}
