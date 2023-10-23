package game.services.validators;

import game.requests.ShowLeaderBoardRequest;
import game.responses.CoreError;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ShowLeaderBoardValidator {

    public List<CoreError> validate(ShowLeaderBoardRequest request) {
        List<CoreError> errors = new ArrayList<>();
        validateGamesPlayed(request).ifPresent(errors::add);
        return errors;
    }

    private Optional<CoreError> validateGamesPlayed(ShowLeaderBoardRequest request) {
        if (request.getGamesPlayed() == null) {
            return Optional.of(new CoreError("Games played", "Must not be empty"));
        } else if (request.getGamesPlayed() < 0) {
            return Optional.of(new CoreError("Games played", "Must be equal or more than 0"));
        }
        return Optional.empty();
    }

}
