package sensorData;

import streamMachine.StreamMachine;
import org.junit.Assert;
import org.junit.Test;
import transmission.DataConnection;
import transmission.DataConnector;

import java.io.IOException;

public class SensorDataTransmissionTests {
    private static final int PORTNUMBER = 9876;

    @Test
    public void gutTransmissionTest() throws IOException {
        // create example data set
        String sensorName = "MyGoodOldSensor"; // does not change
        long timeStamp = System.currentTimeMillis();
        float[] valueSet = new float[3];
        valueSet[0] = (float) 0.7;
        valueSet[1] = (float) 1.2;
        valueSet[2] = (float) 2.1;

        ///////////////////////////////////////////////////////////////////////////////////////////////////////
        //                                              receiver side                                        //
        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        // create storage
        // TODO: create object that implements SensorDataStorage
        StreamMachine dataStorage = null;

        // create connections
        DataConnection receiverConnection = new DataConnector("localhost", PORTNUMBER);

        // create receiver
        SensorDataReceiver sensorDataReceiver = new SensorDataReceiver(receiverConnection, dataStorage);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////
        //                                              sender side                                          //
        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        // create connections
        DataConnection senderConnection = new DataConnector(PORTNUMBER);

        // create sender
        SensorDataSender sensorDataSender = new SensorDataSender(senderConnection);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////
        //                               execute communication and test                                      //
        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        // send data with TCP
        sensorDataSender.sendData(sensorName, timeStamp, valueSet);

        // test if stored
        StreamMachine dataStorageReceived = sensorDataReceiver.getStorage();

        // TODO - get data and test

        // just dummy values
        String sensorNameReceived = "dummy";
        long timeStampReceived = 0; // dummy
        float[] valueSetReceived = new float[3];

        // test
        Assert.assertEquals(sensorName, sensorNameReceived);
        Assert.assertEquals(timeStamp, timeStampReceived);
        // TODO: test values
    }
}
