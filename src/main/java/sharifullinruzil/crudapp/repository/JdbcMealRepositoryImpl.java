package sharifullinruzil.crudapp.repository;

import sharifullinruzil.crudapp.domain.Meal;

import java.util.List;

public class JdbcMealRepositoryImpl implements MealRepository{
    @Override
    public Meal get(int id, int userId) {
        return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return null;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        return null;
    }

    @Override
    public boolean delete(Meal meal, int userId) {
        return false;
    }
}
