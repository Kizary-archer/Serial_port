package SerialPort;
import com.fazecast.jSerialComm.*;
public class seriallist {

    public static String[] getSerialPortNames() {

        SerialPort[] commPorts = SerialPort.getCommPorts();
        String[] names = new String[commPorts.length];

        for (int i = 0; i < commPorts.length; i++)
            names[i] = commPorts[i].getSystemPortName();

        return names;
    }
}
