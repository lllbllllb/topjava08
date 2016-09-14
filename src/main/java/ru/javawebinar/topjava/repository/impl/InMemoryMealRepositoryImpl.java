package ru.javawebinar.topjava.repository.impl;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealRepositoryImpl implements MealRepository {
    private static AtomicInteger number = new AtomicInteger(0);

    private static Map<Integer, Meal> mealRepo = new ConcurrentHashMap<>();

    {
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 500));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 1000));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    @Override
    public Meal add(Meal meal) {
        int id;

        if (meal.getId() == 0) {
            id = number.incrementAndGet();
            meal.setId(id);
        } else {
            id = meal.getId();
        }

        mealRepo.put(id, meal);
        return meal;
    }

    @Override
    public Meal get(int id) {
        return mealRepo.get(id);
    }

    @Override
    public Meal edit(Meal newMeal) {
        int id = newMeal.getId();
        mealRepo.remove(id);
        return add(newMeal);
    }

    @Override
    public void delete(int id) {
        mealRepo.remove(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealRepo.values());
    }
}
