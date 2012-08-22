package trivia;

import com.google.common.base.Splitter;

public class GameLog {
    public static final String CurrentPlayerPattern = " is the current player";
    public final String log;

    public GameLog(String log) {
        this.log = log;
    }

    public String nameOfCurrentPlayer() {
        Iterable<String> split = Splitter.on("\n").split(log);
        for (String line : split) {
            if(line.endsWith(CurrentPlayerPattern)) {
                int startOfPattern = line.length() - CurrentPlayerPattern.length();
                return line.substring(0, startOfPattern);
            }
        }
        throw new RuntimeException("Pattern not found");
    }
}
