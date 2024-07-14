package eu.gaminatorium.user;

import eu.gaminatorium.game.Game;
import eu.gaminatorium.game.GameRepository;
import eu.gaminatorium.game.dto.GameDto;
import eu.gaminatorium.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    public List<UserDto> findAll(Pageable pageable) {
        return userRepository.findAllBy(pageable).getContent()
                .stream().map(this::mapToDto).toList();
    }

    public Integer countAllUsers() {
        return userRepository.countAllBy();
    }

    public Optional<UserDto> getUserById(long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return Optional.empty();
        }
        return user.map(this::mapToDto);
    }

    public void deleteUserById(long userId) {
        userRepository.deleteById(userId);
    }

    public Optional<UserDto> updateUser(long userId, UserDto userDto) {
        Optional<User> existingUserOptional = userRepository.findById(userId);
        if (existingUserOptional.isPresent()) {
            User user = existingUserOptional.get();
            if (userDto.getUserName() != null) user.setUserName(userDto.getUserName());
            if (userDto.getEmail() != null) user.setEmail(userDto.getEmail());
            if (userDto.getPassword() != null) user.setPassword(userDto.getPassword());
            return Optional.ofNullable(mapToDto(userRepository.save(user)));
        }
        return Optional.empty();
    }

    public UserDto addUser(UserDto userDto) {
        User savedUser = userRepository.save(mapFromDto(userDto));
        return mapToDto(savedUser);
    }

    private UserDto mapToDto(User user) {
        return UserDto.builder()
                .userName(user.getUserName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    private static GameDto mapToDto(Game game) {
        return GameDto.builder()
                .id(game.getId())
                .title(game.getTitle())
                .description(game.getDescription())
                .build();
    }

    private User mapFromDto(UserDto userDto) {
        return User.builder()
                .userName(userDto.getUserName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();
    }

    public List<GameDto> getFavoriteGames(long id) {
        Optional<User> existingUserOptional = userRepository.findById(id);
        if (existingUserOptional.isPresent()) {
            User user = existingUserOptional.get();
            return  user.getMyFavoriteGames().stream().map(UserService::mapToDto).toList();
        }
        return List.of();
    }

    public boolean toggleFavoriteStatus(long id, long gameid) {
        Optional<User> existingUserOptional = userRepository.findById(id);
        if (existingUserOptional.isPresent() && gameRepository.existsById(gameid)) {
            User user = existingUserOptional.get();
            Game game = gameRepository.findById(gameid);
            user.toggleFavoriteGame(game);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public Optional<GameDto> getLastGamePlayed(long id) {
        return null;
        //todo
    }
}
