--liquibase formatted sql
--changeSet Slawek84PL:003

CREATE TABLE user_favorite_games
(
    user_id BIGINT,
    game_id BIGINT,
    CONSTRAINT fk_user_favorite FOREIGN KEY (user_id) REFERENCES users (user_id),
    CONSTRAINT fk_game_favorite FOREIGN KEY (game_id) REFERENCES games (id),
    PRIMARY KEY (user_id, game_id)
);