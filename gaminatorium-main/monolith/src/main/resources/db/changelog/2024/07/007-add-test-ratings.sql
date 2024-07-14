--liquibase formatted sql
--changeSet Slawek84PL:007

INSERT INTO game_rating (score, comment, posting_date, game_id)
VALUES (4, 'spoko', '2024-06-01', 1),
       (1, 'shitty', '2024-06-05', 2),
       (2, 'boring', '2024-06-06', 3),
       (3, 'mediocre', '2024-06-11', 1),
       (4, 'nothing special', '2024-06-12', 2),
       (8, 'okay', '2024-06-15', 1),
       (9, 'nice', '2024-06-16', 3),
       (9, 'exciting', '2024-06-01', 2),
       (8, 'great', '2024-06-08', 1),
       (10, 'awesome', '2024-06-07', 3),
       (4, 'spoko', '2024-06-06', 2);