package game.services.validators;

import game.requests.GuessedNumberRequest;
import game.responses.CoreError;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class GuessedNumberValidator {

    public List<CoreError> validate(GuessedNumberRequest request) {
        List<CoreError> errors = new ArrayList<>();
        validateNumber(request).ifPresent(errors::add);
        return errors;
    }

    private static Optional<CoreError> validateNumber(GuessedNumberRequest request) {
        if (request.getNumber().length() != 4) {
            return Optional.of(new CoreError("Number", "Must be 1 digit in each input"));
        }

        int[] digits = numberToArrMap(request.getNumber());
        Set<Integer> intSet = new HashSet<>();

        for (int digit : digits) {
            intSet.add(digit);
        }
        if (intSet.size() < 4) {
            return Optional.of(new CoreError("Number", "Input unique digits, please"));
        }

        return Optional.empty();
    }

    private static int[] numberToArrMap(String number) {
        String[] digits = number.split("");
        int[] result = new int[digits.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = Integer.parseInt(digits[i]);
        }
        return result;
    }

}
