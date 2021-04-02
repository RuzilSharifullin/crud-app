package sharifullinruzil.crudapp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import sharifullinruzil.crudapp.domain.Meal;
import sharifullinruzil.crudapp.dto.MealDto;
import sharifullinruzil.crudapp.service.MealService;
import sharifullinruzil.crudapp.util.MealsUtil;

import java.util.List;

import static sharifullinruzil.crudapp.SecurityUtil.*;

@RestController
@RequestMapping(value = "/rest/meals", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestMealController {

    @Autowired
    private MealService mealService;

    @GetMapping("/{id}")
    public Meal get(@PathVariable int id) {
        int userId = getAuthenticatedUserId();
        return mealService.get(id, userId);
    }

    @GetMapping
    public List<MealDto> getAll() {
        int userId = getAuthenticatedUserId();
        return MealsUtil.getConvertedToDto(mealService.getAll(userId), getAuthenticatedUserTargetCalories());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void create(@RequestBody Meal meal) {
        int userId = getAuthenticatedUserId();
        mealService.create(meal, userId);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Meal meal, @PathVariable int id) {
        int userId = getAuthenticatedUserId();
        mealService.update(meal, userId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        int userId = getAuthenticatedUserId();
        mealService.delete(id, userId);
    }
}
