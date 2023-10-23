package game.services;

import game.requests.GuessedNumberRequest;
import game.responses.CoreError;
import game.responses.GuessedNumberResponse;
import game.services.validators.GuessedNumberValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuessedNumberService {

    @Autowired
    private GuessedNumberValidator validator;

    public GuessedNumberResponse execute(GuessedNumberRequest request) {
        List<CoreError> errors = validator.validate(request);
        if (!errors.isEmpty()) {
            return new GuessedNumberResponse(errors);
        }
        return new GuessedNumberResponse(request.getNumber());
    }

}
