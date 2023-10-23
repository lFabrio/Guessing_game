package game.services.validators;

import game.requests.LoginRequest;
import game.responses.CoreError;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class UsernameSpellingValidatorTest {


    private final UsernameSpellingValidator validator = new UsernameSpellingValidator();

    @Test
    public void shouldReturnErrorWhenUsernameIsNull() {
        LoginRequest request = new LoginRequest(null);
        List<CoreError> errors = validator.validate(request);
        assertEquals(1, errors.size());
        assertEquals("username", errors.get(0).getField());
        assertEquals("Must not be empty", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnErrorWhenUsernameIsEmpty() {
        LoginRequest request = new LoginRequest("");
        List<CoreError> errors = validator.validate(request);
        assertEquals(1, errors.size());
        assertEquals("username", errors.get(0).getField());
        assertEquals("Must not be empty", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnErrorWhenLessThanFourSymbols() {
        LoginRequest request = new LoginRequest("ABC");
        List<CoreError> errors = validator.validate(request);
        assertEquals(errors.size(), 1);
        assertEquals( "username", errors.get(0).getField());
        assertEquals("Must contain at least 4 symbols", errors.get(0).getMessage());
    }

    @Test
    public void shouldSuccess() {
        LoginRequest request = new LoginRequest("Vlad");
        List<CoreError> errors = validator.validate(request);
        assertEquals(0, errors.size());
    }

}