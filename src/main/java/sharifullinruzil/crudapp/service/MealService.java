package sharifullinruzil.crudapp.service;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import sharifullinruzil.crudapp.domain.Meal;
import sharifullinruzil.crudapp.repository.MealRepository;

import java.time.LocalDate;
import java.util.List;

import static sharifullinruzil.crudapp.util.ValidationUtil.checkIfNotFound;

@Service
public class MealService {

    MealRepository mealRepository;

    public MealService(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    public Meal get(int id, int userId) {
        return checkIfNotFound(mealRepository.get(id, userId), id);
    }

    public List<Meal> getAll(int userId) {
        return mealRepository.getAll(userId);
    }


    public Meal create(Meal meal, int userId) {
        return mealRepository.save(meal, userId);
    }

    public void delete(int id, int userId) {
        checkIfNotFound(mealRepository.delete(id, userId), id);
    }

    public void update(Meal meal, int userId) {
        checkIfNotFound(mealRepository.save(meal, userId), meal.getId());
    }
}
