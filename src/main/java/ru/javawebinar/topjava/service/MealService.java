package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.util.List;

public interface MealService {
    Meal add(Meal meal);
    Meal get(int id);
    Meal edit(Meal meal);
    void delete(int id);
    List<Meal> getAll();
}
