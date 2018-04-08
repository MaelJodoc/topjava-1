package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    // null if updated meal do not belong to userId
    //Meal save(Meal meal, int userId);

    // false if meal do not belong to userId
    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id=:mealId AND m.user.id=:userId")
    int delete(@Param("mealId") int id, @Param("userId") int userId);

    @Transactional
    Integer deleteMealByIdAndUser_Id(int mealId, int userId);

    // null if meal do not belong to userId
    @Query("SELECT m FROM Meal m WHERE m.id=:mealId AND m.user.id=:userId")
    Meal get(@Param("mealId") int id, @Param("userId") int userId);

    Meal getMealByIdAndUser_Id(int mealId, int userId);

    // ORDERED dateTime desc
    @Query("SELECT m FROM Meal m WHERE m.user.id=:userId ORDER BY m.dateTime DESC")
    List<Meal> getAll(@Param("userId") int userId);

    List<Meal> findAllByUser_Id(int userId, Sort sort);

    // ORDERED dateTime desc
    @Query("SELECT m FROM Meal m WHERE m.user.id=:userId AND m.dateTime>=:startDate AND m.dateTime<=:endDate ORDER BY m.dateTime DESC")
    List<Meal> getBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("userId") int userId);

    List<Meal> findByDateTimeBetweenAndUser_Id(LocalDateTime startDate, LocalDateTime endDate, int userId, Sort sort);
}
