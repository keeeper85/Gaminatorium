--liquibase formatted sql
--changeSet Slawek84PL:008

CREATE TABLE IF NOT EXISTS user_favorite_games
(
    user_id   INTEGER,
    game_id   INTEGER,
    CONSTRAINT pk_user_favorite_games PRIMARY KEY (user_id, game_id),
    CONSTRAINT fk_user_favorite_games_user FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT fk_user_favorite_games_game FOREIGN KEY (game_id) REFERENCES games(game_id)
);