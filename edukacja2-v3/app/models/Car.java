package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Car extends Model {

    @Id
    public Long id;
    public String model;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    public User user;


    private static Finder<Long, Car> find = new Finder<>(Car.class);

    public static List<Car> findAll() {
        return find.all();
    }

    public static Car findCar(Long id) {
        return find.where().eq("id", id).findUnique();
    }

    public Car(String model) {
        this.model = model;
    }
}
