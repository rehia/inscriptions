package helpers;

public class StringFormatter {

	public String format(String stringToFormat) {
		String stringFormatted = "";
		for (String stringPart : getStringParts(stringToFormat.trim())) {
			stringFormatted += formatPart(stringPart) + " ";
		}
		
		return stringFormatted.trim();
	}

	protected String formatPart(String stringPart) {
		return getFirstLetterUpperCased(stringPart) + getResidueLowerCased(stringPart);
	}

	protected String getResidueLowerCased(String stringPart) {
		return stringPart.substring(1).toLowerCase();
	}

	protected String getFirstLetterUpperCased(String stringPart) {
		return stringPart.substring(0,1).toUpperCase();
	}

	protected String[] getStringParts(String stringToFormat) {
		return stringToFormat.split("( |-)");
	}

}
