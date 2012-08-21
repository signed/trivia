package trivia;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;



@RunWith(value = Parameterized.class)
public class GameRunner_LegacyAndRewriteProduceSameResult {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][] { { 42 }, { 0 }, { Long.MIN_VALUE }, { Long.MAX_VALUE } };
        return Arrays.asList(data);
    }

    private long seed;

    public GameRunner_LegacyAndRewriteProduceSameResult(long seed) {
        this.seed = seed;
    }

    @Test
    public void legacyAndRewriteProduceSameOutputForSameRandom() throws Exception {
        GameLog legacyGameLog = new TestSandbox().runLegacyGame(new Random(seed));
        GameLog rewriteGameLog = new TestSandbox().runRewrittenGame(new Random(seed));
        assertThat(legacyGameLog.log, is(rewriteGameLog.log));
    }
}
