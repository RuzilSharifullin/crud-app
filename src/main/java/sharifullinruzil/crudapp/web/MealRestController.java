package sharifullinruzil.crudapp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sharifullinruzil.crudapp.domain.Meal;
import sharifullinruzil.crudapp.dto.MealDto;
import sharifullinruzil.crudapp.service.MealService;
import sharifullinruzil.crudapp.util.MealsUtil;
import sharifullinruzil.crudapp.util.exception.NotFoundException;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static sharifullinruzil.crudapp.SecurityUtil.getAuthenticatedUserId;
import static sharifullinruzil.crudapp.SecurityUtil.getAuthenticatedUserTargetCalories;

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
    public ResponseEntity<Meal> create(@Valid @RequestBody Meal meal) {
        int userId = getAuthenticatedUserId();
        Meal created = mealService.create(meal, userId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/rest/meals/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Meal meal, @PathVariable int id) {
        int userId = getAuthenticatedUserId();
        mealService.update(meal, userId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        int userId = getAuthenticatedUserId();
        mealService.delete(id, userId);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public String validationError(MethodArgumentNotValidException ex) {
        return "MethodArgumentNotValidException!!!";
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public String uniqueConstraintError(DataIntegrityViolationException ex) {
        return "DataIntegrityViolationException!!!";
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String notFoundError(NotFoundException ex) {
        return "NotFoundException!!!";
    }
}
