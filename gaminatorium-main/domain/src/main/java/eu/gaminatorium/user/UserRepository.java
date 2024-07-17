package eu.gaminatorium.user;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserRepository {

    Page<User> findAllBy(Pageable pageable);
    Optional<User> findByUserName(String userName); // TODO w tym przypadku wielkość liter każdego słowa metody ma znaczenie, sprawdzić czy wielkość liter argumentu też

    Integer countAllBy();

    Optional<User> findById(long userId);

    void deleteById(long userId);

    User save(User user);
}
