package SerialPort;


import com.fazecast.jSerialComm.SerialPort;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

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

    SerialPort comport;
    Stage stage = new Stage();
   //private volatile ObservableList ComlistName;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

       // SerialPort comPort = SerialPort.getCommPort("COM4");
          //  comPort.openPort();
          //  comPort.setComPortParameters(115200, 8, 1, SerialPort.NO_PARITY);
            //showAlertWithoutHeaderText();
    }
    public void enterButtonClick(ActionEvent actionEvent) {//отправка данных
       // TextArea1.clear();
        //byte[] byteData = TextField1.getText().getBytes();
       // comPort.writeBytes(byteData, byteData.length);
        Stage stage = (Stage) TextArea1.getScene().getWindow();
        Label secondLabel = new Label("I'm a Label on new Window");

        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().add(secondLabel);

        Scene secondScene = new Scene(secondaryLayout, 230, 100);

        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle("Second Stage");
        newWindow.setScene(secondScene);

        // Specifies the modality for new window.
        newWindow.initModality(Modality.WINDOW_MODAL);

        // Specifies the owner Window (parent) for new window
        newWindow.initOwner(stage);

        // Set position of second window, related to primary window.
        newWindow.setX(stage.getX() + 200);
        newWindow.setY(stage.getY() + 100);

        newWindow.show();

    }

    // Show a Warning Alert without Header Text
    private  void showAlertWithoutHeaderText() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Warning alert");
        alert.setHeaderText(null);
        alert.setContentText("COM port not detected");

        alert.show();
    }

    public void settingClick(MouseEvent actionEvent) throws IOException, InterruptedException {
        if (VBoxMainMonitor.getChildren().size()>2) {
            VBoxMainMonitor.getChildren().remove(2);
            return;

        }
        ComboBox<String> ComboBox1 = new ComboBox<String>();
        ComboBox<String> ComboBox2 = new ComboBox<String>();
        ComPortServise comPortServise = new ComPortServise();
        comPortServise.setOnSucceeded(e -> {
             ComboBox2.setItems(comPortServise.getName());
        });
        comPortServise.start();
        HBox hBox = new HBox(ComboBox1,ComboBox2);
        VBoxMainMonitor.getChildren().add(hBox);
        ObservableList<String> comList = FXCollections.observableArrayList("Java", "JavaScript", "C#", "Python");
        ComboBox1.setItems(comList);
           }
}
