package eu.gaminatorium.user;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserRepository {

    Page<User> findAllBy(Pageable pageable);

    Integer countAllBy();

    Optional<User> findById(long userId);

    void deleteById(long userId);

    User save(User user);

    boolean existsById(long userId);
}
