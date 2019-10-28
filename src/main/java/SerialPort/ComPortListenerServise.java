package SerialPort;

import com.fazecast.jSerialComm.SerialPort;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

public class ComPortListenerServise extends ScheduledService{
    private SerialPort comPort = null;

    ComPortListenerServise(String comName){
        this.comPort = SerialPort.getCommPort(comName);
    }

    ComPortListenerServise() { this.comPort = null;}

    void setComPortName(String comName){this.comPort = SerialPort.getCommPort(comName);}
    @Override
    protected Task createTask() {
        return  new  Task<String> () {
            @Override
            protected String call() throws Exception {
                if(comPort ==null){System.out.println("dddddddddddddddddd");}
                System.out.println(comPort.getSystemPortName());
                return "bkj";
            }


        };
    }
}
