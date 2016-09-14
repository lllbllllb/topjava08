package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepository {
    Meal add(Meal meal);
    Meal get(int id);
    Meal edit(Meal newMeal);
    void delete(int id);
    List<Meal> getAll();
}
