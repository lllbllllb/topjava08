package ru.javawebinar.topjava.service.impl;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.impl.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.service.MealService;

import java.util.List;

public class MealServiceImpl implements MealService {
    MealRepository repository = new InMemoryMealRepositoryImpl();

    @Override
    public Meal add(Meal meal) {
        return repository.add(meal);
    }

    @Override
    public Meal get(int id) {
        return repository.get(id);
    }

    @Override
    public Meal edit(Meal meal) {
        return repository.edit(meal);
    }

    @Override
    public void delete(int id) {
        repository.delete(id);
    }

    @Override
    public List<Meal> getAll() {
        return repository.getAll();
    }
}
