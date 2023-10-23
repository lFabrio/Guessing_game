package game.services;

import game.database.JpaGameRepository;
import game.domain.Game;
import game.domain.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class GameService {

    @Autowired
    private JpaGameRepository gameRepository;
    @Autowired
    private PlayerService playerService;

    public Game startNewGame(Player player, boolean restart) {
        Game newGame = gameRepository.save(new Game(generateSecretNumber()));

        if (restart) {
            player.increaseGamesPlayed();
        }

        playerService.attachGameToPlayer(player, newGame);

        return newGame;
    }

    public Game getAttachedGame(Player player) {
        Long gameId = player.getGameId();
        if (gameId != null) {
            return gameRepository.findById(gameId).orElse(null);
        } else {
            return null;
        }
    }

    public String makeGuess(String guess, Player player, Game game) {
        int countM = 0;
        int countP = 0;

        int[] secretArr = numberToArrMap(game.getSecretNumber());
        int[] guessedArr = numberToArrMap(guess);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (secretArr[i] == guessedArr[j]) {
                    if (i == j) {
                        countP++;
                    } else {
                        countM++;
                    }
                }
            }
        }

        String result = "M:" + countM + " P:" + countP;

        game.decreaseTries();
        game.saveGuess(guess);

        if (result.endsWith("4") || game.getTriesLeft() <= 0) {
            result = winLooseLogic(result);
            game.setFinished(true);
            playerService.finishGame(player, result);
        }

        game.setLastResult(result);
        gameRepository.save(game);

        return result;
    }

    private String winLooseLogic(String result) {
        if (result.endsWith("4")) {
            return "You Win!";
        } else {
            return "You Loose!";
        }
    }

    private String generateSecretNumber() {
        Random r = new Random();
        Set<Integer> randomNumbers = new HashSet<>();

        while (randomNumbers.size() < 4) {
            randomNumbers.add(r.nextInt(10));
        }

        StringBuilder builder = new StringBuilder();
        for (int number: randomNumbers) {
            builder.append(number);
        }

        return builder.toString();
    }

    private int[] numberToArrMap(String number) {
        String[] digits = number.split("");
        int[] result = new int[digits.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = Integer.parseInt(digits[i]);
        }
        return result;
    }

}
