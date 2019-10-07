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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class serialmonitorController implements Initializable {


    @FXML
    TextField TextField1 = new TextField();
    @FXML
    volatile TextArea TextArea1 = new TextArea();

    private SerialPort comPort;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

            comPort = SerialPort.getCommPort("COM4");
            comPort.openPort();
            comPort.setComPortParameters(115200, 8, 1, SerialPort.NO_PARITY);
            comPort.addDataListener(new SerialPortDataListener() { //отслеживание события наличия данных в буфере
                @Override
                public int getListeningEvents() {
                    return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
                }

                @Override
                public void serialEvent(SerialPortEvent event) {
                    if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
                        return;

                    byte[] newData = new byte[comPort.bytesAvailable()];
                    comPort.readBytes(newData, newData.length);
                    String ComData = new String(newData);
                   Platform.runLater(() ->{TextArea1.appendText(ComData);});
                    System.out.println(ComData);
                }
            });

            //showAlertWithoutHeaderText();


    }
    public void EnterButtonClick(ActionEvent actionEvent) {//отправка данных
        TextArea1.clear();
        byte[] byteData = TextField1.getText().getBytes();
        comPort.writeBytes(byteData, byteData.length);
    }
    // Show a Warning Alert without Header Text
    private  void showAlertWithoutHeaderText() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Warning alert");
        alert.setHeaderText(null);
        alert.setContentText("COM port not detected");

        alert.show();
    }
}
