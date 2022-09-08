package com.habityouheard.habityouheard.repositories;

import com.habityouheard.habityouheard.models.Habit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Micah Young
 */
@Repository
public interface HabitRepository extends JpaRepository<Habit, Integer> {


    @Query(value = "SELECT habit_id FROM habit_selected_days where selected_days LIKE DAYNAME(CURDATE());", nativeQuery = true)
    Optional<List<Habit>> findTodaysHabit();
}
