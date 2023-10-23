package game.database;

import game.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaPlayerRepository extends JpaRepository<Player, Long> {

    Optional<Player> findByUsername(String userName);

}
