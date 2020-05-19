package tictactoe;

import java.io.IOException;

public interface TicTacToeUsage {
    /**
     * figure out who starts
     */
    void doDice() throws StatusException, IOException;

    /**
     *
     * @return true if active - player can set a game stone
     */
    boolean isActive();

    /**
     * set game stone at position x,y
     * @param x 0..2
     * @param y 0..2
     */
    void set(int x, int y) throws TicTacToeException, StatusException;
}

