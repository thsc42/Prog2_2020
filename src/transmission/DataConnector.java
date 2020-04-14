package transmission;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class DataConnector implements DataConnection {

    /**
     * Create client side - open connection to address / port
     * @param address
     */
    public DataConnector(String address, int port) {
        // TODO
    }

    /**
     * Create server side - open port on this port and wait for one client
     * @param port
     */
    public DataConnector(int port) {
        // TODO
    }

    @Override
    public DataInputStream getDataInputStream() throws IOException {
        return null; // TODO
    }

    @Override
    public DataOutputStream getDataOutputStream() throws IOException {
        return null; // TODO
    }
}
