package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.impl.MealServiceImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(UserServlet.class);

    private static final String MEAL_LIST = "mealList.jsp";
    private static final String ADD_OR_EDIT = "/mealForm.jsp";

    private MealService service = new MealServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //solve for the encoding problem in the browser
        req.setCharacterEncoding("UTF-8");

        //param of the DATE
        String date = req.getParameter("date").replace("T", " ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

        //param of the DESCRIPTION
        String description = req.getParameter("description");

        //param of the CALORIES
        String dataFromParam = req.getParameter("calories");
        int calories = (dataFromParam == null || dataFromParam.isEmpty()) ? 0 : Integer.parseInt(req.getParameter("calories"));

        //param of the ID
        String id = req.getParameter("id");
        if (id == null || id.isEmpty()) {
            service.add(new Meal(dateTime, description, calories));
        } else {
            service.edit(new Meal(Integer.parseInt(id), dateTime, description, calories));
        }

        req.setAttribute("mealsWithExceeded", MealsUtil.getWithExceeded(service.getAll(), 2000));
        req.getRequestDispatcher(MEAL_LIST).forward(req, resp);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("redirect to mealList");

        String forward;
        String action = request.getParameter("action");

        if (action == null) {
            request.setAttribute("mealsWithExceeded", MealsUtil.getWithExceeded(service.getAll(), 2000));
            forward = MEAL_LIST;
        } else if (action.equalsIgnoreCase("delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            service.delete(id);
            request.setAttribute("mealsWithExceeded", MealsUtil.getWithExceeded(service.getAll(), 2000));
            forward = MEAL_LIST;
        } else if (action.equalsIgnoreCase("edit")){
            int id = Integer.parseInt(request.getParameter("id"));
            Meal meal = service.get(id);
            request.setAttribute("meal", meal);
            forward = ADD_OR_EDIT;
        } else {
            forward = ADD_OR_EDIT;
        }

        request.getRequestDispatcher(forward).forward(request, response);
    }
}
