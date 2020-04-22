package sensorData;

import streamMachine.StreamMachine;
import transmission.DataConnection;

public class SensorDataReceiver {
    private final DataConnection connection;
    private final StreamMachine storage;

    public SensorDataReceiver(DataConnection connection, StreamMachine storage) {
        this.connection = connection;
        this.storage = storage;
    }

    StreamMachine getStorage() {
        return storage;
    }
}
