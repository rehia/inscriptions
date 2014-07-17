package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HttpRequestSender {
	
	public String sendPostRequest(URL url, String data) {
        
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            
            //write parameters
            writer.write(data);
            writer.flush();
            
            
            // Get the response
            StringBuffer answer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                answer.append(line);
            }
            writer.close();
            reader.close();            
            
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
    			throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode() + " : " + answer.toString());
    		}
            
            connection.disconnect();
            
            return answer.toString();
            
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

	public String sendGetRequest(URL url) throws IOException {
		StringBuilder content = new StringBuilder();
		BufferedReader reader = null;
		try {
			InputStream stream = url.openStream();
			reader = new BufferedReader(new InputStreamReader(stream));
	
	        String inputLine;
	        while ((inputLine = reader.readLine()) != null)
	            content.append(inputLine);
	        
		} finally {
			if (reader != null)
				reader.close();
		}
		
		return content.toString();
	}

}
