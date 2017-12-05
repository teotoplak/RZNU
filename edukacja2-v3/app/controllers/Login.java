package controllers;

import controllers.*;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by teo on 12/5/17.
 */
public class Login extends Controller {

    public Result login() {
        return ok(views.html.login.render());
    }

    public Result loginSubmit() {
        DynamicForm form = Form.form().bindFromRequest();
        String username = form.get("username");
        String password = form.get("password");
        if (username.equals("admin") && password.equals("admin")) {
            session().clear();
            session("username", "admin");
            return redirect(controllers.routes.Users.readUsers());
        } else {
           return  redirect(controllers.routes.Login.login());
        }
    }

    public Result logout() {
        session().clear();
        return redirect(controllers.routes.Login.login());
    }

}
