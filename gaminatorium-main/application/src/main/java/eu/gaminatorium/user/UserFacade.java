package eu.gaminatorium.user;

import eu.gaminatorium.game.dto.GameDto;
import eu.gaminatorium.user.dto.NewUserDto;
import eu.gaminatorium.user.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserFacade {

    private final UserService userService;


    public List<UserDto> findAll(Pageable pageable) {
        return userService.findAll(pageable);
    }

    public Optional<UserDto> getUserById(long userid) {
        return userService.getUserById(userid);

    }

    public void deleteUserById(long userid) {
        userService.deleteUserById(userid);
    }

    public Optional<NewUserDto> updateUser(long userid, NewUserDto newUserDto) {
        return userService.updateUser(userid, newUserDto);
    }

    public NewUserDto addUser(NewUserDto newUserDto) {
        return userService.addUser(newUserDto);
    }

    public Integer countAllUsers() {
        return userService.countAllUsers();
    }

    public List<GameDto> getFavoriteGames(long userid) {
        return userService.getFavoriteGames(userid);
    }

    public boolean toggleFavoriteStatus(long userid, long gameid) {
        return userService.toggleFavoriteStatus(userid, gameid);
    }

    public Optional<GameDto> getLastGamePlayed(long userid) {
        return userService.getLastGamePlayed(userid);
    }
}
