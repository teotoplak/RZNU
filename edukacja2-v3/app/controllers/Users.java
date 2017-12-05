package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.User;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by teo on 12/4/17.
 */
public class Users extends Controller {

    public Result list() {
        List<User> users = User.findAll();
        return ok(views.html.users.render(users));
    }

    public Result createUser() {
        JsonNode json = request().body().asJson();
        User user = Json.fromJson(json,User.class);
        user.cars = new LinkedList<>();
        user.save();
        return ok(Json.toJson(user));
    }

    public Result readUsers() {
        return ok(Json.toJson(User.findAll()));
    }

    public Result readUser(Long id) {
        User user = User.findUser(id);
        return ok(Json.toJson(user));
    }

    public Result deleteUser(Long id) {
        User user = User.findUser(id);
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
        return ok(Json.toJson(user));
    }

}
