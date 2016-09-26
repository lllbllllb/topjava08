package ru.javawebinar.topjava;

import ru.javawebinar.topjava.util.MealsUtil;

/**
 * GKislin
 * 06.03.2015.
 */
public class AuthorizedUser {

    private static int userId;

    public static int getId() {
        return userId;
    }

    public static void setId(int id) {
        System.out.println(userId + " <-------------------- ID");
        userId = id;
    }

    public static int getCaloriesPerDay() {
        return MealsUtil.DEFAULT_CALORIES_PER_DAY;
    }
}
