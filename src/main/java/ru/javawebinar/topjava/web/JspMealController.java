package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

/**
 * Created by Смена on 02.05.2018.
 */
@Controller
public class JspMealController extends MealRestController {

    public JspMealController(MealService service) {
        super(service);
    }

    @GetMapping("/meals")
    public String meals(Model model) {
        model.addAttribute("meals", getAll());
        return "meals";
    }

    @GetMapping("/deleteMeal")
    public String deleteMeal(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        delete(Integer.parseInt(paramId));
        return "redirect:meals";
    }

    @GetMapping("/updateMealForm")
    public String updateMealForm(HttpServletRequest request, Model model) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        Meal meal = get(Integer.parseInt(paramId));
        model.addAttribute("meal", meal);
        model.addAttribute("formHeader", "update");
        return "mealForm";
    }

    @GetMapping("/addMealForm")
    public String addMealForm(Model model) {
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        model.addAttribute("formHeader", "create");
        return "mealForm";
    }

    @PostMapping("/filteredMeals")
    public String filteredMeals(HttpServletRequest request, Model model) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        List<MealWithExceed> meals = getBetween(startDate, startTime, endDate, endTime);
        model.addAttribute("meals", meals);
        return "meals";
    }

    @PostMapping("/saveMeal")
    public String addMeal(HttpServletRequest request) {
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        if (request.getParameter("id").isEmpty()) {
            create(meal);
        } else {
            update(meal, Integer.parseInt(request.getParameter("id")));
        }
        return "redirect:meals";
    }
}
