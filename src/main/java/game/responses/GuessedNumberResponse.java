package game.responses;

import java.util.List;

public class GuessedNumberResponse extends CoreResponse {

    private String guessedNumber;

    public GuessedNumberResponse(String guessedNumber) {
        this.guessedNumber = guessedNumber;
    }

    public GuessedNumberResponse(List<CoreError> errors) {
        super(errors);
    }

    public String getGuessedNumber() {
        return guessedNumber;
    }

}
