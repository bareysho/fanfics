package by.bareysho.fanfics.repository;

import by.bareysho.fanfics.model.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<CustomUser, Long> {
    CustomUser findByUsername(String username);

    CustomUser findByEmail(String email);

    CustomUser findByPassword(String password);

    CustomUser findById(Long id);

    void removeCustomUserById(Long id);

    CustomUser findByConfirmationToken(String token);
}
