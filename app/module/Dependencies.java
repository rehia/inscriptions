package module;

import com.google.inject.Provides;
import javax.inject.Singleton;
import play.Play;
import com.typesafe.plugin.inject.InjectPlugin;

import services.Repository;

public class Dependencies {
	
	public static InjectPlugin inject() {
	    return Play.application().plugin(InjectPlugin.class);
	  }

	  //this is needed for each controller
	  public static controllers.Application application() {
	    return inject().getInstance(controllers.Application.class);
	  }

	  @Provides
	  @Singleton
	  public Repository makeRepository() {
	    return new Repository();
	  }
}
