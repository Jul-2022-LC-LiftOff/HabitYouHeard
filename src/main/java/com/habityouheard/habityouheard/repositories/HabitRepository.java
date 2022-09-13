package com.habityouheard.habityouheard.repositories;

import com.habityouheard.habityouheard.models.Habit;
import com.habityouheard.habityouheard.models.HabitMeta;
import com.habityouheard.habityouheard.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Created by Micah Young
 */
@Repository
public interface HabitRepository extends JpaRepository<Habit, Integer> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE habit SET activity_status = 0 WHERE id = ?1", nativeQuery = true)
    void stopHabit(@Param("habitId") int id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE habit SET activity_status = 1 WHERE id = ?1", nativeQuery = true)
    void resumeHabit(@Param("habitId") int id);

    @Query(value = "SELECT * FROM habit WHERE activity_status=1 AND user_id = ?1", nativeQuery = true)
    Optional<Habit> findAllActiveHabits(@Param("userId") int userId);

    @Query(value = "SELECT * FROM habit WHERE activity_status=0 AND user_id = ?1", nativeQuery = true)
    Optional<Habit> findAllInactiveHabits(@Param("userId") int userId);
}
