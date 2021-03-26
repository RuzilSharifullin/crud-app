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
import sharifullinruzil.crudapp.TestDBConfig;
import sharifullinruzil.crudapp.domain.Meal;
import sharifullinruzil.crudapp.util.NotFoundException;

import java.time.Month;
import java.util.List;

import static java.time.LocalDateTime.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestDBConfig.class})
@Transactional
@Rollback
class MealServiceTest {

    public static final int MEAL1_ID = 100_000;
    public static final int USER_ID = 111;
    public static final int WRONG_MEAL_ID = 10;
    public static final int WRONG_USER_ID = 666;

    public static final Meal meal1 = new Meal(MEAL1_ID, of(1999, Month.AUGUST, 15, 10, 0), "scrambled eggs with smoked salmon", 500);
    public static final Meal meal2 = new Meal(MEAL1_ID + 1, of(1999, Month.AUGUST, 15, 15, 0), "pesto lasagna rolls", 1000);
    public static final Meal meal3 = new Meal(MEAL1_ID + 2, of(1999, Month.AUGUST, 15, 23, 0), "chicken and broccoli stir-fry", 500);
    public static final Meal meal4 = new Meal(MEAL1_ID + 3, of(1999, Month.SEPTEMBER, 9, 7, 0), "huge burger", 700);
    public static final Meal meal5 = new Meal(MEAL1_ID + 4, of(1999, Month.SEPTEMBER, 9, 14, 0), "roasted sweet potatoes with honey and cinnamon", 700);
    public static final Meal meal6 = new Meal(MEAL1_ID + 5, of(1999, Month.SEPTEMBER, 9, 19, 0), "cheese danish", 300);
    public static final Meal meal7 = new Meal(MEAL1_ID + 6, of(1999, Month.NOVEMBER, 12, 8, 0), "spaghetti in alfredo sauce", 1000);
    public static final Meal meal8 = new Meal(MEAL1_ID + 7, of(1999, Month.NOVEMBER, 12, 12, 0), "seaweed salad", 400);
    public static final Meal meal9 = new Meal(MEAL1_ID + 8, of(1999, Month.NOVEMBER, 12, 20, 0), "lion king roll", 800);
    public static final List<Meal> meals = List.of(meal1, meal2, meal3, meal4, meal5, meal6, meal7, meal8, meal9);
    @Autowired
    MealService mealService;

    public static Meal getNew() {
        return new Meal(null, of(2050, Month.JANUARY, 24, 11, 30), "a new one for create() test case", 1500);
    }

    public static Meal getUpdated() {
        return new Meal(meal3.getId(), meal3.getDateTime(), "an updated for update() test case", 999);
    }

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