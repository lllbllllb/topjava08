package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    private MealRestController controller;

    {
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            controller = appCtx.getBean(MealRestController.class);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if (action.equals("save")) {
            String id = request.getParameter("id");

            Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.valueOf(request.getParameter("calories")));

            LOG.info(meal.isNew() ? "Create {}" : "Update {}", meal);
            controller.save(meal);
            response.sendRedirect("meals");
        }

        if (action.equals("filter")) {
            String startDate = request.getParameter("dateStart");
            String endDate = request.getParameter("dateEnd");
            String startTime = request.getParameter("timeStart");
            String endTime = request.getParameter("timeEnd");
            request.setAttribute("mealList", controller.getSortedByDateTime(startDate, endDate, startTime, endTime));
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            LOG.info("getAll");
            request.setAttribute("mealList", controller.getAll());
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);

        } else if ("delete".equals(action)) {
            int id = getId(request);
            LOG.info("Delete {}", id);
            controller.delete(id);
            response.sendRedirect("meals");

        } else if ("create".equals(action) || "update".equals(action)) {
            final Meal meal = action.equals("create") ?
                    new Meal(LocalDateTime.now().withNano(0).withSecond(0), "", 1000) :
                    controller.get(getId(request));
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
