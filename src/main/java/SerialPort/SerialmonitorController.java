package SerialPort;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    ListView<String> ListView1 = new ListView<String>();

    private String comPortName = null;
    private int baundRate = 115200; //скорость порта
    private ComPortListenerServise comPortListenerServise = new ComPortListenerServise();
    private ObservableList<String> ComLog = FXCollections.observableArrayList();



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ListView1.setItems(ComLog);
        comPortListenerServise.setPeriod(Duration.millis(100));
        comPortListenerServise.setRestartOnFailure(false); //при ошибке сервис не автозапускается
        comPortListenerServise.setOnSucceeded(e->{ //событие срабатывает при нормальной работе порта
            if (!((String) comPortListenerServise.getValue()).isEmpty())//пустой вывод сервиса не обрабатывается
            {
                String outStr = (String) comPortListenerServise.getValue();//данные полученные с COM порта
                outStr = outStr.replaceAll("[\r]","");//удаляем лишние спецсимволы
                if (ComLog.size()!=0){
                   String lastStr = ComLog.get(ComLog.size()-1); //последняя строка лога
                    if(lastStr.endsWith("\f")){//если строка заканчивается символом, добавляем недостающую часть
                        lastStr = lastStr.replaceAll("[\f]","");//удаляем лишние спецсимволы
                        String str; //строка склейки
                        if(outStr.contains("\n"))
                        {
                            str = outStr.substring(0, outStr.indexOf("\n"));//если есть символ конца строки, режем до него
                            outStr = outStr.substring(outStr.indexOf("\n") + 1, outStr.length()); //удаляем приклеенную часть из основной строки
                        }
                        else {
                            str = outStr;//если нет, берём всю строку
                            outStr = "";
                        }
                            ComLog.set(ComLog.size() - 1, lastStr.concat(str));//приклеиваеи недостающую часть
                    }
                }
               if(!outStr.isEmpty()) {
                   ComLog.addAll(outStr.split("\n"));//формируем строки и выводим их
               }
               if(!outStr.endsWith("\n")){
                   ComLog.set(ComLog.size() - 1,ComLog.get(ComLog.size()-1)+"\f");//добавляем пробел к последней строке если не обнаружен её конец
               }
            }
        });
        comPortListenerServise.setOnFailed(e->{
            ComLog.add("порт "+ comPortName +" аварийно завершил работу");
            comPortName = null;
            try {
                App.comSelecter(this);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
    void setComPort(String name){//сервис слушатель стартует при установке/переустановке порта
        this.comPortName = name;
        comPortListenerServise.setComPort(name,baundRate);
        comPortListenerServise.restart();
        ComLog.add("Подключение к порту "+ comPortName);
    }

    public void enterButtonClick(ActionEvent actionEvent) {//отправка данных в порт
     if(TextField1.getLength() == 0)return;
     comPortListenerServise.writeComPort(TextField1.getText());
     TextField1.clear();

    }
    public void settingClick(MouseEvent actionEvent) {
        if (VBoxMainMonitor.getChildren().size()>2) {
            VBoxMainMonitor.getChildren().remove(2);
            return;
        }
        ComboBox<String> comboboxBaundRate = new ComboBox<String>();
        ImageView imgViewPortSell = new ImageView();
        ImageView imgViewClear = new ImageView();
        HBox hBox = new HBox(comboboxBaundRate,imgViewPortSell,imgViewClear);
        hBox.setPadding(new Insets(0,10,10,10));
        hBox.setSpacing(10);
        VBoxMainMonitor.getChildren().add(hBox);
        ///////////////comboboxBaundRate////////////
        ObservableList<String> comList = FXCollections.observableArrayList(
                                                                     "300",
                                                                            "1200",
                                                                            "2400",
                                                                            "4800",
                                                                            "9600",
                                                                            "19200",
                                                                            "38400",
                                                                            "57600",
                                                                            "115200",
                                                                            "230400",
                                                                            "250000",
                                                                            "500000",
                                                                            "1000000",
                                                                            "2000000"
        );
        comboboxBaundRate.setItems(comList);
        comboboxBaundRate.setValue(Integer.toString(baundRate));
        comboboxBaundRate.setOnAction(e->{
           baundRate = Integer.parseInt(comboboxBaundRate.getSelectionModel().getSelectedItem());
           comPortListenerServise.setBaundRate(baundRate);
           ComLog.add("Скорость порта изменена на "+baundRate+" бод\n");
        });
        ////////////////////////////////////////////

        ////////////////imgViewPortSell/////////////
        Image setImg = new Image(getClass().getResourceAsStream("/img/port.png"));
        imgViewPortSell.setImage(setImg);
        imgViewPortSell.setOnMouseClicked(e -> {
            this.comPortName = null;
            try {
                App.comSelecter(this);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        ///////////////////////////////////////////

        ////////////////imgViewClear///////////////
        Image clearImg = new Image(getClass().getResourceAsStream("/img/clear.png"));
        imgViewClear.setImage(clearImg);
        imgViewClear.setOnMouseClicked(e -> {
            ComLog.clear();
        });
        ///////////////////////////////////////////
    }

    public void TextField1keyPresed(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) enterButtonClick(new ActionEvent());//если нажат enter, генерируем событие нажатия кнопки ENTER
    }
}
