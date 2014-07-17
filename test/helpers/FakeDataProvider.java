package helpers;

import java.util.ArrayList;
import java.util.List;

import models.Inscription;
import models.ModelFactory;

public class FakeDataProvider {
	
	private static ModelFactory createModelFactory() {
		return new ModelFactory("15723826", "16008859", "16057183", "16057319", "15929463", "17826293");
	}

	public static Inscription getAnExistingInscription() {
		return createModelFactory().createInscription("Durand", "Fernand", "f.d@df.fr", "119309147152822123456", Inscription.INSCRIT);
	}

	public static List<Inscription> getANewInscription() {
		List<Inscription> inscriptions = new ArrayList<Inscription>();
		inscriptions.add(createModelFactory().createInscription("Hazard", "Thierry", "thierry.hazard@lejerk.com", "119309147152822000000", Inscription.INSCRIT));
		return inscriptions;
	}

	public static List<Inscription> getSomeNewInscriptions() {
		List<Inscription> inscriptions = getANewInscription();
		inscriptions.add(createModelFactory().createInscription("Montagné", "Gilbert", "gilbert@montagne.com", "119309147152822111111", Inscription.INSCRIT));
		return inscriptions;
	}

	public static List<Inscription> getSomeExistingInscriptions() {
		List<Inscription> inscriptions = new ArrayList<Inscription>();
		inscriptions.add(getAnExistingInscription());
		inscriptions.add(createModelFactory().createInscription("Dupont", "Bernard", "u_I@uj.fr", "119309147152822654321", Inscription.INSCRIT));
		inscriptions.addAll(getOrganizers());

		return inscriptions;
	}

	public static List<Inscription> getOrganizers() {
		List<Inscription> inscriptions = new ArrayList<Inscription>();
		inscriptions.add(createModelFactory().createInscription("Goldman", "Jean Jacques", "zokd@iy.fr", "119309147152822987654", Inscription.ORGANISATEUR));
		
		return inscriptions;
	}

	public static Inscription getAnExistingInscriptionWithBadgeSent() {
		Inscription inscription = createModelFactory().createInscription("Martin", "Raymond", "ray@mart.fr", "119309147152123987654", Inscription.INSCRIT);
		inscription.setBadgeAsSent();
		return inscription;
	}
	
