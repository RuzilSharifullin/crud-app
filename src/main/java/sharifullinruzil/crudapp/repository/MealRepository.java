package sharifullinruzil.crudapp.repository;

import sharifullinruzil.crudapp.domain.Meal;

import java.util.List;

public interface MealRepository {

    Meal get(int id, int userId);

    List<Meal> getAll(int userId);

    Meal save(Meal meal, int userId);

    boolean delete(Meal meal, int userId);
}
