package helpers;
import java.util.HashMap;
import java.util.Map;

import play.test.FakeApplication;


public class PlayTestHelpers {
	
	public static FakeApplication fakeApplicationOverloaded() {
		
		Map<String, String> parameters = new HashMap<String, String>();
		return fakeApplicationOverloaded(parameters);
	}

	public static FakeApplication fakeApplicationOverloaded(
			Map<String, String> parameters) {
		parameters.put("inject.modules", "module.DependenciesTest");
		
		return play.test.Helpers.fakeApplication(parameters);
	}

}
