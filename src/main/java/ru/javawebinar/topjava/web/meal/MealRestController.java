package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class MealRestController {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private int userId = AuthorizedUser.getId();
    private int calories = AuthorizedUser.getCaloriesPerDay();

    @Autowired
    private MealService service;

    public Meal save(Meal meal) {
        LOG.info("save=" + meal + " user=" + userId);
        return service.save(meal, userId);
    }

    public Meal update(Meal meal) throws NotFoundException {
        LOG.info("update=" + meal + " user=" + userId);
        return service.save(meal, userId);
    }

    public void delete(int mealId) throws NotFoundException {
        LOG.info("delete=" + mealId + " user=" + userId);

        service.delete(mealId, userId);
    }

    public Meal get(int mealId) throws NotFoundException {
        LOG.info("get=" + mealId + " user=" + userId);
        return service.get(mealId, userId);
    }

    public List<MealWithExceed> getAll() {
        LOG.info("getAll userId=" + userId);
        return MealsUtil.getWithExceeded(service.getAll(userId), calories);
    }

    public List<MealWithExceed> getSortedByDateTime(String startDate, String endDate, String startTime, String endTime) {
        LOG.info("getSortedByDateTime userId=" + userId);
        List<MealWithExceed> list =
                MealsUtil.getWithExceeded(service.getSortedByDateTime(userId, startDate, endDate, startTime, endTime), calories);
        return list;
    }
}
