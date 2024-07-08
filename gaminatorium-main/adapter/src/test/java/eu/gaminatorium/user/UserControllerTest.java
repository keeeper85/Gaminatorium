package eu.gaminatorium.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.gaminatorium.user.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    private static final String BASE_URL = "/v1/users";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserFacade facade;

    private UserDto userDto;

    @BeforeEach
    void setUp() {
        userDto = UserDto.builder()
                .userName("User Name")
                .email("user@gaminatorium.pl")
                .password("password")
                .build();
    }

    @Test
    void getAllUsers() throws Exception {
        UserDto userDto1 = UserDto.builder()
                .userName("User Name2")
                .email("user2@gaminatorium.pl")
                .password("password2")
                .build();

        List<UserDto> userDtos = List.of(userDto, userDto1);

        when(facade.findAll(any())).thenReturn(userDtos);

        String jsonDtos = objectMapper.writeValueAsString(userDtos);

        mvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonDtos));
    }

    @Test
    void shouldReturnCorrectUsersAmount() throws Exception {
        int USERS_AMOUNT = 10;
        when(facade.countAllUsers()).thenReturn(USERS_AMOUNT);

        mvc.perform(get(BASE_URL + "/count"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(USERS_AMOUNT)));

        verify(facade, times(1)).countAllUsers();
    }

    @Test
    void shouldReturnUserIfExist() throws Exception {
        when(facade.getUserById(anyLong())).thenReturn(Optional.ofNullable(userDto));

        mvc.perform(get(BASE_URL + "/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userName").value(userDto.getUserName()));
    }

    @Test
    void shouldReturnStatusNotFoundIfUserDoesNotExist() throws Exception {
        when(facade.getUserById(anyLong())).thenReturn(Optional.empty());

        mvc.perform(get(BASE_URL + "/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldAddNewUser() throws Exception {
        when(facade.addUser(any(UserDto.class))).thenReturn(userDto);

        String jsonDto = objectMapper.writeValueAsString(userDto);

        mvc.perform(post(BASE_URL + "/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonDto))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userName").value(userDto.getUserName()));
    }

    @Test
    void shouldDeleteUser() throws Exception {
        mvc.perform(delete(BASE_URL + "/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldUpdateIfUserExist() throws Exception {
        when(facade.updateUser(anyLong(), any(UserDto.class))).thenReturn(Optional.ofNullable(userDto));

        String requestBody = objectMapper.writeValueAsString(userDto);

        mvc.perform(patch(BASE_URL + "/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userName").value(userDto.getUserName()));
    }

    @Test
    void shouldReturnNotFoundIfUserDoesNotExist() throws Exception {
        when(facade.updateUser(anyLong(), any(UserDto.class))).thenReturn(Optional.empty());

        String requestBody = objectMapper.writeValueAsString(userDto);

        mvc.perform(patch(BASE_URL + "/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotFound());
    }
}