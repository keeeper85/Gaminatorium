package eu.gaminatorium.user;

import eu.gaminatorium.user.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(this::mapToDto).toList();
    }

    public Optional<UserDto> getUserById(long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()){
            //TODO Exception
            return Optional.empty();
        }
        return user.map(this::mapToDto);
    }

    public void deleteUserById(long userId) {
        userRepository.deleteById(userId);
    }

    public Optional<UserDto> updateUser(long userId, UserDto userDto) {
        Optional<UserDto> existingUser = getUserById(userId);
        if (existingUser.isEmpty()) {
            //TODO Exception
            return Optional.empty();
        }
        //TODO finish update
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

    private User mapFromDto(UserDto userDto) {
        return User.builder()
                .userName(userDto.getUserName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();
    }

}
