package sensorData;

import filepersistence.SensorDataStorage;
import transmission.DataConnection;

public class SensorDataReceiver {
    private final DataConnection connection;
    private final SensorDataStorage storage;

    public SensorDataReceiver(DataConnection connection, SensorDataStorage storage) {
        this.connection = connection;
        this.storage = storage;
    }

    SensorDataStorage getStorage() {
        return storage;
    }
}
