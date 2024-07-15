--liquibase formatted sql
--changeSet Slawek84PL:008

INSERT INTO games (title, description, game_tags, game_service_url, max_players, release_date, times_played_total, author_id)
VALUES ('Makao', 'A fun multiplayer card game.', 'card multi turn-based', 'https://www.gaminatorium.eu/games/makao.png',
        4, '2024-06-01', 0, 1);

INSERT INTO games (title, description, game_tags, game_service_url, source_code_url, moderation_status, max_players, release_date, times_played_total, author_id)
VALUES ('Mahjong', 'An addictive solo puzzle game.', 'puzzle solo', 'https://www.gaminatorium.eu/games/mahjong.png', 'https://github.com/keeeper85/Gaminatorium',
        'ACCEPTED', 1, '2024-06-11', 0, 2);

INSERT INTO games (title, description, game_tags, game_service_url, moderation_status, max_players, release_date, times_played_total, author_id)
VALUES ('Tic Tac Toe', 'Play this classic game with a friend or against a computer.', 'solo multi classic', 'https://www.gaminatorium.eu/games/tictactoe.png',
        'ACCEPTED', 2, '2024-06-07', 0, 3);
