package sharifullinruzil.crudapp.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class MealRestController {

    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    @Autowired
    private MealService mealService;

    @GetMapping("/{id}")
    public Meal get(@PathVariable int id) {
        int userId = getAuthenticatedUserId();
        log.info("get meal {} for user {}", id, userId);
        return mealService.get(id, userId);
    }

    @GetMapping
    public List<MealDto> getAll() {
        int userId = getAuthenticatedUserId();
        log.info("getAll for user {}", userId);
        return MealsUtil.getConvertedToDto(mealService.getAll(userId), getAuthenticatedUserTargetCalories());
    }

    @PostMapping
    public ResponseEntity<Meal> create(@Valid @RequestBody Meal meal) {
        int userId = getAuthenticatedUserId();
        log.info("create {} for user {}", meal, userId);
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
        log.info("update {} for user {}", meal, userId);
        mealService.update(meal, userId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        int userId = getAuthenticatedUserId();
        log.info("delete meal {} for user {}", id, userId);
        mealService.delete(id, userId);
    }

}