	public static String getBase64PDF() {
		return "JVBERi0xLjQKJeLjz9MKMyAwIG9iaiA8PC9UeXBlL1hPYmplY3QvQ29sb3JTcGFjZS9EZXZpY2VH" +
				"cmF5L1N1YnR5cGUvSW1hZ2UvQml0c1BlckNvbXBvbmVudCA4L1dpZHRoIDE2L0xlbmd0aCAxMzAv" +
				"SGVpZ2h0IDE2L0ZpbHRlci9GbGF0ZURlY29kZT4+c3RyZWFtCnicY2BgsL0Yz4AMmv7/n8qNxF/5" +
				"////k7pwLtMZIP//O7gekVf/wQCmx/AvhP//hB6YH/4fBt5HgvgNcP7/3/pA/goE/6A2AwP7GRjv" +
				"YYMMUFr+NYT3ZakF2DjHf2DusShOiHWZEKWyMOdM/P//M1QpGCxBKAUD63I5ZM8CAH6CahQKZW5k" +
				"c3RyZWFtCmVuZG9iago0IDAgb2JqIDw8L1R5cGUvWE9iamVjdC9Db2xvclNwYWNlL0RldmljZVJH" +
				"Qi9TTWFzayAzIDAgUi9TdWJ0eXBlL0ltYWdlL0JpdHNQZXJDb21wb25lbnQgOC9XaWR0aCAxNi9M" +
				"ZW5ndGggNDc2L0hlaWdodCAxNi9GaWx0ZXIvRmxhdGVEZWNvZGU+PnN0cmVhbQp4nPv//793npR3" +
				"nrR3vlRIrWTNrMCZa2tnrq+bub5hxrrG6euap65p7ltev+Xomv8w4F+s4FcoH1qrVLpWJ3eJSskM" +
				"l4nLS+dsaMGlPrBUPaBYPaZNq3KLSc0Os8JV2rkzrdoXZs3Z2D5jfQum+oASbb8i7YQ+w8qtNkBU" +
				"vd22dKNZ9lyL6lkxM9e1T1/bhuEeA79ig5RpVpXbXCu2uABR5VYg6ZSzxCZ/qt+klQ2TVjYjq/cp" +
				"NPYpMs6c51Kxza9ssy8ElW/xLd/qW7DGLW2KW9Pcwp3HN8LV+xVbBVba5C7zL9saVro5FBmVbQkr" +
				"3hgUN8Vx5qae339+Q9R7F9oF1bjkr44u3RJfsikODZVujs9dH1a4PP79l3cQ9V75LqHNfoXr04o3" +
				"ZxRtQkEFG9Oy1sY37aq48OzMv///YOb7RHVHFW4sREYFGwsy12YVbSracHn9l59f/iMBtzyf2Mmp" +
				"BZuq8jdUQlD2utKMtSVTj855+P7xfwzgWRSaNLsof1Nz3oamnPUNqaur6ndOOPnwAqZKqHvKY1MX" +
				"1eVu7EpdDdTSte7Sni8/v+FSDIrfhqykpW1pa3omH1nz8N0LPCohIKituHrLvJOPbhBUCQFP3rz8" +
				"9usnkYoBRjoPfQplbmRzdHJlYW0KZW5kb2JqCjUgMCBvYmogPDwvTGVuZ3RoIDU0OS9GaWx0ZXIv" +
				"RmxhdGVEZWNvZGU+PnN0cmVhbQp4nI1VTY/TMBS8+1c8bsDhbfydcESAAIlDpUgIIQ5lm227ittt" +
				"6arw73H80dhWElAr5T15Zt5kbDkn8rYlFeiGQbsh71uyIidSYcW1hCth8NkuPhJawRfy/UcFG8IV" +
				"aKnAEKl91buKK6yErXlW+vUd+UoOVmf4nbdEMFCNREatiFKx7omyT2lbgWJgCYY6aXfk4XVGLdZD" +
				"KzXSxrW2VihEpFaomtr6Kp/WUBjsqWZJabCbqY7+Tek/U52gCuHhDBsXJ09amylD37loRFJPkUWB" +
				"1Ro5H2oP0wplfQsi7oMf4aFmiRasZSqZXVPYTZWn6Qu7IYXf16CseNLaGHkyRmKZS0HOEb672fLQ" +
				"6WyGMTGZRVqwl6lklk1medSdpi7kEjdFYlWP58W3t8hF2MCkTs9LJIsCKxt/MANM1nbszHnxULNE" +
				"C9YylcyuKeymytP0f5+XqBxTDbm4wOMYiWUuBTlH+O5my0OnsxnGxGQWacFeppJZNpnlUXeaai/v" +
				"uw8UqL29Hwh1bijYG0vzGlpDXn4y6233Bl61j8PdfhqADmMRNXLhcPcG7vZmS+HdEVbzikygok70" +
				"Y9f3R7gez/3mRZSepVGKjDnat+PzGQ7dFdZPT/3+fn3ZHw+w/wXnbr35g1HIM22kc4qq0VgLp9ju" +
				"LN3+f/bPHVy635dEo5rXkC7HukLtVOjSK9grm6Vgtvi+XlkxrH1S/D+kR7QI6OETvCJ/ASknjIgK" +
				"ZW5kc3RyZWFtCmVuZG9iagoxIDAgb2JqPDwvUGFyZW50IDYgMCBSL0NvbnRlbnRzIDUgMCBSL1R5" +
				"cGUvUGFnZS9SZXNvdXJjZXM8PC9YT2JqZWN0PDwvaW1nMSA0IDAgUi9pbWcwIDMgMCBSPj4vUHJv" +
				"Y1NldCBbL1BERiAvVGV4dCAvSW1hZ2VCIC9JbWFnZUMgL0ltYWdlSV0vRm9udDw8L0YxIDIgMCBS" +
				"Pj4+Pi9NZWRpYUJveFswIDAgNjEyIDc5Ml0+PgplbmRvYmoKMiAwIG9iajw8L0Jhc2VGb250L1Rp" +
				"bWVzLVJvbWFuL1R5cGUvRm9udC9FbmNvZGluZy9XaW5BbnNpRW5jb2RpbmcvU3VidHlwZS9UeXBl" +
				"MT4+CmVuZG9iago2IDAgb2JqPDwvVHlwZS9QYWdlcy9Db3VudCAxL0tpZHNbMSAwIFJdPj4KZW5k" +
				"b2JqCjcgMCBvYmo8PC9UeXBlL0NhdGFsb2cvUGFnZXMgNiAwIFI+PgplbmRvYmoKOCAwIG9iajw8" +
				"L1Byb2R1Y2VyKGlUZXh0IDIuMC44IFwoYnkgbG93YWdpZS5jb21cKSkvTW9kRGF0ZShEOjIwMTIx" +
				"MTE5MjIxNzM1KzAxJzAwJykvQ3JlYXRpb25EYXRlKEQ6MjAxMjExMTkyMjE3MzUrMDEnMDAnKT4+" +
				"CmVuZG9iagp4cmVmCjAgOQowMDAwMDAwMDAwIDY1NTM1IGYgCjAwMDAwMDE1NTYgMDAwMDAgbiAK" +
				"MDAwMDAwMTc0NiAwMDAwMCBuIAowMDAwMDAwMDE1IDAwMDAwIG4gCjAwMDAwMDAyOTkgMDAwMDAg" +
				"biAKMDAwMDAwMDk0MCAwMDAwMCBuIAowMDAwMDAxODM1IDAwMDAwIG4gCjAwMDAwMDE4ODUgMDAw" +
				"MDAgbiAKMDAwMDAwMTkyOSAwMDAwMCBuIAp0cmFpbGVyCjw8L1Jvb3QgNyAwIFIvSUQgWzwzODc2" +
				"MjNiNTUzOTc2NjUxZGQ5MGU5ZmY4MGVhNWRmNz48ODEyYjAwMjdhMGUyNzkzNGYzNjM5ZTdlNjkx" +
				"ODc0NDI+XS9JbmZvIDggMCBSL1NpemUgOT4+CnN0YXJ0eHJlZgoyMDYwCiUlRU9GCg==";
	}

}
