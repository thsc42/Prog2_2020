package tictactoe;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class TicTacToeTest {

    @Test
    public void usageTest() throws TicTacToeException, StatusException, IOException {
        ShortCut sender1 = new ShortCut();
        TicTacToeEngine game1 = new TicTacToeEngine(sender1);

        ShortCut sender2 = new ShortCut();
        TicTacToeEngine game2 = new TicTacToeEngine(sender2);

        // connect both games
        sender1.setReceiver(game2);
        sender2.setReceiver(game1);

        // test methods
        game1.doDice();
        game2.doDice();

        TicTacToeUsage activeGame = game1.isActive() ? game1 : game2;

        activeGame.set(0,0);
        Assert.assertFalse(activeGame.isActive());
    }
}
