package sharifullinruzil.crudapp.domain;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Meal {
    Integer id;
    @NotNull
    LocalDateTime dateTime;
    @NotBlank
    String description;
    @NotNull
    @Range(min = 10, max = 3000)
    Integer calories;

    public Meal() {
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, Integer calories) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public LocalDate getDate() {
        return this.dateTime.toLocalDate();
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

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public boolean isNew() {
        return this.id == null;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
