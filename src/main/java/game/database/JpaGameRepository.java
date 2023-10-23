package game.database;

import game.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaGameRepository extends JpaRepository<Game, Long> {

}
