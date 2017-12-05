package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
public class User extends Model {

    @Id
    public Long id;
    public String username;
    public String password;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    public List<Car> cars = new LinkedList<>();

    private static Finder<Long, User> find = new Finder<>(User.class);

    public static List<User> findAll() {
        return find.all();
    }

    public static User findUser(Long id) {
        return find.where().eq("id", id).findUnique();
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
