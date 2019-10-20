package SerialPort;
import com.fazecast.jSerialComm.SerialPort;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;


public class  ComPortServise extends Service {

    ObservableList<String> names = FXCollections.observableArrayList();
    public ObservableList<String> getName(){
       return names;
    }
    @Override
    protected  Task  createTask() {
        return new  Task() {
            @Override
            protected ObservableList<String> call() throws Exception {
                SerialPort[] serialPort = SerialPort.getCommPorts();

                for (SerialPort port : serialPort) {
                    names.add(port.getSystemPortName());
                }

                return names;
            }

            @Override protected void succeeded() {
                super.succeeded();
                updateMessage("Done!");
                System.out.println("aa");
            }

            @Override protected void cancelled() {
                super.cancelled();
                updateMessage("Cancelled!");
                System.out.println("CCCC");
            }

            @Override protected void failed() {
                super.failed();
                updateMessage("Failed!");
            }

        };
    }
}

