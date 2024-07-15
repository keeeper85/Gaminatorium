--liquibase formatted sql
--changeSet Slawek84PL:009

INSERT INTO game_rating (score, comment, posting_date, game_id, user_id)
VALUES (4, 'spoko', '2024-06-01', 1, 1),
       (1, 'shitty', '2024-06-05', 2, 2),
       (2, 'boring', '2024-06-06', 3, 3)
       ;