package SerialPort;

import com.fazecast.jSerialComm.SerialPort;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

import java.util.Arrays;

public class ComPortListenerServise extends ScheduledService{

    private SerialPort comPort = null;

    ComPortListenerServise() {}

    void setBaundRate(int baundRate){comPort.setBaudRate(baundRate);}

    void setComPort(String comName,int BaundRate){
        try {
            if (comPort != null) comPort.closePort();
            comPort = SerialPort.getCommPort(comName);
            comPort.openPort();
            comPort.setComPortParameters(BaundRate, 8, 1, SerialPort.NO_PARITY);
            }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    void writeComPort(String out){
        if (comPort == null) return;
        byte[] byteData = out.getBytes();
        comPort.writeBytes(byteData, byteData.length);
    }
    @Override
    protected Task createTask() {
        return  new  Task<String> () {
            @Override
            protected String call() throws Exception {
                    try {
                        if (comPort.bytesAvailable() != 0) {
                            byte[] readBuffer = new byte[comPort.bytesAvailable()];
                            comPort.readBytes(readBuffer, readBuffer.length);
                            return new String(readBuffer);
                        }
                    } catch (Exception e) {//при ошибке порта инициализируем порт заново
                        e.printStackTrace();
                        comPort = null;
                        getOnFailed();
                    }

                return "";
            }
        };
    }
}
