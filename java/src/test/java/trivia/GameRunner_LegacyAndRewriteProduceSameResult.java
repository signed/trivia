package trivia;

import org.junit.Test;

import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class GameRunner_LegacyAndRewriteProduceSameResult {

    @Test
    public void legacyAndRewriteProduceSameOutputForSameRandom() throws Exception {
        GameLog legacyGameLog = new TestSandbox().runLegacyGame(new Random(47));
        GameLog rewriteGameLog = new TestSandbox().runRewrittenGame(new Random(47));
        assertThat(legacyGameLog.log, is(rewriteGameLog.log));
    }
}
