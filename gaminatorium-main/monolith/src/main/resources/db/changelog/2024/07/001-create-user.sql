--liquibase formatted sql
--changeSet Slawek84PL:001

CREATE TABLE users
(
    user_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(255) UNIQUE NOT NULL,
    email     VARCHAR(255)        NOT NULL,
    password  VARCHAR(255)        NOT NULL,
    game_id   BIGINT
);
