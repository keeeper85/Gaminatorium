--liquibase formatted sql
--changeSet Slawek84PL:004

CREATE TABLE IF NOT EXISTS game_active
(
    id              BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    current_players INTEGER                                 NOT NULL,
    started_at      TIMESTAMP WITHOUT TIME ZONE,
    game_id         BIGINT,
    CONSTRAINT pk_game_active PRIMARY KEY (id)
);

ALTER TABLE game_active
    ADD CONSTRAINT FK_GAME_ACTIVE_ON_GAME FOREIGN KEY (game_id) REFERENCES game (id);