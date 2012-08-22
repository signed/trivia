package trivia.legacy;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class LegacyGame_TimAnswersQuestionWrongTest {
    private final GameRemote remote = new GameRemote();

    @Before
    public void addTimToTheGameAndProgressUpToSixFieldsOnTheBoardAndAnswerTheQuestionWrong() throws Exception {
        remote.addTimToTheGame();
        remote.rollSixSidedDiceAndProgressOnBoard();
        remote.answerTheQuestionWrong();
    }

    @Test
    public void stateTheFactThatTimAnsweredTheQuestionWrong() throws Exception {
        assertThat(theLog(), containsString("Question was incorrectly answered"));
    }

    @Test
    public void putHimIntoThePenaltyBox() throws Exception {
        assertThat(theLog(), containsString("Tim was sent to the penalty box"));
    }

    private String theLog() {
        return remote.lastLog().log;
    }
}
