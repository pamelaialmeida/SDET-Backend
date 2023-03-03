package br.com.nasaapi.common;

import io.restassured.http.ContentType;

/**
 * This interface maps all the constants that will be used during the tests of the Nasa API - Mars Rover Photos.
 * @author Pamela Inacio Almeida
 */
public interface Constants {

	String BASE_URL = "https://api.nasa.gov";
	
	String BASE_PATH_MARS_CURIOSITY = "/mars-photos/api/v1/rovers/curiosity/photos";
	
	String BASE_PATH_MANIFEST_OPPORTUNITY = "/mars-photos/api/v1/manifests/Opportunity";
	String BASE_PATH_MANIFEST_SPIRIT = "/mars-photos/api/v1/manifests/Spirit";
	
	ContentType CONTENT_TYPE = ContentType.JSON;
	
	String API_KEY = "DEMO_KEY";

}
