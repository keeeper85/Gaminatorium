--liquibase formatted sql
--changeSet Slawek84PL:006

insert into game (title, description,
                  game_tags, game_service_link,
                  max_players, release_date, times_played_total)
VALUES ('Makao', 'A fun multiplayer card game.',
        'card multi turn-based', 'https://www.gaminatorium.eu/games/makao.png',
        4, '2024-06-01', 0);

--changeSet Slawek84PL:001_5
insert into game (title, description, game_service_link,
                  max_players, moderation_status, release_date, times_played_total)
VALUES ('Mahjong', 'An addictve solo puzzle game.','https://www.gaminatorium.eu/games/mahjong.png',
        1, 1, '2024-05-31', 0);

--changeSet Slawek84PL:001_6
insert into game (title, description, game_service_link,
                  max_players, moderation_status, release_date, times_played_total)
VALUES ('Tictactoe', 'Play this classic game with a friend or against a computer.','https://www.gaminatorium.eu/games/tictactoe.png',
        2, 1, '2024-06-15', 17);