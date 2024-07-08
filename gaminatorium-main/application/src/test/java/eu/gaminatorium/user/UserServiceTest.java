package eu.gaminatorium.user;

import eu.gaminatorium.user.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    UserService userService;

    private User user;

    private UserDto userDto;

    private User user2;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .userName("User Name")
                .email("user@gaminatoriu.pl")
                .password("Password")
                .build();

        userDto = UserDto.builder()
                .userName(user.getUserName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();

        user2 = User.builder()
                .userName("User2")
                .email("user2@gaminatoriu.pl")
                .password("Password")
                .build();
    }

    @Test
    void shouldReturnUserDtoList() {
        Page<User> userPage = new PageImpl<>(List.of(user, user2));

        when(userRepository.findAll(any())).thenReturn(userPage);

        List<UserDto> result = userService.findAll(any());

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(user.getUserName(), result.getFirst().getUserName());
    }

    @Test
    void shouldCountAllUsers() {
        List<User> users = List.of(user, user2);
        when(userRepository.countAll()).thenReturn(users.size());

        long result = userService.countAllUsers();

        assertEquals(users.size(), result);
    }

    @Test
    void shouldReturnUserIfExist() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));

        Optional<UserDto> result = userService.getUserById(anyLong());

        assertTrue(result.isPresent());
        assertEquals(result.get().getUserName(), user.getUserName());
    }

    @Test
    void shouldReturnNullIfUserDoseExist() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<UserDto> result = userService.getUserById(anyLong());

        assertFalse(result.isPresent());
    }

    @Test
    void shouldCallUserRepository() {
        userRepository.deleteById(anyLong());

        verify(userRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void shouldUpdateUserIfExist() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        Optional<UserDto> result = userService.updateUser(anyLong(), userDto);

        assertTrue(result.isPresent());
    }

    @Test
    void shouldReturnEmptyIfUserDoseExist() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<UserDto> result = userService.updateUser(anyLong(), userDto);

        assertFalse(result.isPresent());
    }

    @Test
    void shouldReturnCorrectUserDto() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto result = userService.addUser(userDto);

        assertEquals(result.getUserName(), userDto.getUserName());
    }
}