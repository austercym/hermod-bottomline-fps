package com.example.test.kafka;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

public class TestHelloService {

	private final static String USER_AGENT = "Mozilla/5.0";
	
	@Test
	public void testHelloCalled() {
		
		String url = "http://localhost:8080/springtest/sayHello/Borja";

		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	
			// optional default is GET
			con.setRequestMethod("GET");
	
			//add request header
			con.setRequestProperty("User-Agent", USER_AGENT);
			
			// get response code and validate
			int code = con.getResponseCode();
			assert(code == 201);
			
			// get response text and validate
			InputStream stream = con.getInputStream();
			int i = -1;
			StringBuffer result = new StringBuffer();
			while((i = stream.read()) !=-1) {
				result.append((char) i);
			}
			assert(result.toString().contains("Hello Borja"));
			
		} catch (Exception e) {
			assert(false);
		}
	}
}
