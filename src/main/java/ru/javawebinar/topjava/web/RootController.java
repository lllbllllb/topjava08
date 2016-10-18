package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
@Controller
public class RootController {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private MealService mealService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
        return "index";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String users(Model model) {
        model.addAttribute("users", userService.getAll());
        return "users";
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String setUser(HttpServletRequest request) {
        int userId = Integer.valueOf(request.getParameter("userId"));
        AuthorizedUser.setId(userId);
        return "redirect:meals";
    }

    @GetMapping("/meals")
    public String meals(Model model) {
        LOG.info("getAll");
        int userId = AuthorizedUser.id();
        List<MealWithExceed> mealWithExceeds = MealsUtil.getWithExceeded(mealService.getAll(userId), AuthorizedUser.getCaloriesPerDay());
        model.addAttribute("meals", mealWithExceeds);
        return "meals";
    }

    @PostMapping("/meals")
    public String filterMeals(HttpServletRequest request) {
        int userId = AuthorizedUser.id();

        String action = request.getParameter("action");

        if ("filter".equals(action)) {

            LocalDate startDate = TimeUtil.parseLocalDate(resetParam("startDate", request));
            LocalDate endDate = TimeUtil.parseLocalDate(resetParam("endDate", request));
            LocalTime startTime = TimeUtil.parseLocalTime(resetParam("startTime", request));
            LocalTime endTime = TimeUtil.parseLocalTime(resetParam("endTime", request));

            List<MealWithExceed> mealWithExceeds = MealsUtil.getFilteredWithExceeded(
                    mealService.getBetweenDates(startDate != null ? startDate : TimeUtil.MIN_DATE,
                            endDate != null ? endDate : TimeUtil.MAX_DATE, userId),
                    startTime != null ? startTime : LocalTime.MIN,
                    endTime != null ? endTime : LocalTime.MAX,
                    AuthorizedUser.getCaloriesPerDay());
            request.setAttribute("meals", mealWithExceeds);
            return "meals";
        }

        return "meals";
    }

    @GetMapping("/meals/del/{mealId}")
    public String delMeal(@PathVariable int mealId) {
        LOG.info("Delete {}", mealId);
        int userId = AuthorizedUser.id();
        mealService.delete(mealId, userId);
        return "redirect:/meals";
    }

    @GetMapping("/meals/upd/{mealId}")
    public String meal(@PathVariable("mealId") String mealState, Model model) {
        int userId = AuthorizedUser.id();

        final Meal meal = "new".equals(mealState) ?
                new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), "", 1000) :
                mealService.get(Integer.parseInt(mealState), userId);
        model.addAttribute("meal", meal);
        return "meal";
    }

    @PostMapping("/meals/upd/{mealId}")
    public String updMeals(HttpServletRequest request) throws ServletException, IOException {
        int userId = AuthorizedUser.id();

        request.setCharacterEncoding("UTF-8");

        final Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));

        if (request.getParameter("id").isEmpty()) {
            LOG.info("Create {}", meal);
            mealService.save(meal, userId);
        } else {
            LOG.info("Update {}", meal);
            int id = Integer.parseInt(request.getParameter("id"));
            meal.setId(id);
            mealService.update(meal, userId);
        }

        return "redirect:/meals";
    }


    private String resetParam(String param, HttpServletRequest request) {
        String value = request.getParameter(param);
        request.setAttribute(param, value);
        return value;
    }
}
