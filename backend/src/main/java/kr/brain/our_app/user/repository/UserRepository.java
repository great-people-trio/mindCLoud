package kr.brain.our_app.user.repository;

import kr.brain.our_app.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User>findById(String id);
    /**************************************************/
    Optional<User>findByEmail(String email);
    /**************************************************/
}
