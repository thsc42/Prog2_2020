package tictactoe.protocolBinding;

import tictactoe.StatusException;
import tictactoe.TicTacToeSender;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Encodes methods and parameter and sends via OutputStream
 */
public class StreamBindingSender implements TicTacToeSender {
    private final DataOutputStream dos;

    public StreamBindingSender(OutputStream os) {
        this.dos = new DataOutputStream(os);
    }

    @Override
    public void sendDice(int random) throws IOException, StatusException {
        this.dos.writeInt(StreamBinding.DICE);
        this.dos.writeInt(random);
    }

    @Override
    public void sendSet(int x, int y) throws IOException, StatusException {
        this.dos.writeInt(StreamBinding.SET);
        this.dos.writeInt(x);
        this.dos.writeInt(y);
    }
}
