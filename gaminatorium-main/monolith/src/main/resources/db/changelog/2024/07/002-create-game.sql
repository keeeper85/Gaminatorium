--liquibase formatted sql
--changeSet Slawek84PL:002

CREATE TABLE IF NOT EXISTS game
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    title              VARCHAR(30),
    description        VARCHAR(200),
    game_tags          VARCHAR(255),
    game_service_link  VARCHAR(255),
    source_code_link   VARCHAR(255),
    moderation_status  SMALLINT,
    max_players        INTEGER                                 NOT NULL,
    times_played_total BIGINT                                  NOT NULL,
    release_date       date,
    last_time_played   TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_game PRIMARY KEY (id)
);