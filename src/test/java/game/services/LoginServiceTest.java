package game.services;

import game.database.JpaPlayerRepository;
import game.domain.Player;
import game.requests.LoginRequest;
import game.responses.CoreError;
import game.responses.LoginResponse;
import game.services.validators.UsernameSpellingValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {

    @Mock
    private JpaPlayerRepository playerRepository;
    @Mock
    private UsernameSpellingValidator validator;
    @InjectMocks
    private LoginService loginService;

    @Test
    public void shouldReturnResponseWithErrorsWhenValidationFails() {
        LoginRequest notValidRequest = new LoginRequest(null);
        when(validator.validate(notValidRequest))
                .thenReturn(List.of(new CoreError("username", "Must not be empty")));
        LoginResponse response = loginService.execute(notValidRequest);

        assertTrue(response.hasErrors());
    }

    @Test
    public void shouldReturnResponseWithErrorsReceivedFromValidator() {
        LoginRequest notValidRequest = new LoginRequest("");
        when(validator.validate(notValidRequest))
                .thenReturn(List.of(new CoreError("username", "Must not be empty")));
        LoginResponse response = loginService.execute(notValidRequest);

        assertEquals(response.getErrors().size(), 1);
        assertEquals("username", response.getErrors().get(0).getField());
        assertEquals("Must not be empty", response.getErrors().get(0).getMessage());
    }

    @Test
    public void shouldNotInvokeDatabaseWhenRequestValidationFails() {
        LoginRequest notValidRequest = new LoginRequest(null);
        when(validator.validate(notValidRequest))
                .thenReturn(List.of(new CoreError("username", "Must not be empty")));
        loginService.execute(notValidRequest);

        verifyNoInteractions(playerRepository);
    }

    @Test
    public void shouldRegisterPlayerWhenRequestIsValid() {
        LoginRequest validRequest = new LoginRequest("Name");
        when(validator.validate(validRequest)).thenReturn(List.of());
        loginService.execute(validRequest);

        verify(playerRepository).findByUsername(validRequest.getUsername());
        verify(playerRepository).save(any(Player.class));
    }

    @Test
    public void shouldReturnPlayerIfExists() {
        Player player = new Player("Name");

        LoginRequest request = new LoginRequest(player.getUsername());
        when(validator.validate(request)).thenReturn(List.of());
        when(playerRepository.findByUsername(player.getUsername())).thenReturn(Optional.of(player));
        LoginResponse response = loginService.execute(request);

        assertEquals(player, response.getPlayer());
    }

    @Test
    public void shouldReturnResponseWithoutErrorsWhenRequestIsValid() {
        LoginRequest validRequest = new LoginRequest("Name");
        when(validator.validate(validRequest)).thenReturn(List.of());
        LoginResponse response = loginService.execute(validRequest);

        assertFalse(response.hasErrors());
    }

    @Test
    public void shouldReturnResponseWithBookWhenRequestIsValid() {
        LoginRequest validRequest = new LoginRequest("Name");
        when(validator.validate(validRequest)).thenReturn(List.of());
        LoginResponse response = loginService.execute(validRequest);

        assertNotNull(response.getPlayer());
        assertEquals(response.getPlayer().getUsername(), validRequest.getUsername());
    }

}