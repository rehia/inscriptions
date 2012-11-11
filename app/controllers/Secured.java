package controllers;

import play.mvc.Result;
import play.mvc.Security;
import play.mvc.Http.Context;
import static play.mvc.Controller.*;

public class Secured extends Security.Authenticator {
	    
	    @Override
	    public String getUsername(Context ctx) {
	    	return ctx.session().get("connectedUser");
	    }
	    
	    @Override
	    public Result onUnauthorized(Context ctx) {
	        //flash("not logged in", "You're not logged in !");
	        return redirect(routes.Application.index());
	    }
}
