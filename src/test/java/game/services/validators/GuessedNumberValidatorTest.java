package game.services.validators;

import game.requests.GuessedNumberRequest;
import game.responses.CoreError;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class GuessedNumberValidatorTest {

    private final GuessedNumberValidator validator = new GuessedNumberValidator();

    @Test
    public void shouldReturnErrorWhenNumberIsOutOfBoundsMore() {
        GuessedNumberRequest request = new GuessedNumberRequest("12345");
        List<CoreError> errors = validator.validate(request);
        assertEquals(1, errors.size());
        assertEquals("Number", errors.get(0).getField());
        assertEquals("Must be 1 digit in each input", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnErrorWhenNumberIsOutOfBoundsLess() {
        GuessedNumberRequest request = new GuessedNumberRequest("123");
        List<CoreError> errors = validator.validate(request);
        assertEquals(1, errors.size());
        assertEquals("Number", errors.get(0).getField());
        assertEquals("Must be 1 digit in each input", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnErrorWhenNotUniqueA() {
        GuessedNumberRequest request = new GuessedNumberRequest("1123");
        List<CoreError> errors = validator.validate(request);
        assertEquals(1, errors.size());
        assertEquals("Number",errors.get(0).getField());
        assertEquals("Input unique digits, please", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnErrorWhenNotUniqueB() {
        GuessedNumberRequest request = new GuessedNumberRequest("3111");
        List<CoreError> errors = validator.validate(request);
        assertEquals(1, errors.size());
        assertEquals("Number",errors.get(0).getField());
        assertEquals("Input unique digits, please", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnErrorWhenNotUniqueC() {
        GuessedNumberRequest request = new GuessedNumberRequest("1111");
        List<CoreError> errors = validator.validate(request);
        assertEquals(1, errors.size());
        assertEquals("Number",errors.get(0).getField());
        assertEquals("Input unique digits, please", errors.get(0).getMessage());
    }

    @Test
    public void shouldSuccess() {
        GuessedNumberRequest request = new GuessedNumberRequest("0379");
        List<CoreError> errors = validator.validate(request);
        assertEquals(0, errors.size());
    }

}