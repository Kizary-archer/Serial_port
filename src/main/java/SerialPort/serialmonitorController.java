package SerialPort;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Observable;



import java.net.URL;
import java.util.ResourceBundle;

import static SerialPort.App.setRoot;

public class serialmonitorController implements Initializable {

    @FXML
    Button settingsButton;
    @FXML
    TextField TextField1 = new TextField();
    @FXML
    volatile TextArea TextArea1 = new TextArea();

    private SerialPort comPort;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //comPort = SerialPort.getCommPort("COM4");
          //  comPort.openPort();
          //  comPort.setComPortParameters(115200, 8, 1, SerialPort.NO_PARITY);
            //showAlertWithoutHeaderText();
    }
    public void enterButtonClick(ActionEvent actionEvent) {//отправка данных
        TextArea1.clear();
        byte[] byteData = TextField1.getText().getBytes();
       // comPort.writeBytes(byteData, byteData.length);

    }

    // Show a Warning Alert without Header Text
    private  void showAlertWithoutHeaderText() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Warning alert");
        alert.setHeaderText(null);
        alert.setContentText("COM port not detected");

        alert.show();
    }

    public void settingButtonClick(ActionEvent actionEvent) {
    }
}
