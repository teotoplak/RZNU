package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Car;
import models.User;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.List;

/**
 * Created by teo on 12/4/17.
 */
@Security.Authenticated(Secured.class)
public class Cars extends Controller {

    public Result createCar(Long userId) {
        User user = User.findUser(userId);
        DynamicForm form = Form.form().bindFromRequest();
        String model = form.get("model");
        Car car = new Car(model);
        car.user = user;
        car.save();
        return redirect(routes.Cars.readCar(userId,car.id));
    }

    public Result readCars(Long userId) {
        User user = User.findUser(userId);
        return ok(views.html.getCars.render(user.cars,user));
    }

    public Result readCar(Long userId, Long carId) {
        Car car = Car.findCar(carId);
        return ok(views.html.getCar.render(car));
    }

    public Result deleteCar(Long userId, Long carId) {
        Car car = Car.findCar(carId);
        car.delete();
        return redirect(routes.Cars.readCars(userId));
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

    public Result carForm(Long userId) {
        return ok(views.html.carForm.render(userId));
    }

}
