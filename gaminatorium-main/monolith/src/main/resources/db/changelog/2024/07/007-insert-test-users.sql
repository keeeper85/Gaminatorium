--liquibase formatted sql
--changeSet Slawek84PL:007

INSERT INTO users (user_name, email, password)
VALUES ('user', 'user@gaminatorium.eu', 'pass'),
       ('admin', 'admin@gaminatorium.eu', 'pass'),
       ('Rick', 'rick@gaminatorium.eu', 'pass');