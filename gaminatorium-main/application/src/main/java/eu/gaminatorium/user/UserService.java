package eu.gaminatorium.user;

import eu.gaminatorium.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

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

    private User mapFromDto(UserDto userDto) {
        return User.builder()
                .userName(userDto.getUserName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();
    }
}
