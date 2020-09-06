package SerialPort;
import com.fazecast.jSerialComm.SerialPort;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;


public class ComPortListServise extends ScheduledService {

   private ObservableList<String> names;
   private Boolean stop = false;

    ObservableList<String> getName(){return names;}
    void stopServise(Boolean stop){this.stop = stop;}

    @Override
    protected  Task  createTask() {
        return new  Task() {
            @Override
            protected ObservableList<String> call() throws Exception {
                    if(stop){getOnFailed();}//остановка сервиса
                    SerialPort[] serialPort = SerialPort.getCommPorts();
                    names = FXCollections.observableArrayList();
                    for (SerialPort port : serialPort) {
                        names.add(port.getSystemPortName());
                    }
                return null;
            }
        };
    }

}

