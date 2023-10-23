package game.responses;

import game.domain.Player;

import java.util.ArrayList;
import java.util.List;

public class ShowLeaderBoardResponse extends CoreResponse {

    private List<Player> leaderBoard;

    public ShowLeaderBoardResponse(ArrayList<Player> leaderBoard) {
        this.leaderBoard = leaderBoard;
    }

    public ShowLeaderBoardResponse(List<CoreError> errors) {
        super(errors);
    }

    public List<Player> getLeaderBoard() {
        return leaderBoard;
    }

}
