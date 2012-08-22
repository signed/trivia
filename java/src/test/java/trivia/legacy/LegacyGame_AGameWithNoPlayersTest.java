package trivia.legacy;

import org.junit.Test;
import trivia.TestSandbox;

public class LegacyGame_AGameWithNoPlayersTest {

    @Test(expected = IndexOutOfBoundsException.class)
    public void canNotBePlayed() throws Exception {
        new GameRemote().rollSixSidedDiceAndProgressOnBoard();
    }
}
