package controllers;

import javax.inject.Inject;

import models.Inscription;
import play.Play;
import play.Configuration;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import services.Repository;
import views.html.index;
import views.html.login;

public class Application extends Controller {
	
	final static Form<Inscription> inscriptionForm = form(Inscription.class);
	@Inject static Repository repository;
	
	@Transactional
	public static Result index() {
		session().put("fake", "fake");
		return ok(index.render(inscriptionForm, "", session().get("connectedUser")));
  	}

	@Transactional
	public static Result submit() {
		Form<Inscription> filledForm = inscriptionForm.bindFromRequest();
		
		if(filledForm.hasErrors()) {
            return badRequest(index.render(filledForm, "inscription non valide : " + filledForm.errorsAsJson().toString(), session().get("connectedUser")));
        } else {
        	Inscription inscription = filledForm.get();
			repository.save(inscription);
        	return ok(index.render(filledForm, "inscription validée", session().get("connectedUser")));
        }
	}
	
	public static Result login() {
		return ok(login.render(form(Login.class), ""));
	}
	
	public static Result authenticate() {
		Form<Login> loginForm = form(Login.class).bindFromRequest();
		
		if(loginForm.hasErrors()) {
            return badRequest(login.render(loginForm, loginForm.errorsAsJson().toString()));
        } else {
        	session("connectedUser", "Admin");
			return redirect(routes.Administration.admin());
        }
	}
	
    public static Result logout() {
        session().clear();
        return redirect(routes.Application.index());
    }
	
	public static class Login {
        
        public String password;
        
        public String validate() {
        	Configuration configuration = Play.application().configuration();
            if(!password.equals(configuration.getString("admin.password"))) {
                return "Invalid user or password : " + configuration.getString("admin.password") + " - " + password;
            }
            return null;
        }
        
    }
  
}