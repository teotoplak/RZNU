package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Car;
import models.User;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

/**
 * Created by teo on 12/4/17.
 */
public class Cars extends Controller {

    public Result list() {
        List<User> users = User.findAll();
        return ok(views.html.users.render(users));
    }

    public Result createCar(Long userId) {
        User user = User.findUser(userId);
        JsonNode json = request().body().asJson();
        Car car = Json.fromJson(json,Car.class);
        car.user = user;
        car.save();
        System.out.println(user.cars.size());
        return ok(Json.toJson(car));
    }

    public Result readCars(Long userId) {
        User user = User.findUser(userId);
        return ok(Json.toJson(user.cars));
    }

    public Result readCar(Long userId, Long carId) {
        Car car = Car.findCar(carId);
        return ok(Json.toJson(car));
    }

    public Result deleteCar(Long id) {
        User user = User.findUser(id);
        user.delete();
        return ok();
    }

    public Result updateCar(Long id) {
        User user = User.findUser(id);
        JsonNode json = request().body().asJson();
        String username = json.findPath("username").textValue();
        String password = json.findPath("password").textValue();
        if(user == null) {
            user = Json.fromJson(json,User.class);
            user.id = id;
            user.save();
        } else {
            user.username = username;
            user.password = password;
            user.update();
        }
        return ok(Json.toJson(user));
    }

}
