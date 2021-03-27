package sharifullinruzil.crudapp.dto;

import java.time.LocalDateTime;

public class MealDto {
    Integer id;
    LocalDateTime dateTime;
    String description;
    Integer calories;
    boolean excess;

    public MealDto(Integer id, LocalDateTime dateTime, String description, Integer calories, boolean excess) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public Integer getCalories() {
        return calories;
    }

    public boolean isExcess() {
        return excess;
    }
}
