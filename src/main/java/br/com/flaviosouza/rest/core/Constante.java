package br.com.flaviosouza.rest.core;

import io.restassured.http.ContentType;

public interface Constante {	
	String APP_BASE_URL = "http://localhost:8080/api/v1";
	String APP_BASE_PATH = "";
	
	ContentType APP_CONTENT_TYPE = ContentType.JSON;
	
	Long MAX_TIMEOUT = 5000L;
}
