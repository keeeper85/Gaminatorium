package eu.gaminatorium.user;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SqlUserRepository extends UserRepository, JpaRepository<User, Integer> {

    Page<User> findAll(Pageable pageable);

    Integer countAll();

    Optional<User> findById(long userId);

    void deleteById(long userId);

    User save(User user);

}
