package com.habityouheard.habityouheard.repositories;

import com.habityouheard.habityouheard.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Created by Micah Young
 */

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Transactional

    Optional<User> findByUsername(String username);
    @Transactional

    Optional<User> findByAuthToken(String authToken);
}
