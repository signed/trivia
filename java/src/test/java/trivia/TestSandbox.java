package trivia;

import com.google.common.io.CharStreams;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Random;

public class TestSandbox {
    private final PrintStream originalSystemOut = System.out;

    public GameLog runGameWith(Random random) {
        try {
            ByteArrayOutputStream data = replaceSystemOut();
            GameRunner.runWith(random);
            String consoleLog = restoreSystemOut(data);
            return new GameLog(consoleLog);
        } finally {
            restoreOriginalSystemOut();
        }
    }

    private void restoreOriginalSystemOut() {
        System.setOut(originalSystemOut);
    }

    private String restoreSystemOut(ByteArrayOutputStream data) {
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
}
