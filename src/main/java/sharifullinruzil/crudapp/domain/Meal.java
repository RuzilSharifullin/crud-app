package sharifullinruzil.crudapp.domain;

import java.time.LocalDateTime;

public class Meal {
    Integer id;
    LocalDateTime localDateTime;
    String description;
    Integer calories;

    public Meal() {
    }

    public Meal(Integer id, LocalDateTime localDateTime, String description, Integer calories) {
        this.id = id;
        this.localDateTime = localDateTime;
        this.description = description;
        this.calories = calories;
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public String getDescription() {
        return description;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }
}
