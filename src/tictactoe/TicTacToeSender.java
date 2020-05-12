package tictactoe;

import java.io.IOException;

public interface TicTacToeSender {
    /**
     * erlaubt im Zustand START.
     * @param random
     * @throws IOException
     */
    void sendDice(int random) throws IOException, StatusException;

    /**
     * erlaubt im Zustand aktiv
     * @param x
     * @param y
     * @throws IOException
     */
    void sendSet(int x, int y) throws IOException, StatusException;
}
