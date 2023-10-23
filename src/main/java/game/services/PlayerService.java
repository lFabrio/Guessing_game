package game.services;

import game.database.JpaPlayerRepository;
import game.domain.Game;
import game.domain.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@Transactional
public class PlayerService {

    @Autowired
    private JpaPlayerRepository playerRepository;

    public void attachGameToPlayer(Player player, Game game) {
        player.setGame(game.getId());
        playerRepository.save(player);
    }

    public void finishGame(Player player, String result) {
        if (result.endsWith("Win!")) {
            player.increaseCorrectGuesses();
        }
        player.increaseGamesPlayed();

        double notRoundedScore = (double) player.getCorrectGuesses() / player.getGamesPlayed();
        BigDecimal rounded = BigDecimal.valueOf(notRoundedScore).setScale(3, RoundingMode.HALF_UP);

        player.setScore(rounded.doubleValue());

        playerRepository.save(player);
    }

}
