package helpers;

import junit.framework.Assert;

import org.junit.Test;


public class StringFormatterTests {
	
	@Test
	public void shouldFormatSimpleLowerCaseStringAsPascal() {
		testFormatter("jerome", "Jerome");
	}
	
	@Test
	public void shouldFormatAnotherSimpleLowerCaseStringAsPascal() {
		testFormatter("claire", "Claire");
	}

	protected void testFormatter(String name, String expectedString) {
		StringFormatter formatter = new StringFormatter();
		
		String formattedString = formatter.format(name);
		
		Assert.assertEquals(expectedString, formattedString);
	}
	
	@Test
	public void shouldFormatASimpleUpperCaseStringAsPascal() {
		testFormatter("JEROME", "Jerome");
	}
	
	@Test
	public void shouldFormatAComplexStringWithSpaceAsPascal() {
		testFormatter("jean philippe", "Jean Philippe");
	}
	
	@Test
	public void shouldFormatAComplexStringWithDashAsPascal() {
		testFormatter("jean-philippe", "Jean Philippe");
	}
	
	@Test
	public void shouldTrimString() {
		testFormatter(" jerome ", "Jerome");
	}

}
