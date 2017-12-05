package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Car;
import models.User;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by teo on 12/4/17.
 */
public class Users extends Controller {

    public Result createUser() {
        DynamicForm form = Form.form().bindFromRequest();
        String username = form.get("username");
        String password = form.get("password");
        User user = new User(username,password);
        user.cars = new LinkedList<>();
        user.save();
        return redirect(routes.Users.readUser(user.id));
    }

    public Result readUsers() {
        return ok(views.html.getUsers.render(User.findAll()));
    }

    public Result readUser(Long id) {
        User user = User.findUser(id);
        return ok(views.html.getUser.render(user));
    }

    public Result deleteUser(Long id) {
        User user = User.findUser(id);
        for(Car car : user.cars) {
            car.delete();
        }
        user.delete();
        return ok();
    }

    public Result updateUser(Long id) {
        User user = User.findUser(id);
        JsonNode json = request().body().asJson();
        String username = json.findPath("username").textValue();
        String password = json.findPath("password").textValue();
        if(user == null) {
            user = Json.fromJson(json,User.class);
            user.id = id;
            user.cars = new LinkedList<>();
            user.save();
        } else {
            user.username = username;
            user.password = password;
            user.update();
        }
        return redirect(routes.Users.readUser(user.id));
    }

    public Result userForm() {
        return ok(views.html.userForm.render());
    }
}
