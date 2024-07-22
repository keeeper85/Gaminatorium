--liquibase formatted sql
--changeSet Slawek84PL:002

CREATE TABLE games
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    title             VARCHAR(30)  NOT NULL,
    description       VARCHAR(200) NOT NULL,
    game_tags          VARCHAR(255) DEFAULT '',
    game_service_url   VARCHAR(255),
    source_code_url    VARCHAR(255),
    moderation_status VARCHAR(255) DEFAULT 'PENDING',
    max_players        INT          NOT NULL,
    times_played_total  BIGINT       NOT NULL,
    release_date       DATE,
    last_time_played    TIMESTAMP,
    author_id         BIGINT,
    CONSTRAINT fk_game_author FOREIGN KEY (author_id) REFERENCES users (user_id)
);

ALTER TABLE users
    ADD CONSTRAINT fk_users_last_game_played FOREIGN KEY (game_id) REFERENCES games (id);


