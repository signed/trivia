package trivia.legacy;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class LegacyGame_InAGameWithTimAndSarahTest {
    private final GameRemote gameRemote = new GameRemote();

    @Before
    public void boothJoinTheGameInOrder() throws Exception {
        gameRemote.addPlayersInOrder("Tim", "Sarah");
    }

    @Test
    public void timIsFirstInEachTurnBecauseHeJoinedFirst() throws Exception {
        gameRemote.rollSixSidedDiceAndProgressOnBoard();

        assertThat(gameRemote.lastLog().log, containsString("Tim is the current player"));
    }


    @Test
    public void sarahIsSecondInEachTurnBecauseSheJoinedSecond() throws Exception {
        timCompletesHisFirstMove();
        gameRemote.rollSixSidedDiceAndProgressOnBoard();

        assertThat(gameRemote.lastLog().log, containsString("Sarah is the current player"));
    }

    @Test
    public void timHasTheThirdTurnBecauseSarahWasTheLastPlayerWhoJoined() throws Exception {
        timCompletesHisFirstMove();
        sarahCompletesHerFirstMove();
        gameRemote.rollSixSidedDiceAndProgressOnBoard();

        assertThat(gameRemote.lastLog().log, containsString("Tim is the current player"));
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
