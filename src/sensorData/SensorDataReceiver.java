package sensorData;

import streamMachine.PersistenceException;
import streamMachine.StreamMachine;
import transmission.DataConnection;

import java.io.DataInputStream;
import java.io.IOException;

public class SensorDataReceiver {
    private final DataConnection connection;
    private final StreamMachine storage;

    public SensorDataReceiver(DataConnection connection, StreamMachine storage) {
        this.connection = connection;
        this.storage = storage;
    }

    private void readDataSet() throws IOException, PersistenceException {
        DataInputStream dis = this.connection.getDataInputStream();

        // read from tcp
        String name = dis.readUTF();
        long time = dis.readLong();
        int len = dis.readInt();
        float[] values = new float[len];
        for(int i = 0; i < len; i++) {
            values[i] = dis.readFloat();
        }

        // write into machine
        this.storage.saveData(time, values);
    }

    StreamMachine getStorage() {
        return storage;
    }
}
