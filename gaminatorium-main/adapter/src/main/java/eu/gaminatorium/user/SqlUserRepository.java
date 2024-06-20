package eu.gaminatorium.user;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SqlUserRepository extends UserRepository, JpaRepository<User, Integer> {

    List<User> findAll();
    Optional<User> findById(long userId);
    void deleteById(long userId);
    User save(User user);

}
