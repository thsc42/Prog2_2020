package sensorData;

import streamMachine.StreamMachine;
import transmission.DataConnection;

import java.io.DataInputStream;
import java.io.IOException;

public class SensorDataReceiver {
    private final DataConnection connection;
    private final StreamMachine storage;

    public SensorDataReceiver(DataConnection connection, StreamMachine storage) throws IOException {
        this.connection = connection;
        this.storage = storage;

        SensorDataSetReader reader = new SensorDataSetReader(
                new DataInputStream(connection.getDataInputStream()),
                storage);

        reader.start();
    }

    StreamMachine getStorage() {
        return storage;
    }
}
