package trivia.legacy;

import com.google.common.io.CharStreams;
import org.junit.Before;
import org.junit.Test;
import trivia.GameLog;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class LegacyGame_AGameWithTimTest {
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private LegacyGame game = new LegacyGame(new PrintStream(out));

    @Before
    public void timJoins() {
        game.add("Tim");
    }

    @Test
    public void eachPlayerThatJoinsTheGameIsAnnouncedInTheLog() throws Exception {
        assertThat(game().log, containsString("Tim was added"));
    }

    @Test
    public void writeTheNumberOfPlayersToTheLog() throws Exception {
        assertThat(game().log, containsString("They are player number 1"));
    }

    private GameLog game() throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        String log = CharStreams.toString(new InputStreamReader(in, "UTF-8"));
        System.out.println(log);
        return new GameLog(log);
    }

}
