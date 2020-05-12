package sensorData;

import streamMachine.PersistenceException;
import streamMachine.StreamMachine;

import java.io.DataInputStream;
import java.io.IOException;

class SensorDataSetReader extends Thread {

    private final DataInputStream dis;
    private final StreamMachine storage;

    SensorDataSetReader(DataInputStream dis, StreamMachine storage) {
        this.dis = dis;
        this.storage = storage;
    }

    public void run() {
        // read from tcp
        try {
            String name = dis.readUTF();
            long time = dis.readLong();
            int len = dis.readInt();
            float[] values = new float[len];
            for(int i = 0; i < len; i++) {
                values[i] = dis.readFloat();
            }

            // write into machine
            this.storage.saveData(time, values);

        } catch (IOException | PersistenceException e) {
            // create error log message
            System.err.println("problems when reading / wridanating sensor data");
        }
    }
}
