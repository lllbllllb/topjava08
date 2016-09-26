package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.ExceptionUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Service
public class MealServiceImpl implements MealService {
    @Autowired
    private MealRepository repository;

    @Override
    public Meal save(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    @Override
    public Meal update(Meal meal, int userId) throws NotFoundException {
        return ExceptionUtil.checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }

    @Override
    public void delete(int mealId, int userId) throws NotFoundException {
        ExceptionUtil.checkNotFoundWithId(repository.delete(mealId, userId), mealId);
    }

    @Override
    public Meal get(int mealId, int userId) throws NotFoundException {
        return ExceptionUtil.checkNotFoundWithId(repository.get(mealId, userId), mealId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    @Override
    public List<Meal> getSortedByDateTime(int userId, String startDate, String endDate, String startTime, String endTime) {
        return repository.getSortedByDateTime(userId, startDate, endDate, startTime, endTime);
    }
}
