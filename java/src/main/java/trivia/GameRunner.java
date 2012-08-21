package trivia;

import trivia.legacy.Game;
import trivia.legacy.LegacyGame;

import java.util.Random;


public class GameRunner {

    private static boolean notAWinner;

    public static void main(String[] args) {
        runWith(new Random(), new LegacyGame());
    }

    public static void runWith(Random rand, Game aGame) {
        aGame.add("Chet");
        aGame.add("Pat");
        aGame.add("Sue");

        runConfiguredGame(rand, aGame);
    }

    public static void runConfiguredGame(Random rand, Game aGame) {
        do {
            aGame.roll(rand.nextInt(5) + 1);

            if (rand.nextInt(9) == 7) {
                notAWinner = aGame.wrongAnswer();
            } else {
                notAWinner = aGame.wasCorrectlyAnswered();
            }
        } while (notAWinner);
    }
}