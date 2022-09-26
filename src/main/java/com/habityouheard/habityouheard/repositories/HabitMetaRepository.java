package com.habityouheard.habityouheard.repositories;

import com.habityouheard.habityouheard.models.HabitMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by Micah Young
 */
@Repository
public interface HabitMetaRepository extends JpaRepository<HabitMeta,Integer> {

    @Query(value = "SELECT * FROM habit_meta where habit_id= ?1 ORDER BY `date_of_completion` DESC LIMIT 1;", nativeQuery = true)
    Optional<HabitMeta> findLatestByHabitId(@Param("habitId") int habitId);

    @Query(value = "SELECT date_of_completion FROM habit_meta where habit_id= ?1 ORDER BY `date_of_completion` DESC LIMIT 1;", nativeQuery = true)
    Optional<Date> findLatestDateByHabitId(@Param("habitId") int habitId);

    @Query(value = "SELECT * FROM habit_meta where CURDATE() = DATE(`date_of_completion`) AND habit_id= ?1 ORDER BY `date_of_completion` DESC LIMIT 1;", nativeQuery = true)
    Optional<HabitMeta> findLatestDateByTodaysHabitId(@Param("habitId") int habitId);

}
