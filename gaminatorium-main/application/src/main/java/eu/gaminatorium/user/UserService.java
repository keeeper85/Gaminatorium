package eu.gaminatorium.user;

import eu.gaminatorium.game.Game;
import eu.gaminatorium.game.GameRepository;
import eu.gaminatorium.game.dto.GameDto;
import eu.gaminatorium.user.dto.NewUserDto;
import eu.gaminatorium.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    public List<UserDto> findAll(Pageable pageable) {
        return userRepository.findAllBy(pageable).getContent()
                .stream().map(this::mapToUserDto).toList();
    }

    public Integer countAllUsers() {
        return userRepository.countAllBy();
    }

    public Optional<UserDto> getUserById(long userid) {
        Optional<User> user = userRepository.findById(userid);
        if (user.isEmpty()) {
            return Optional.empty();
        }
        return user.map(this::mapToUserDto);
    }

    public void deleteUserById(long userid) {
        gameRepository.reassignGamesToFirstUser(userid);
        userRepository.deleteById(userid);
    }

    public Optional<NewUserDto> updateUser(long userid, NewUserDto newUserDto) {
        Optional<User> existingUserOptional = userRepository.findById(userid);
        if (existingUserOptional.isPresent()) {
            User user = existingUserOptional.get();
            if (newUserDto.getUserName() != null) user.setUserName(newUserDto.getUserName());
            if (newUserDto.getEmail() != null) user.setEmail(newUserDto.getEmail());
            if (newUserDto.getPassword() != null) user.setPassword(newUserDto.getPassword());
            return Optional.ofNullable(mapToNewUserDto(userRepository.save(user)));
        }
        return Optional.empty();
    }

    public NewUserDto addUser(NewUserDto newUserDto) {
        User savedUser = userRepository.save(mapFromDto(newUserDto));
        return mapToNewUserDto(savedUser);
    }

    private UserDto mapToUserDto(User user) {
        if (user == null) return null;
        return UserDto.builder()
                .userName(user.getUserName())
                .email(user.getEmail())
                .password(user.getPassword())
                .lastGamePlayed(user.getLastGamePlayed() != null ? user.getLastGamePlayed().toString() : "Empty")
                .favoriteGames(user.getFavoriteGames().stream().map(game -> game.getTitle()).collect(Collectors.toUnmodifiableSet()))
                .build();
    }

    private NewUserDto mapToNewUserDto(User user) {
        if (user == null) return null;
        return NewUserDto.builder()
                .userName(user.getUserName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    private static GameDto mapToGameDto(Game game) {
        if (game == null) return null;
        return GameDto.builder()
                .gameid(game.getId())
                .title(game.getTitle())
                .description(game.getDescription())
                .build();
    }

    private User mapFromDto(NewUserDto newUserDto) {
        if (newUserDto == null) return null;
        return User.builder()
                .userName(newUserDto.getUserName())
                .email(newUserDto.getEmail())
                .password(newUserDto.getPassword())
                .build();
    }

    public List<GameDto> getFavoriteGames(long userid) {
        Optional<User> existingUserOptional = userRepository.findById(userid);
        if (existingUserOptional.isPresent()) {
            User user = existingUserOptional.get();
            return  user.getFavoriteGames().stream().map(UserService::mapToGameDto).toList();
        }
        return List.of();
    }

    public boolean toggleFavoriteStatus(long userid, long gameid) {
        Optional<User> existingUserOptional = userRepository.findById(userid);
        Optional<Game> gameOptional = gameRepository.findById(gameid);
        if (existingUserOptional.isPresent() && gameOptional.isPresent()) {
            User user = existingUserOptional.get();
            Game game = gameOptional.get();
            user.toggleFavoriteGame(game);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public Optional<GameDto> getLastGamePlayed(long userid) {
        Optional<User> existingUserOptional = userRepository.findById(userid);
        if (existingUserOptional.isPresent()) {
            User user = existingUserOptional.get();
            Game lastGamePlayed = user.getLastGamePlayed();
            return Optional.ofNullable(mapToGameDto(lastGamePlayed));
        }
        return Optional.empty();
    }
}
