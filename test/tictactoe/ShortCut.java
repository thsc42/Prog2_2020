package tictactoe;

import java.io.IOException;

public class ShortCut implements TicTacToeSender {
    private TicTacToeReceive receiver;

    public void setReceiver(TicTacToeReceive receiver) {
        this.receiver = receiver;
    }

    @Override
    public void sendDice(int random) throws IOException, StatusException {
        this.receiver.receiveDice(random);
    }

    @Override
    public void sendSet(int x, int y) throws IOException, StatusException {
        this.receiver.receiveSet(x, y);
    }
}
