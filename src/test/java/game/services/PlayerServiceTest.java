package game.services;

import game.database.JpaPlayerRepository;
import game.domain.Game;
import game.domain.Player;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PlayerServiceTest {

    @Mock
    private JpaPlayerRepository playerRepository;
    @InjectMocks
    private PlayerService playerService;

    @Test
    public void shouldIncreaseGamesCountAndCorrectGuessIfWin() {
        Player player = new Player("Name");

        playerService.finishGame(player, "You Win!");

        verify(playerRepository).save(eq(player));
        assertEquals(1, player.getCorrectGuesses());
        assertEquals(1, player.getGamesPlayed());
    }

    @Test
    public void shouldIncreaseGamesCountIfLoose() {
        Player player = new Player("Name");

        playerService.finishGame(player, "You Loose!");

        verify(playerRepository).save(eq(player));
        assertEquals(0, player.getCorrectGuesses());
        assertEquals(1, player.getGamesPlayed());
    }

    @Test
    public void shouldAttachGameToPlayer() {
        Player player = new Player("Name");
        Game game = new Game("1234");

        playerService.attachGameToPlayer(player, game);

        verify(playerRepository).save(eq(player));
        assertEquals(game.getId(), player.getGameId());
    }

}
