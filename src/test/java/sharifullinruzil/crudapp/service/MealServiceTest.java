package sharifullinruzil.crudapp.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import sharifullinruzil.crudapp.config.TestConfig;
import sharifullinruzil.crudapp.domain.Meal;
import sharifullinruzil.crudapp.util.NotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static sharifullinruzil.crudapp.MealTestData.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
@Transactional
@Rollback
class MealServiceTest {

    @Autowired
    MealService mealService;


    @Test
    void get() {
        Meal meal = mealService.get(MEAL1_ID + 3, USER_ID);
        assertThat(meal).usingRecursiveComparison().isEqualTo(meal4);
    }

    @Test
    void getNonexistent() {
        assertThrows(NotFoundException.class, () -> mealService.get(WRONG_MEAL_ID, USER_ID));
        assertThrows(NotFoundException.class, () -> mealService.get(MEAL1_ID, WRONG_USER_ID));
    }

    @Test
    void getAll() {
        List<Meal> actual = mealService.getAll(USER_ID);
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(meals);
    }

    @Test
    void create() {
        Meal created = mealService.create(getNew(), USER_ID);
        Integer newId = created.getId();
        Assert.notNull(newId, "Must have an id");
        Meal expected = getNew();
        expected.setId(created.getId());
        assertThat(created).usingRecursiveComparison().isEqualTo(expected);
        assertThat(mealService.get(newId, USER_ID)).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void createWithException() {
        assertThrows(DuplicateKeyException.class, () -> mealService.create(new Meal(null, meal9.getDateTime(), "we have the unique constraint on user_id and date_time columns so this record won't be written", 500), USER_ID));
    }

    @Test
    void delete() {
        mealService.delete(MEAL1_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> mealService.delete(MEAL1_ID, USER_ID));
    }

    @Test
    void deleteNonexistent() {
        assertThrows(NotFoundException.class, () -> mealService.delete(WRONG_MEAL_ID, USER_ID));
    }

    @Test
    void update() {
        Meal updated = getUpdated();
        mealService.update(updated, USER_ID);
        Meal actual = mealService.get(updated.getId(), USER_ID);
        assertThat(actual).usingRecursiveComparison().isEqualTo(updated);
    }

    @Test
    void updateNonexistent() {
        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> mealService.update(meal1, WRONG_USER_ID));
        Assertions.assertEquals("Unable to find entity with id=" + meal1.getId(), notFoundException.getMessage());
    }
}