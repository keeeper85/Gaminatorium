--liquibase formatted sql
--changeSet Slawek84PL:005

CREATE TABLE game_active
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    current_players INT       DEFAULT 1,
    started_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    game_id        BIGINT NOT NULL,
    user_id        BIGINT NOT NULL,
    CONSTRAINT fk_active_game FOREIGN KEY (game_id) REFERENCES games (id),
    CONSTRAINT fk_active_user FOREIGN KEY (user_id) REFERENCES users (user_id)
);