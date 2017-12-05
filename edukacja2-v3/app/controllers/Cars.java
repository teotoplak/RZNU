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

    public Result deleteCar(Long userId, Long carId) {
        Car car = Car.findCar(carId);
        car.delete();
        return ok();
    }

    public Result updateCar(Long userId, Long carId) {
        Car car = Car.findCar(carId);
        User user = User.findUser(userId);
        JsonNode json = request().body().asJson();
        String model = json.findPath("model").textValue();
        if(car == null) {
            car = Json.fromJson(json,Car.class);
            car.id = carId;
            car.user = user;
            car.save();
        } else {
            car.model = model;
            car.update();
        }
        return ok(Json.toJson(user));
    }

}
