package trivia.legacy;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class LegacyGame_AGameWithTimTest {

    private GameRemote gameRemote = new GameRemote();

    @Before
    public void timJoins() {
        gameRemote.addPlayer("Tim");
    }

    @Test
    public void eachPlayerThatJoinsTheGameIsAnnouncedInTheLog() throws Exception {
        assertThat(theTextWrittenToTimJoiningTheGame(), containsString("Tim was added"));
    }

    @Test
    public void announceThatAlbertJoinedAsSecondPlayer() throws Exception {
        albertJoins();
        assertThat(theTextWrittenToAlbertJoiningTheGame(), containsString("Albert was added"));
    }

    @Test
    public void writeTheNumberOfPlayersToTheLog() throws Exception {
        assertThat(theTextWrittenToTimJoiningTheGame(), containsString("They are player number 1"));
    }

    @Test
    public void writeTheNumberOfPlayersToTheLogIfAlbertJoinsAsSecondPlayer() throws Exception {
        albertJoins();
        assertThat(theTextWrittenToAlbertJoiningTheGame(), containsString("They are player number 2"));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void andMoreThanFiveOfHisFriendsIsNotPossibleMaximumOf6Players() throws Exception {
        fillToSixPlayers();
        addASeventhPlayer();
    }

    private void addASeventhPlayer() {
        gameRemote.addPlayer("one to much");
    }

    private void albertJoins() {
        gameRemote.addPlayer("Albert");
    }

    private void fillToSixPlayers() {
        gameRemote.addPlayer("two");
        gameRemote.addPlayer("three");
        gameRemote.addPlayer("four");
        gameRemote.addPlayer("five");
        gameRemote.addPlayer("six");
    }

    private String theTextWrittenToAlbertJoiningTheGame() {
        return gameRemote.lastLog().log;
    }

    private String theTextWrittenToTimJoiningTheGame() {
        return gameRemote.lastLog().log;
    }
}
