package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    // null if updated meal do not belong to userId
    //Meal save(Meal meal, int userId);

    // false if meal do not belong to userId
    @Transactional
    @Query("DELETE FROM Meal m WHERE m.id=:mealId AND m.user.id=:userId")
    boolean delete(@Param("mealId") int id, @Param("userId") int userId);

    // null if meal do not belong to userId
    @Query("SELECT m FROM Meal m WHERE m.id=:mealId AND m.user.id=:userId")
    Meal get(int id, int userId);

    // ORDERED dateTime desc
    @Query("SELECT m FROM Meal m WHERE m.user.id=:userId ")
    List<Meal> getAll(@Param("userId") int userId);

    // ORDERED dateTime desc
    @Query("SELECT m FROM Meal m WHERE m.user.id=:userId AND m.dateTime>=:startDate AND m.dateTime<=:endDate")
    List<Meal> getBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("userId") int userId);
}
