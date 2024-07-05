package eu.gaminatorium.user;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserRepository {

    Page<User> findAll(Pageable pageable);

    Integer countAll();

    Optional<User> findById(long userId);

    void deleteById(long userId);

    User save(User user);
}
