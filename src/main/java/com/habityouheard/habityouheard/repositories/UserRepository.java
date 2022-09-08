package com.habityouheard.habityouheard.repositories;

import com.habityouheard.habityouheard.models.Habit;
import com.habityouheard.habityouheard.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
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

    @Query(value = "SELECT * FROM habit_selected_days join habit where selected_days LIKE DAYNAME(CURDATE())", nativeQuery = true)
    Optional<List<User>> findBySelectedDays();

}
