package services;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.*;

import java.net.MalformedURLException;
import java.net.URL;

import helpers.FakeDataProvider;

import models.Inscription;

import org.junit.Before;
import org.junit.Test;

import com.avaje.ebean.validation.AssertTrue;

public class MandrillAccessorTests {
	
	//private static final String MANDRILL_MESSAGE = "{\"key\":\"MYKEY\",\"message\":{\"subject\":\"[Agile Tour Montpellier 2012] Voici votre billet d'entrée... et votre badge !\",\"attachments\":[{\"type\":\"application/pdf\",\"name\":\"Badge ATMTP2012 - Fernand Durand\",\"content\":\"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bnNpRW5jb2RpbmcvU3VidHlwZS9UeXBlMT4+CmVuZG9iago2IDAgb2JqPDwvVHlwZS9QYWdlcy9Db3VudCAxL0tpZHNbMSAwIFJdPj4KZW5kb2JqCjcgMCBvYmo8PC9UeXBlL0NhdGFsb2cvUGFnZXMgNiAwIFI+PgplbmRvYmoKOCAwIG9iajw8L1Byb2R1Y2VyKGlUZXh0IDIuMC44IFwoYnkgbG93YWdpZS5jb21cKSkvTW9kRGF0ZShEOjIwMTIxMTE5MjIxNzM1KzAxJzAwJykvQ3JlYXRpb25EYXRlKEQ6MjAxMjExMTkyMjE3MzUrMDEnMDAnKT4+CmVuZG9iagp4cmVmCjAgOQowMDAwMDAwMDAwIDY1NTM1IGYgCjAwMDAwMDE1NTYgMDAwMDAgbiAKMDAwMDAwMTc0NiAwMDAwMCBuIAowMDAwMDAwMDE1IDAwMDAwIG4gCjAwMDAwMDAyOTkgMDAwMDAgbiAKMDAwMDAwMDk0MCAwMDAwMCBuIAowMDAwMDAxODM1IDAwMDAwIG4gCjAwMDAwMDE4ODUgMDAwMDAgbiAKMDAwMDAwMTkyOSAwMDAwMCBuIAp0cmFpbGVyCjw8L1Jvb3QgNyAwIFIvSUQgWzwzODc2MjNiNTUzOTc2NjUxZGQ5MGU5ZmY4MGVhNWRmNz48ODEyYjAwMjdhMGUyNzkzNGYzNjM5ZTdlNjkxODc0NDI+XS9JbmZvIDggMCBSL1NpemUgOT4+CnN0YXJ0eHJlZgoyMDYwCiUlRU9GCg==\"}],\"from_email\":\"at-montpellier-contact@googlegroups.com\",\"from_name\":\"Equipe Agile Tour Montpellier\",\"to\":[{\"email\":\"f.d@df.fr\",\"name\":\"Fernand Durand\"}],\"global_merge_vars\":[{\"name\":\"FNAME\",\"content\":\"Fernand\"},{\"name\":\"LNAME\",\"content\":\"Durand\"}]},\"template_name\":\"EventBrite Agile Tour Montpellier\",\"template_content\":[]}";
	
	private MandrillAccessor mandrill;
	private HttpRequestSender requestSender;
	private static final String URL_SERVICE = "http://localhost:9000/assets/mandrill.json";
	
	@Before
	public void Before() throws MalformedURLException {
		requestSender = mock(HttpRequestSender.class);
		mandrill = spy(new MandrillAccessor(requestSender, URL_SERVICE, "MYKEY"));
	}	

	@Test
	public void shouldSendMessageWithBadgeToMandrill() {		
		Inscription inscription = givenIHaveAnInscription();
		String badge = andIHaveABadge();
		
		String response = whenISendTheBadgeViaMandrill(inscription, badge);
		
		thenTheMessageIsSentWithNoError(response);
	}

	protected String andIHaveABadge() {
		return "TEST";
	}

	private Inscription givenIHaveAnInscription() {
		when(requestSender.sendPostRequest(any(URL.class), anyString())).thenReturn("OK");
		
		return FakeDataProvider.getAnExistingInscription();
	}

	private String whenISendTheBadgeViaMandrill(Inscription inscription, String badge) {
		return mandrill.send(inscription, badge);
	}

	private void thenTheMessageIsSentWithNoError(String response) {
		assertFalse(response.equals(""));
		verify(requestSender).sendPostRequest(any(URL.class), anyString());
	}

}
