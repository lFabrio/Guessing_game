package game.services.validators;

import game.requests.ShowLeaderBoardRequest;
import game.responses.CoreError;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ShowLeaderBoardValidatorTest {

    private final ShowLeaderBoardValidator validator = new ShowLeaderBoardValidator();

    @Test
    public void shouldReturnErrorWhenNegative() {
        ShowLeaderBoardRequest request = new ShowLeaderBoardRequest(-1);
        List<CoreError> errors = validator.validate(request);
        assertEquals(1, errors.size());
        assertEquals("Games played", errors.get(0).getField());
        assertEquals("Must be equal or more than 0", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnErrorWhenNull() {
        ShowLeaderBoardRequest request = new ShowLeaderBoardRequest(null);
        List<CoreError> errors = validator.validate(request);
        assertEquals(1, errors.size());
        assertEquals("Games played", errors.get(0).getField());
        assertEquals("Must not be empty", errors.get(0).getMessage());
    }

    @Test
    public void shouldSuccess() {
        ShowLeaderBoardRequest request = new ShowLeaderBoardRequest(0);
        List<CoreError> errors = validator.validate(request);
        assertEquals(0, errors.size());
    }

}