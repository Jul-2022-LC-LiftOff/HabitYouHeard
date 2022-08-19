package com.habityouheard.habityouheard.repositories;

import com.habityouheard.habityouheard.models.HabitMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Micah Young
 */
@Repository
public interface HabitMetaRepository extends JpaRepository<HabitMeta,Integer> {

    @Query(value = "SELECT * FROM habit_meta where habit_id = :#{#habitId} ORDER BY `date_of_completion` DESC LIMIT 1;", nativeQuery = true)
    List<HabitMeta> findLatestByHabitId(@Param("habitId") Integer habitId);

}
