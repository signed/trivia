package trivia;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class GameLog_ExtractThePlayerTheCurrentPlayerNameByPatternTest {

    @Test
    public void inASingleLineLog() throws Exception {
        GameLog gameLog = new GameLog("Sarah is the current player");
        String playerName = gameLog.nameOfCurrentPlayer();

        assertThat(playerName, is("Sarah"));
    }

    @Test
    public void inAMultiLineLog() throws Exception {
        GameLog gameLog = new GameLog("BluBlu\nSarah is the current player\nBlaBla");
        String playerName = gameLog.nameOfCurrentPlayer();

        assertThat(playerName, is("Sarah"));
    }

    @Test(expected = RuntimeException.class)
    public void inALogThatDoesNotContainTheCurrentPlayerInformation() throws Exception {
        GameLog gameLog = new GameLog("Duuasf \nHave fun\nnot containing\nthe pattern ");
        gameLog.nameOfCurrentPlayer();
    }
}
