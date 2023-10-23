CREATE TABLE games (
  game_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  secret_number VARCHAR(255) NOT NULL,
  tries_left INT NOT NULL,
  last_result VARCHAR(255),
  is_finished BOOLEAN NOT NULL
);

CREATE TABLE game_turns (
  game_id BIGINT,
  turn VARCHAR(255),
  FOREIGN KEY (game_id) REFERENCES games(game_id)
);

CREATE TABLE players (
  player_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(255) NOT NULL,
  correct_guesses INT NOT NULL,
  games_played INT NOT NULL,
  score DOUBLE PRECISION NOT NULL,
  game_id BIGINT,
  FOREIGN KEY (game_id) REFERENCES games(game_id)
);

