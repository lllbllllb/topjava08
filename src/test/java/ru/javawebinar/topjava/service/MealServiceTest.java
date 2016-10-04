package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.MealTestData.MATCHER;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml",
//        "classpath:spring/spring-mock.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {

    @Autowired
    private MealService mealService;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void get() throws Exception {
        Meal meal = mealService.get(USER_MEAL_1.getId(), USER_ID);
        MATCHER.assertEquals(meal, USER_MEAL_1);
    }

    @Test
    public void delete() throws Exception {
        mealService.delete(ADMIN_MEAL_1.getId(), ADMIN_ID);
        MATCHER.assertCollectionEquals(Collections.singletonList(ADMIN_MEAL_2), mealService.getAll(ADMIN_ID));
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        Collection<Meal> meals = mealService.getBetweenDateTimes(
                LocalDateTime.of(2015, Month.MAY, 30, 9, 0), LocalDateTime.of(2015, Month.MAY, 30, 14, 0), USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(USER_MEAL_1, USER_MEAL_2), meals);
    }

    @Test
    public void getAll() throws Exception {
        Collection<Meal> mealList = mealService.getAll(ADMIN_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN_MEAL_1, ADMIN_MEAL_2), mealList);
    }

    @Test
    public void update() throws Exception {
        Meal updated = new Meal(USER_MEAL_1);
        updated.setCalories(5000);
        updated.setDescription(updated.getDescription() + " UPDATED!");
        mealService.update(updated, USER_ID);
        MATCHER.assertEquals(updated, mealService.get(updated.getId(), USER_ID));
    }

    @Test
    public void save() throws Exception {
        Meal newMeal = new Meal(null, LocalDateTime.of(2016, Month.MAY, 30, 10, 0), "newMeal", 2000);
        Meal created = mealService.save(newMeal, ADMIN_ID);
        newMeal.setId(created.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN_MEAL_1, ADMIN_MEAL_2, newMeal), mealService.getAll(ADMIN_ID));
    }

    @Test(expected = NotFoundException.class)
    public void deleteOtherUser() throws Exception {
        mealService.delete(ADMIN_MEAL_1.getId(), 10);
    }

    @Test(expected = NotFoundException.class)
    public void getOtherUser() throws Exception {
        Meal meal = mealService.get(USER_MEAL_1.getId(), 10);
    }

    @Test(expected = NotFoundException.class)
    public void updateOtherUser() throws Exception {
        Meal updated = new Meal(USER_MEAL_1);
        updated.setCalories(5000);
        updated.setDescription(updated.getDescription() + " UPDATED!");
        mealService.update(updated, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void updateOtherMeal() throws Exception {
        Meal updated = new Meal(ADMIN_MEAL_1);
        updated.setCalories(5000);
        updated.setDescription(updated.getDescription() + " UPDATED!");
        mealService.update(updated, USER_ID);
    }
}