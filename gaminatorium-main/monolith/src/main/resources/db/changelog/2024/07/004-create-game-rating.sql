--liquibase formatted sql
--changeSet Slawek84PL:004

CREATE TABLE game_rating
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    score       INT          NOT NULL,
    comment     VARCHAR(255) NOT NULL,
    posting_date DATE,
    game_id     BIGINT       NOT NULL,
    user_id     BIGINT       NOT NULL,
    CONSTRAINT fk_rating_game FOREIGN KEY (game_id) REFERENCES games (id),
    CONSTRAINT fk_rating_user FOREIGN KEY (user_id) REFERENCES users (user_id),
    CONSTRAINT unique_game_user_rating UNIQUE (game_id, user_id)
);