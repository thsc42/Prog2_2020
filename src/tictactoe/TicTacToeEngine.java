package tictactoe;

import java.io.IOException;
import java.util.Random;

public class TicTacToeEngine implements TicTacToeReceive, TicTacToeSender {
    public static final int UNDEFINED_DICE = -1;

    private TicTacToeStatus status;
    private int sentDice = UNDEFINED_DICE;

    public TicTacToeEngine() {
        this.status = TicTacToeStatus.START;
    }

    @Override
    public void receiveDice(int random) throws IOException, StatusException {
        if( this.status != TicTacToeStatus.START
            && this.status != TicTacToeStatus.DICE_SENT
        ) {
            throw new StatusException();
        }

        // höhere zahl - aktiv, kleinere -> passiv, gleiche zahl noch einmal.
        if(this.status == TicTacToeStatus.DICE_SENT) {
            if(random == this.sentDice) {
                this.status = TicTacToeStatus.START;
            } else if(random > this.sentDice) {
                this.status = TicTacToeStatus.PASSIVE;
            } else {
                this.status = TicTacToeStatus.ACTIVE;
            }
        }
    }

    @Override
    public void receiveSet(int x, int y) throws IOException, StatusException {
        if(this.status != TicTacToeStatus.PASSIVE) {
            throw new StatusException();
        }

        // gibt es eine Reihe von drei Steinen? -> TicTacToeStatus.LOST
    }

    @Override
    public void sendDice(int random) throws IOException, StatusException {
        if(this.status != TicTacToeStatus.START) {
            throw new StatusException();
        }

        Random r = new Random();
        this.sentDice = r.nextInt();

        // sende den Wert über TCP

        this.status = TicTacToeStatus.DICE_SENT;
    }

    @Override
    public void sendSet(int x, int y) throws IOException, StatusException {
        if(this.status != TicTacToeStatus.ACTIVE) {
            throw new StatusException();
        }

        // wenn nun drei steine in einer Reihe - TicTacToeStatus.WON
    }
}
