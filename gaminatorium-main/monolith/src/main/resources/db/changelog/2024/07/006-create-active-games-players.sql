--liquibase formatted sql
--changeSet Slawek84PL:006

CREATE TABLE active_game_players
(
    active_game_id BIGINT,
    user_id        BIGINT,
    CONSTRAINT fk_active_game_player FOREIGN KEY (active_game_id) REFERENCES game_active (id),
    CONSTRAINT fk_active_game_user FOREIGN KEY (user_id) REFERENCES users (user_id),
    PRIMARY KEY (active_game_id, user_id)
);