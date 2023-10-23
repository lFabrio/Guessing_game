package game.services.validators;

import game.requests.LoginRequest;
import game.responses.CoreError;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UsernameSpellingValidator {

    public List<CoreError> validate(LoginRequest request) {
        List<CoreError> errors = new ArrayList<>();
        validateUsername(request).ifPresent(errors::add);
        return errors;
    }

    private Optional<CoreError> validateUsername(LoginRequest request) {
        Optional<CoreError> result = Optional.empty();
        if (request.getUsername() == null || request.getUsername().isEmpty()) {
            result = Optional.of(new CoreError("username", "Must not be empty"));
        } else if (request.getUsername().length() < 4) {
            result = Optional.of(new CoreError("username", "Must contain at least 4 symbols"));
        }
        return result;
    }

}
