package sharifullinruzil.crudapp.util;

import sharifullinruzil.crudapp.domain.Meal;
import sharifullinruzil.crudapp.dto.MealDto;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MealsUtil {

    public static List<MealDto> getConvertedToDto(Collection<Meal> meals, int dailyCalorieLimit) {

        Map<LocalDate, Integer> totalCaloriesPerDay = meals.stream().
                collect(Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories)));

        return meals.stream().
                map(meal -> createDto(meal, totalCaloriesPerDay.get(meal.getDate()) > dailyCalorieLimit)).
                collect(Collectors.toList());
    }

    private static MealDto createDto(Meal meal, boolean excess) {
        return new MealDto(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }

}

