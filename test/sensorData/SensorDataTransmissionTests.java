package sensorData;

import streamMachine.PersistenceException;
import streamMachine.SensorDataSet;
import streamMachine.StreamMachine;
import org.junit.Assert;
import org.junit.Test;
import streamMachine.StreamMachineFS;
import transmission.DataConnection;
import transmission.DataConnector;

import java.io.IOException;

public class SensorDataTransmissionTests {
    private static final int PORTNUMBER = 9876;
    private static final String SENSOR_NAME = "goodOldSensor";

    @Test
    public void gutTransmissionTest() throws IOException, PersistenceException, InterruptedException {
        // create example data set
        long timeStamp = System.currentTimeMillis();
        float[] valueSet = new float[3];
        valueSet[0] = (float) 0.7;
        valueSet[1] = (float) 1.2;
        valueSet[2] = (float) 2.1;

        // create storage - for layer 7 server
        StreamMachine dataStorage = new StreamMachineFS(SENSOR_NAME);
        dataStorage.clean();

        // create (layer 4: tcp) server
        DataConnection receiverConnection = new DataConnector(PORTNUMBER);

        // create (layer 4) client and connect
        DataConnection senderConnection = new DataConnector("localhost", PORTNUMBER);

        // create (layer 7: our sensor data protocol) sender
        SensorDataSender sensorDataSender = new SensorDataSender(senderConnection);

        // create layer 7 receiver and establish layer 7 connection
        SensorDataReceiver sensorDataReceiver = new SensorDataReceiver(receiverConnection, dataStorage);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////
        //                               execute communication and test                                      //
        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        // issue layer 7 command - which uses tcp as layer 4 protocol
        sensorDataSender.sendData(SENSOR_NAME, timeStamp, valueSet);

        // stop unit test thread a moment to finish transmission
        Thread.sleep(1);

        // get receiver storage
        StreamMachine dataStorageReceived = sensorDataReceiver.getStorage();

        // get received data
        SensorDataSet dataSet = dataStorageReceived.getDataSet(0);
        long timeStampReceived = dataSet.getTime();
        float[] valueSetReceived = dataSet.getValues();

        // test
        Assert.assertEquals(timeStamp, timeStampReceived);
        Assert.assertArrayEquals(valueSet, valueSetReceived, 0);
    }
}
