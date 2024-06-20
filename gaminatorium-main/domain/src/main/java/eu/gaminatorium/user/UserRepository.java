package eu.gaminatorium.user;


import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAll();
    Optional<User> findById(long userId);
    void deleteById(long userId);
    User save(User user);

}
