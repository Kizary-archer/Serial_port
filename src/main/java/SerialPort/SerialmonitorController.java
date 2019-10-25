package SerialPort;


import com.fazecast.jSerialComm.SerialPort;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    private ComPortListenerServise comPortListenerServise;

    static void setComPortName(String name){comPortName = name;}

    @Override
    public void initialize(URL location, ResourceBundle resources) {

       // SerialPort comPort = SerialPort.getCommPort("COM4");
          //  comPort.openPort();
          //  comPort.setComPortParameters(115200, 8, 1, SerialPort.NO_PARITY);
            //showAlertWithoutHeaderText();
        comPortListenerServise = new ComPortListenerServise("COM3");
        comPortListenerServise.setOnSucceeded(e->{
            TextArea1.appendText((String)comPortListenerServise.getValue());
        });
        comPortListenerServise.start();


    }

    public void enterButtonClick(ActionEvent actionEvent) throws IOException {//отправка данных
       // TextArea1.clear();
        //byte[] byteData = TextField1.getText().getBytes();
       // comPort.writeBytes(byteData, byteData.length);
        TextArea1.appendText(comPortName);
        ComPortListenerServise comPortListener = new ComPortListenerServise(comPortName);

    }
    public void settingClick(MouseEvent actionEvent) throws IOException, InterruptedException {
        ComPortListServise comPortListServise = new ComPortListServise();
        if (VBoxMainMonitor.getChildren().size()>2) {
            VBoxMainMonitor.getChildren().remove(2);
            comPortListServise.cancel();
            return;
        }
        ComboBox<String> ComboBox1 = new ComboBox<String>();
        ComboBox1.setValue(comPortName);
        ComboBox<String> ComboBox2 = new ComboBox<String>();
        HBox hBox = new HBox(ComboBox1,ComboBox2);

        VBoxMainMonitor.getChildren().add(hBox);
      comPortListServise.setPeriod(Duration.seconds(1));
        comPortListServise.setOnSucceeded(e -> {
            ComboBox1.setItems(comPortListServise.getName());
            ComboBox1.setValue(comPortName);
        });
        comPortListServise.start();
        ObservableList<String> comList = FXCollections.observableArrayList("4800", "9600", "115200");
        ComboBox2.setItems(comList);
        ComboBox2.setValue("9600");
           }

}
