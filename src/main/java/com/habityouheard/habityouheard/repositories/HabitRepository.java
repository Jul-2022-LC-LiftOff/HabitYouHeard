package com.habityouheard.habityouheard.repositories;

import com.habityouheard.habityouheard.models.Habit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Micah Young
 */
@Repository
public interface HabitRepository extends JpaRepository<Habit, Integer> {
}
