--liquibase formatted sql
--changeSet Slawek84PL:009

CREATE TABLE IF NOT EXISTS active_game_players
(
    active_game_id INTEGER,
    user_id        INTEGER,
    CONSTRAINT pk_active_game_players PRIMARY KEY (active_game_id, user_id),
    CONSTRAINT fk_active_game_players_active_game FOREIGN KEY (active_game_id) REFERENCES game_active(active_id),
    CONSTRAINT fk_active_game_players_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);