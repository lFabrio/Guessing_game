package game.services;

import game.database.JpaPlayerRepository;
import game.domain.Player;
import game.requests.ShowLeaderBoardRequest;
import game.responses.ShowLeaderBoardResponse;
import game.services.validators.ShowLeaderBoardValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShowLeaderBoardServiceTest {

    @Mock
    private JpaPlayerRepository playerRepository;
    @Mock
    private ShowLeaderBoardValidator validator;
    @InjectMocks
    private ShowLeaderBoardService service;

    @Test
    public void shouldGetPlayersFromDb() {
        List<Player> players = new ArrayList<>();
        players.add(new Player("Name"));

        when(playerRepository.findAll()).thenReturn(players);

        ShowLeaderBoardRequest request = new ShowLeaderBoardRequest(0);
        ShowLeaderBoardResponse response = service.execute(request);

        assertFalse(response.hasErrors());
        assertEquals(1, response.getLeaderBoard().size());
        assertEquals("Name", response.getLeaderBoard().get(0).getUsername());
    }

    @Test
    public void shouldGetPlayersWithExactNumberOfGamesPlayed() {
        Player player1 = new Player("Name1");
        Player player2 = new Player("Name2");
        player1.increaseGamesPlayed();

        List<Player> players = new ArrayList<>(List.of(player1, player2));
        when(playerRepository.findAll()).thenReturn(players);

        ShowLeaderBoardRequest request = new ShowLeaderBoardRequest(1);
        ShowLeaderBoardResponse response = service.execute(request);

        assertFalse(response.hasErrors());
        assertEquals(1, response.getLeaderBoard().size());
    }

    @Test
    public void shouldReturnPlayersInCorrectOrder() {
        Player player1 = new Player("Name1");
        Player player2 = new Player("Name2");
        Player player3 = new Player("Name3");
        player1.increaseGamesPlayed();
        player2.increaseGamesPlayed();
        player1.setScore(100);
        player2.setScore(90);
        player3.setScore(100);

        List<Player> players = new ArrayList<>(List.of(player1, player2, player3));
        when(playerRepository.findAll()).thenReturn(players);

        ShowLeaderBoardRequest request = new ShowLeaderBoardRequest(0);
        ShowLeaderBoardResponse response = service.execute(request);

        assertFalse(response.hasErrors());
        assertEquals(player3, response.getLeaderBoard().get(0));
        assertEquals(player1, response.getLeaderBoard().get(1));
        assertEquals(player2, response.getLeaderBoard().get(2));
    }

}