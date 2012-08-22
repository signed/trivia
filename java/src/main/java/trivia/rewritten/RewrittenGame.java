package trivia.rewritten;

import trivia.legacy.Game;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RewrittenGame implements Game {
    private final List<String> players = new ArrayList<String>();
    private final boolean[] inPenaltyBox = new boolean[6];
    private final int[] places = new int[6];
    private final int[] purses = new int[6];

    private final List<String> popQuestions = new LinkedList<String>();
    private final List<String> scienceQuestions = new LinkedList<String>();
    private final List<String> sportsQuestions = new LinkedList<String>();
    private final List<String> rockQuestions = new LinkedList<String>();
    private final PrintStream out;

    private int currentPlayer = 0;
    private boolean isGettingOutOfPenaltyBox;

    public RewrittenGame(PrintStream out) {
        this.out = out;
        for (int i = 0; i < 50; i++) {
            popQuestions.add("Pop Question " + i);
            scienceQuestions.add(("Science Question " + i));
            sportsQuestions.add(("Sports Question " + i));
            rockQuestions.add(createRockQuestion(i));
        }
    }

    public RewrittenGame() {
        this(null);
    }

    public String createRockQuestion(int index) {
        return "Rock Question " + index;
    }

    public boolean isPlayable() {
        return (howManyPlayers() >= 2);
    }

    @Override
    public boolean add(String playerName) {
        players.add(playerName);
        places[howManyPlayers()] = 0;
        purses[howManyPlayers()] = 0;
        inPenaltyBox[howManyPlayers()] = false;

        getOut().println(playerName + " was added");
        getOut().println("They are player number " + players.size());
        return true;
    }

    private PrintStream getOut() {
        return null != out ? out :System.out;
    }

    public int howManyPlayers() {
        return players.size();
    }

    @Override
    public void roll(int roll) {
        getOut().println(players.get(currentPlayer) + " is the current player");
        getOut().println("They have rolled a " + roll);

        if (inPenaltyBox[currentPlayer]) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;

                getOut().println(players.get(currentPlayer) + " is getting out of the penalty box");
                places[currentPlayer] = currentPlayersLocationOnBoard() + roll;
                if (currentPlayersLocationOnBoard() > 11) places[currentPlayer] = currentPlayersLocationOnBoard() - 12;

                getOut().println(players.get(currentPlayer)
                        + "'s new location is "
                        + currentPlayersLocationOnBoard());
                getOut().println("The category is " + currentCategory());
                askQuestion();
            } else {
                getOut().println(players.get(currentPlayer) + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }

        } else {

            places[currentPlayer] = currentPlayersLocationOnBoard() + roll;
            if (currentPlayersLocationOnBoard() > 11) places[currentPlayer] = currentPlayersLocationOnBoard() - 12;

            getOut().println(players.get(currentPlayer)
                    + "'s new location is "
                    + currentPlayersLocationOnBoard());
            getOut().println("The category is " + currentCategory());
            askQuestion();
        }

    }

    private void askQuestion() {
        if ("Pop".equals(currentCategory()))
            getOut().println(popQuestions.remove(0));
        if ("Science".equals(currentCategory()))
            getOut().println(scienceQuestions.remove(0));
        if ("Sports".equals(currentCategory()))
            getOut().println(sportsQuestions.remove(0));
        if ("Rock".equals(currentCategory()))
            getOut().println(rockQuestions.remove(0));
    }


    private String currentCategory() {
        if (currentPlayersLocationOnBoard() == 0) return "Pop";
        if (currentPlayersLocationOnBoard() == 1) return "Science";
        if (currentPlayersLocationOnBoard() == 2) return "Sports";
        if (currentPlayersLocationOnBoard() == 4) return "Pop";
        if (currentPlayersLocationOnBoard() == 5) return "Science";
        if (currentPlayersLocationOnBoard() == 6) return "Sports";
        if (currentPlayersLocationOnBoard() == 8) return "Pop";
        if (currentPlayersLocationOnBoard() == 9) return "Science";
        if (currentPlayersLocationOnBoard() == 10) return "Sports";
        return "Rock";
    }

    private int currentPlayersLocationOnBoard() {
        return places[currentPlayer];
    }

    @Override
    public boolean wasCorrectlyAnswered() {
        if (inPenaltyBox[currentPlayer]) {
            if (isGettingOutOfPenaltyBox) {
                getOut().println("Answer was correct!!!!");
                purses[currentPlayer]++;
                getOut().println(players.get(currentPlayer)
                        + " now has "
                        + purses[currentPlayer]
                        + " Gold Coins.");

                boolean winner = didPlayerWin();
                currentPlayer++;
                if (currentPlayer == players.size()) currentPlayer = 0;

                return winner;
            } else {
                currentPlayer++;
                if (currentPlayer == players.size()) currentPlayer = 0;
                return true;
            }
        } else {
            getOut().println("Answer was corrent!!!!");
            purses[currentPlayer]++;
            getOut().println(players.get(currentPlayer)
                    + " now has "
                    + purses[currentPlayer]
                    + " Gold Coins.");

            boolean winner = didPlayerWin();
            currentPlayer++;
            if (currentPlayer == players.size()) currentPlayer = 0;

            return winner;
        }
    }

    @Override
    public boolean wrongAnswer() {
        getOut().println("Question was incorrectly answered");
        getOut().println(players.get(currentPlayer) + " was sent to the penalty box");
        inPenaltyBox[currentPlayer] = true;

        currentPlayer++;
        if (currentPlayer == players.size()) currentPlayer = 0;
        return true;
    }


    private boolean didPlayerWin() {
        return !(purses[currentPlayer] == 6);
    }
}
