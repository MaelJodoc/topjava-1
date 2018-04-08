package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaMealRepositoryImpl implements MealRepository {

    private static final Sort ORDER_DESC_DATE_TIME = new Sort(Sort.Direction.DESC, "dateTime");

    private final CrudMealRepository crudRepository;

    @Autowired
    public DataJpaMealRepositoryImpl(CrudMealRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        User user = new User();
        user.setId(userId);
        meal.setUser(user);
        if (meal.isNew()) return crudRepository.save(meal);
        else {
            Meal original = get(meal.getId(), userId);
            if (original == null || original.getUser().getId() != userId) return null;
            return crudRepository.save(meal);
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        //return crudRepository.delete(id, userId) > 0;
        return crudRepository.deleteMealByIdAndUser_Id(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        //return crudRepository.get(id, userId);
        return crudRepository.getMealByIdAndUser_Id(id, userId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        //return crudRepository.getAll(userId);
        return crudRepository.findAllByUser_Id(userId, ORDER_DESC_DATE_TIME);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        //return crudRepository.getBetween(startDate, endDate, userId);
        return crudRepository.findByDateTimeBetweenAndUser_Id(startDate, endDate, userId, ORDER_DESC_DATE_TIME);
    }
}
