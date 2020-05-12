package tictactoe;

import java.io.IOException;

public interface TicTacToeReceive {
    /**
     * erlaubt im Zustand START. führt zu Activ oder passiv
     * @param random
     * @throws IOException
     */
    void receiveDice(int random) throws IOException, StatusException;

    /**
     * erlaubt im Zustand passiv
     * @param x
     * @param y
     * @throws IOException
     */
    void receiveSet(int x, int y) throws IOException, StatusException;
}
