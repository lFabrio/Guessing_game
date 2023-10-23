package game.responses;

import game.domain.Player;

import java.util.List;

public class LoginResponse extends CoreResponse {

    private Player player;

    public LoginResponse(Player player) {
        this.player = player;
    }

    public LoginResponse(List<CoreError> errors) {
        super(errors);
    }

    public Player getPlayer() {
        return player;
    }

}
