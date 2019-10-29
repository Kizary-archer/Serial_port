package SerialPort;

import com.fazecast.jSerialComm.SerialPort;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

public class ComPortListenerServise extends ScheduledService{

    private SerialPort comPort = null;

    ComPortListenerServise(String comName){setComPort(comName);}

    ComPortListenerServise() {}

    void setComPort(String comName){
        try {
            if (comPort != null) comPort.closePort();
            comPort = SerialPort.getCommPort(comName);
            comPort.openPort();
            comPort.setComPortParameters(115200, 8, 1, SerialPort.NO_PARITY);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    protected Task createTask() {
        return  new  Task<String> () {
            @Override
            protected String call() throws Exception {
                if (!comPort.isOpen()){
                getOnFailed();
                return "";
                }

                return "bkj";
            }


        };
    }
}
