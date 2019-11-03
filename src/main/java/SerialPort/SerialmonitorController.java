package SerialPort;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
    ListView<String> ListView1 = new ListView<String>();

    private String comPortName = null;
    private ComPortListenerServise comPortListenerServise = new ComPortListenerServise();
    private ObservableList<String> ComLog = FXCollections.observableArrayList();

    void setComPortName(String name){comPortName = name;}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ListView1.setItems(ComLog);

    }
    void startListenServise()
    {
        comPortListenerServise.setOnSucceeded(e->{ //событие срабатывает при нормальной работе порта
            if (comPortName == null)comPortListenerServise.setComPort(comPortName);
            else {
                String str = (String) comPortListenerServise.getValue();
                String[] strResMas = str.split("\n");
                comPortListenerServise.setPeriod(Duration.millis(100));
            }
        });
        comPortListenerServise.setOnFailed(e->{
            comPortName = null;
            App.comSelecter();
        });
        comPortListenerServise.start();
    }

    public void enterButtonClick(ActionEvent actionEvent) throws IOException {//отправка данных
     comPortListenerServise.writeComPort(TextField1.getText());
     TextField1.clear();
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

        ComboBox1.setOnMousePressed(e ->{
            System.out.println("sfsdfsdf");
        });
           }

}
