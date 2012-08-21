package trivia.legacy;

import org.junit.Test;
import trivia.GameLog;
import trivia.TestSandbox;

public class LegacyGame_AGameWithTest {

    @Test(expected = IndexOutOfBoundsException.class)
    public void noPlayersCanNotBePlayed() throws Exception {
        Game game = new LegacyGame();
        GameLog gameLog = new TestSandbox().runPreConfigured(game);
        System.out.println(gameLog.log);
    }
}
