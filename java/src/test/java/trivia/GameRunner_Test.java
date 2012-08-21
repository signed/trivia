package trivia;

import org.junit.Test;

import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class GameRunner_Test {

    @Test
    public void twoSubsequentRunsOfTheLegacyGameProduceTheSameOutput() throws Exception {
        GameLog logOfRunOne = new TestSandbox().runGameWith(new Random(42));
        GameLog logOfRunTwo = new TestSandbox().runGameWith(new Random(42));

        assertThat(logOfRunOne.log, is(logOfRunTwo.log));
    }
}
