package ru.javawebinar.topjava.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * GKislin
 * 11.01.2015.
 */
@NamedQueries({
        @NamedQuery(name = Meal.DELETE, query = "delete from Meal m where m.id=:id and m.user.id=?1"),
        @NamedQuery(name = Meal.GET, query = "select m from Meal m where m.id=?1 and m.user.id=?2"),
        @NamedQuery(name = Meal.ALL_SORTED, query = "select m from Meal m where m.user.id=?1 order by m.dateTime desc"),
        @NamedQuery(name = Meal.BETWEEN, query = "select m from Meal m where m.user.id=?3 and m.dateTime between ?1 and ?2 order by m.dateTime desc")
})
@Entity
@Table(name = "meals")
public class Meal extends BaseEntity {
    public static final String DELETE = "Meal.delete";
    public static final String ALL_SORTED = "Meal.getAllSorted";
    public static final String GET = "Meal.get";
    public static final String BETWEEN = "Meal.getBetween";
    public static final String UPDATE = "Meal.update";


    @Column(name = "date_time", nullable = false, columnDefinition = "timestamp default now()")
    private LocalDateTime dateTime;

    @Column(name = "description", nullable = false)
    @NotEmpty
    @Length(min = 4)
    private String description;

    @Column(name = "calories", nullable = false, columnDefinition = "2000")
    @Digits(fraction = 0, integer = 4)
    private int calories;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
