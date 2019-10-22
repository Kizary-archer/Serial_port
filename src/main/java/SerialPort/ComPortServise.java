package SerialPort;
import com.fazecast.jSerialComm.SerialPort;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;


public class  ComPortServise extends ScheduledService {

   private ObservableList<String> names;
    ObservableList<String> getName(){return names;}

    @Override
    protected  Task  createTask() {
        return new  Task() {
            @Override
            protected ObservableList<String> call() throws Exception {
                    SerialPort[] serialPort = SerialPort.getCommPorts();
                    names = FXCollections.observableArrayList();
                    for (SerialPort port : serialPort) {
                        names.add(port.getSystemPortName());
                    }

                return names;
            }
        };
    }
}

