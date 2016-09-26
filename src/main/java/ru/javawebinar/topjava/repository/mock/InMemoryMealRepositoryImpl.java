package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);

    //Map<userId, Map<mealId, Meal>>
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        Map<Integer, Meal> userMeal = new ConcurrentHashMap<>();
        repository.put(AuthorizedUser.getId(), userMeal);
        MealsUtil.MEALS.forEach((meal -> save(meal, AuthorizedUser.getId())));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        LOG.info("save meal " + meal + " userId=" + userId);

        Map<Integer, Meal> userMeal = repository.get(userId);

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        userMeal.put(meal.getId(), meal);

        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        LOG.info("delete=" + id + " userId=" + userId);

        Map<Integer, Meal> userMeal = repository.get(userId);

        return userMeal.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        LOG.info("get=" + id + " userId=" + userId);

        Map<Integer, Meal> userMeal = repository.get(userId);

        return userMeal.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        LOG.info("getAll userId=" + userId);

        Map<Integer, Meal> userMeal = repository.get(userId);

        return userMeal.values().stream()
                .sorted((o1, o2) -> o1.getDate().compareTo(o2.getDate()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getSortedByDateTime(int userId, String startDate, String endDate, String startTime, String endTime) {
        LOG.info("getSortedByDateTime userId=" + userId);

        LocalDate fromDate = LocalDate.MIN;
        LocalDate toDate = LocalDate.MAX;
        LocalTime fromTime = LocalTime.MIN;
        LocalTime toTime = LocalTime.MAX;

        if (!startDate.equals("")) {
            fromDate = LocalDate.parse(startDate);
        }

        if (!endDate.equals("")) {
            toDate = LocalDate.parse(endDate);
        }

        if (!startTime.equals("")) {
            fromTime = LocalTime.parse(startTime);
        }

        if (!endTime.equals("")) {
            toTime = LocalTime.parse(endTime);
        }

        LocalDateTime start = LocalDateTime.of(fromDate, fromTime);
        LocalDateTime end = LocalDateTime.of(toDate, toTime);

        Map<Integer, Meal> userMeal = repository.get(userId);

        return userMeal.values().stream()
                .filter(meal -> TimeUtil.isBetween(meal.getDateTime(), start, end))
                .collect(Collectors.toList());
    }
}

