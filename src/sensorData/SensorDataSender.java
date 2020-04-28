package sensorData;

import transmission.DataConnection;

import java.io.DataOutputStream;
import java.io.IOException;

public class SensorDataSender {
    private final DataConnection connection;

    public SensorDataSender(DataConnection connection) {
        this.connection = connection;
    }

    public void sendData(String name, long time, float[] values) throws IOException {
        if(name == null
           || time < 0
           || values == null | values.length == 0)
            throw new IOException("parameter must not be null or empty");

        DataOutputStream dos = this.connection.getDataOutputStream();

        dos.writeUTF(name);
        dos.writeLong(time);
        dos.writeInt(values.length);
        for(int i = 0; i < values.length; i++) {
            dos.writeFloat(values[i]);
        }
    }
}
