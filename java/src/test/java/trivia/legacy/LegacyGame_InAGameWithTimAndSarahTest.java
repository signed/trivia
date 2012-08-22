package trivia.legacy;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

public class LegacyGame_InAGameWithTimAndSarahTest {
    private final GameRemote gameRemote = new GameRemote();

    @Before
    public void boothJoinTheGameInOrder() throws Exception {
        gameRemote.addPlayersInOrder("Tim", "Sarah");
    }

    @Test
    public void timIsFirstInEachTurnBecauseHeJoinedFirst() throws Exception {
        gameRemote.rollSixSidedDiceAndProgressOnBoard();

        assertThat(theCurrentPlayer(), containsString("Tim"));
    }

    @Test
    public void sarahIsSecondInEachTurnBecauseSheJoinedSecond() throws Exception {
        timCompletesHisFirstMove();
        gameRemote.rollSixSidedDiceAndProgressOnBoard();

        assertThat(theCurrentPlayer(), containsString("Sarah"));
    }

    @Test
    public void timHasTheThirdTurnBecauseSarahWasTheLastPlayerWhoJoined() throws Exception {
        timCompletesHisFirstMove();
        sarahCompletesHerFirstMove();
        gameRemote.rollSixSidedDiceAndProgressOnBoard();

        assertThat(theCurrentPlayer(), is("Tim"));
    }

    private String theCurrentPlayer() {
        return gameRemote.lastLog().nameOfCurrentPlayer();
    }

    private void sarahCompletesHerFirstMove() {
        progressToNextPlayer();
    }

    private void timCompletesHisFirstMove() {
        progressToNextPlayer();
    }

    private void progressToNextPlayer() {
        gameRemote.rollSixSidedDiceAndProgressOnBoard();
        gameRemote.answerTheQuestionRightOrWrong();
    }

}
