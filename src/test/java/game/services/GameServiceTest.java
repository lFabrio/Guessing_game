package game.services;

import game.database.JpaGameRepository;
import game.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GameServiceTest {

    @InjectMocks
    private GameService gameService;
    @Mock
    private JpaGameRepository gameRepository;
    @Mock
    private PlayerService playerService;

    @Test
    public void testStartNewGame() {
        Player player = new Player("Name");
        when(gameRepository.save(any(Game.class))).thenReturn(new Game("1234"));

        Game game = gameService.startNewGame(player, false);

        verify(playerService).attachGameToPlayer(eq(player), eq(game));
        assertEquals("1234", game.getSecretNumber());
    }

    @Test
    public void testRestartGame() {
        Player player = new Player("Name");
        gameService.startNewGame(player, true);
        assertEquals(1, player.getGamesPlayed());
    }

    @Test
    public void testMakeGuessA() {
        Player player = new Player("Name");
        Game game = new Game("1234");

        String result = gameService.makeGuess("5678", player, game);

        assertTrue(result.contains("M:0 P:0"));
        verify(gameRepository).save(eq(game));
        verify(playerService, never()).finishGame(eq(player), anyString());
    }

    @Test
    public void testMakeGuessB() {
        Player player = new Player("Name");
        Game game = new Game("1234");

        String result = gameService.makeGuess("1243", player, game);

        assertTrue(result.contains("M:2 P:2"));
        verify(gameRepository).save(eq(game));
        verify(playerService, never()).finishGame(eq(player), anyString());
    }

    @Test
    public void testMakeGuessC() {
        Player player = new Player("Name");
        Game game = new Game("1234");

        String result = gameService.makeGuess("4321", player, game);

        assertTrue(result.contains("M:4 P:0"));
        verify(gameRepository).save(eq(game));
        verify(playerService, never()).finishGame(eq(player), anyString());
    }


    @Test
    public void testFinishGameWin() {
        Player player = new Player("Name");
        Game game = new Game("1234");

        String result = gameService.makeGuess("1234", player, game);

        assertTrue(result.contains("Win"));
        verify(gameRepository).save(eq(game));
    }

    @Test
    public void testFinishGameLoose() {
        Player player = new Player("Name");
        Game game = new Game("1234");
        game.setTriesLeft(1);

        String result = gameService.makeGuess("5678", player, game);

        assertTrue(result.contains("Loose"));
        verify(gameRepository).save(eq(game));
    }

    @Test
    public void shouldDecreaseTriesAfterGuess() {
        Player player = new Player("Name");
        Game game = new Game("1234");
        player.setGame(game.getId());

        int initialTries = game.getTriesLeft();

        gameService.makeGuess("5678", player, game);

        int updatedTries = game.getTriesLeft();

        assertEquals(initialTries - 1, updatedTries);
    }

    @Test
    public void shouldSaveGuess() {
        Game game = new Game("1234");

        String guess = "5678";
        gameService.makeGuess(guess, any(), game);

        assertEquals(guess, game.getTurns().get(0));
    }

}