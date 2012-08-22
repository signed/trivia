package trivia;

import com.google.common.io.CharStreams;
import trivia.legacy.Game;
import trivia.legacy.LegacyGame;
import trivia.rewritten.RewrittenGame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Random;

public class TestSandbox {

    private static interface GameInvoker{
        void runGame(Random random, Game game);
    }


    private final PrintStream originalSystemOut = System.out;

    public GameLog runPreConfigured(Game game) {
        return runGame(new Random(25), game, new VanillaGameInvoker());
    }

    public GameLog runLegacyGame(Random random) {
        return runGame(random, new LegacyGame(), new JoinThreePlayer());
    }

    public GameLog runRewrittenGame(Random random){
        return runGame(random, new RewrittenGame(), new JoinThreePlayer());
    }

    private GameLog runGame(Random random, Game aGame, GameInvoker invoker) {
        try {
            ByteArrayOutputStream data = replaceSystemOut();
            invoker.runGame(random, aGame);
            String consoleLog = readOutput(data);
            return new GameLog(consoleLog);
        } finally {
            restoreOriginalSystemOut();
        }
    }

    private void restoreOriginalSystemOut() {
        System.setOut(originalSystemOut);
    }

    private String readOutput(ByteArrayOutputStream data) {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(data.toByteArray());
            return CharStreams.toString(new InputStreamReader(in, "UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ByteArrayOutputStream replaceSystemOut() {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        PrintStream mockOut = new PrintStream(data);
        System.setOut(mockOut);
        return data;
    }

    private static class JoinThreePlayer implements GameInvoker {

        @Override
        public void runGame(Random random, Game game) {
            GameRunner.runWith(random, game);

        }
    }

    private static class VanillaGameInvoker implements GameInvoker {
        @Override
        public void runGame(Random random, Game game) {
            GameRunner.runConfiguredGame(random, game);
        }
    }
}
