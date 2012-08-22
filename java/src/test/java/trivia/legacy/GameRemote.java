package trivia.legacy;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.io.CharStreams;
import trivia.GameLog;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.List;

public class GameRemote {
    private final List<GameLog> logs = Lists.newArrayList();
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final Game game;

    public GameRemote() {
        PrintStream out1 = new PrintStream(out);
        game = new LegacyGame(out1);
        //game = new RewrittenGame(out1);
    }

    public void addPlayer(String playerName) {
        game.add(playerName);
        processLoggedText();
    }

    private void processLoggedText() {
        logs.add(createLogFromOutput());
        out.reset();
    }

    public GameLog lastLog() {
        return Iterables.getLast(logs);
    }

    private GameLog createLogFromOutput() {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
            String log = CharStreams.toString(new InputStreamReader(in, "UTF-8"));
            return new GameLog(log);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addTimToTheGame() {
        addPlayer("Tim");
    }

    public void rollSixSidedDiceAndProgressOnBoard() {
        game.roll(2);
        processLoggedText();
    }

    public void answerTheQuestionWrong() {
        game.wrongAnswer();
        processLoggedText();
    }

    public void addPlayersInOrder(String ... playerNames) {
        for (String playerName : playerNames) {
            addPlayer(playerName);
        }
    }

    public void answerTheQuestionRightOrWrong() {
        answerTheQuestionWrong();
    }

    public void dumpCompleteLog(){
        for (GameLog log : logs) {
            System.out.println(log.log);
        }
    }
}
