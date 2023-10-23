package game.services;

import game.database.JpaPlayerRepository;
import game.domain.Player;
import game.requests.LoginRequest;
import game.responses.CoreError;
import game.responses.LoginResponse;
import game.services.validators.UsernameSpellingValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LoginService {

    @Autowired
    private JpaPlayerRepository playerRepository;
    @Autowired
    private UsernameSpellingValidator spellingValidator;

    public LoginResponse execute(LoginRequest request) {
        List<CoreError> spellingErrors = spellingValidator.validate(request);

        if (!spellingErrors.isEmpty()) {
            return new LoginResponse(spellingErrors);
        }

        Player player = checkDatabase(request);

        return new LoginResponse(player);
    }

    private Player checkDatabase(LoginRequest request) {
        Optional<Player> playerOptional = playerRepository.findByUsername(request.getUsername());
        Player player;
        if (playerOptional.isEmpty()) {
            player = new Player(request.getUsername());
            playerRepository.save(player);
        } else {
            player = playerOptional.get();
        }
        return player;
    }

}
