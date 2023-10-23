package game.services;

import game.database.JpaPlayerRepository;
import game.domain.Player;
import game.requests.ShowLeaderBoardRequest;
import game.responses.CoreError;
import game.responses.ShowLeaderBoardResponse;
import game.services.validators.ShowLeaderBoardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class ShowLeaderBoardService {

    @Autowired
    private JpaPlayerRepository playerRepository;
    @Autowired
    private ShowLeaderBoardValidator validator;

    public ShowLeaderBoardResponse execute(ShowLeaderBoardRequest request) {
        List<CoreError> errors = validator.validate(request);

        if (!errors.isEmpty()) {
            return new ShowLeaderBoardResponse(errors);
        }

        List<Player> players = playerRepository.findAll()
                .stream()
                .filter(player -> player.getGamesPlayed() >= request.getGamesPlayed())
                .toList();

        Comparator<Player> gamesPlayedComparator = Comparator.comparing(Player::getGamesPlayed);
        Comparator<Player> scoreComparator = Comparator.comparing(Player::getScore).reversed();

        List<Player> sortedByGames = sort(gamesPlayedComparator, players);
        List<Player> sortedByScore = sort(scoreComparator, sortedByGames);

        return new ShowLeaderBoardResponse(new ArrayList<>(sortedByScore));
    }

    private List<Player> sort(Comparator<Player> comparator, List<Player> players) {
        return players.stream().sorted(comparator).toList();
    }

}
