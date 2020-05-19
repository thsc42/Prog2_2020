package tictactoe.protocolBinding;

import tictactoe.StatusException;
import tictactoe.TicTacToeReceive;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamBindingReceiver extends Thread {
    private final DataInputStream dis;
    private final TicTacToeReceive receiver;

    public StreamBindingReceiver(InputStream is, TicTacToeReceive receiver) {
        this.dis = new DataInputStream(is);
        this.receiver = receiver;
    }

    public void readDice() throws IOException, StatusException {
        int random = this.dis.readInt();
        this.receiver.receiveDice(random);
    }

    public void readSet() throws IOException, StatusException {
        int x = this.dis.readInt();
        int y = this.dis.readInt();
        this.receiver.receiveSet(x, y);
    }

    public void run() {
        boolean again = true;
        while(again) {
            try {
                int cmd = this.dis.readInt();

                switch (cmd) {
                    case StreamBinding.DICE : this.readDice(); break;
                    case StreamBinding.SET : this.readSet(); break;
                    default: again = false; System.err.println("unknown command code: " + cmd);
                }

            } catch (IOException e) {
                System.err.println("IOException: " + e.getLocalizedMessage());
                again = false;
            } catch (StatusException e) {
                System.err.println("Status Exception: " + e.getLocalizedMessage());
                again = false;
            }
        }
    }
}
