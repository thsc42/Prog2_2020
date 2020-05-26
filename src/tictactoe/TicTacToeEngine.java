package tictactoe;

import java.io.IOException;
import java.util.Random;

public class TicTacToeEngine implements TicTacToeReceive, TicTacToeUsage {
    public static final int UNDEFINED_DICE = -1;
    private final TicTacToeSender sender;
    public static final int DIM = 3;

    TicTacToeBoardField[][] board = new TicTacToeBoardField[DIM][DIM];

    private TicTacToeStatus status;
    private int sentDice = UNDEFINED_DICE;
    private TicTacToeBoardField myStone;
    private int receivedRandom;
    private TicTacToeBoardField otherStone;

    public TicTacToeEngine(TicTacToeSender sender) {
        this.status = TicTacToeStatus.START;
        this.sender = sender;

        for(int i = 0; i < DIM; i++) {
            for(int j = 0; j < DIM; j++) {
                this.board[i][j] = TicTacToeBoardField.EMPTY;
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                  remote engine support                                     //
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void receiveDice(int random) throws IOException, StatusException {
        if( this.status != TicTacToeStatus.START
            && this.status != TicTacToeStatus.DICE_SENT
        ) {
            throw new StatusException();
        }

        this.receivedRandom = random;

        // höhere zahl - aktiv, kleinere -> passiv, gleiche zahl noch einmal.
        if(this.status == TicTacToeStatus.DICE_SENT) {
            this.decideWhoStarts();
        } else {
            this.status = TicTacToeStatus.DICE_RECEIVED;
        }
    }

    @Override
    public void receiveSet(int x, int y) throws IOException, StatusException, TicTacToeException {
        if(this.status != TicTacToeStatus.PASSIVE) {
            throw new StatusException();
        }

        // set stone

        // check if allowed
        this.checkValidSet(x, y);

        this.board[x][y] = this.otherStone;

        this.checkGameOver();
    }

    private void checkValidSet(int x, int y) throws TicTacToeException {
        // coordinates correct
        if (x >= DIM || y >= DIM || x < 0 || y < 0) {
            throw new TicTacToeException("wrong parameters");
        }

        if (this.board[x][y] != TicTacToeBoardField.EMPTY) {
            throw new TicTacToeException("position already occupied");
        }
    }

    public void sendSet(int x, int y) throws IOException, StatusException {
        if(this.status != TicTacToeStatus.ACTIVE) {
            throw new StatusException();
        }

        // send
        this.sender.sendSet(x, y);

        // end?
        this.checkGameOver();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                  user interface support                                    //
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void doDice() throws StatusException, IOException {
        if(this.status != TicTacToeStatus.START
            && this.status != TicTacToeStatus.DICE_RECEIVED) {
            throw new StatusException();
        }

        Random r = new Random();
        this.sentDice = r.nextInt();

        // sende den Wert über den Sender
        this.sender.sendDice(this.sentDice);

        if(this.status == TicTacToeStatus.DICE_RECEIVED) {
            this.decideWhoStarts();
        } else {
            this.status = TicTacToeStatus.DICE_SENT;
        }
    }

    private void decideWhoStarts() {
        if(this.receivedRandom == this.sentDice) {
            this.status = TicTacToeStatus.START;
        } else if(this.receivedRandom > this.sentDice) {
            this.status = TicTacToeStatus.PASSIVE;
            this.myStone = TicTacToeBoardField.CIRCLE;
            this.otherStone = TicTacToeBoardField.CROSS;
        } else {
            this.status = TicTacToeStatus.ACTIVE;
            this.myStone = TicTacToeBoardField.CROSS;
            this.otherStone = TicTacToeBoardField.CIRCLE;
        }
    }

    @Override
    public boolean isActive() {
        return this.status == TicTacToeStatus.ACTIVE;
    }

    @Override
    public void set(int x, int y) throws TicTacToeException, StatusException, IOException {
        this.checkValidSet(x, y);

        // valid data
        this.board[x][y] = this.myStone;

        // send
        this.sendSet(x, y);
    }

    private TicTacToeBoardField areThreeInARow(int xIncrement, int yIncrement) {
        // calculate first field
        int firstX;
        int firstY;

        if(xIncrement < 0) firstX = DIM-1; else firstX = 0;
        if(yIncrement < 0) firstY = DIM-1; else firstY = 0;


        for(int x = firstX; x < DIM; x += xIncrement) {
            TicTacToeBoardField firstStone = TicTacToeBoardField.EMPTY, stone;
            boolean isRow = true;
            boolean firstRound = true;
            for(int y = firstY; y < DIM; y += yIncrement) {
                stone = this.board[x][y];
                if(stone == TicTacToeBoardField.EMPTY) {
                    isRow = false; break;
                }

                if(firstRound) {
                    firstStone = stone; // remember first stone
                } else {
                    if(firstStone != stone) { // stone must be of same kind
                        isRow = false; break;
                    }
                }
            }
            if(isRow) return firstStone;
        }

        return TicTacToeBoardField.EMPTY;
    }

    private void checkGameOver() {
        // find three in a row

        // test horizontal
        TicTacToeBoardField stone = this.areThreeInARow(1, 0);
        if(stone == TicTacToeBoardField.EMPTY) {
            // test vertical
            stone = this.areThreeInARow(0, 1);
            if (stone == TicTacToeBoardField.EMPTY) {
                // test diagonal
                stone = this.areThreeInARow(1, 1);
                if (stone == TicTacToeBoardField.EMPTY) {
                    // test diagonal other side
                    stone = this.areThreeInARow(1, -1);
                }
            }
        }

        if(stone == this.myStone) this.status = TicTacToeStatus.WON;
        else if(stone == this.otherStone) this.status = TicTacToeStatus.LOST;

        // else empty field - game not over
    }
}
