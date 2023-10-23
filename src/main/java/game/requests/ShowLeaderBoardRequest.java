package game.requests;

public class ShowLeaderBoardRequest {

    private Integer gamesPlayed;

    public ShowLeaderBoardRequest() {
    }

    public ShowLeaderBoardRequest(Integer gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public Integer getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

}
