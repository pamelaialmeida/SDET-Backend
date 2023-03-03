package br.com.nasaapi.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

/**
 * This BaseTest class contains all the base necessary methods to test the Nasa API - Mars Rover Photos.
 * @author Pamela Inacio Almeida
 */
public class BaseTest implements Constants{
	
	/**
	 * The setUp method sets the base url to Rest Assured. 
	 * It also builds the request specification with the content type to be used in all requests.
	 * 
	 * @author Pamela Inacio Almeida
	 */
	@BeforeClass
	public static void setUp() {
		RestAssured.baseURI = BASE_URL;
		
		RequestSpecBuilder requestBuilder = new RequestSpecBuilder();
		requestBuilder.setContentType(CONTENT_TYPE);
		RestAssured.requestSpecification = requestBuilder.build();		
		
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
	}
	
	/**
	 * The getMarsPhotos method sends a get request on Mars Photos. 
	 * 
	 * @author Pamela Inacio Almeida
	 * @param path - enpoint for the get request
	 * @param parameters - map of parameters to be sent on the request
	 * @return response with all Mars photos that correspond to the request
	 */
	public Response getMarsPhotos(String path, Map<String, String> parameters) {
		String requestParameters = buildParameters(parameters);
		
		return RestAssured.given()
					.urlEncodingEnabled(false)
					.param(requestParameters)
				.when()
					.get(path);
	}
	
	/**
	 * The buildParameters method builds a string with all the parameters to be sent on the request. 
	 * 
	 * @author Pamela Inacio Almeida
	 * @param parameters - map of parameters to be sent on the request
	 * @return string with the parameters to be sent on the request
	 */
	public String buildParameters(Map<String, String> parameters) {
		StringBuilder requestParameters = new StringBuilder();
		
		for (String parameter : parameters.keySet()) {
			requestParameters.append(parameter + "=" + parameters.get(parameter) + "&");
		}
		
		requestParameters.deleteCharAt(requestParameters.lastIndexOf("&"));
				
		return requestParameters.toString();
	}
	
	/**
	 * The getTotalPhotosFromManifest method returns the total photos taken by a rover camera on a specific sol and specific earth date.
	 *  
	 * @author Pamela Inacio Almeida
	 * @param path - enpoint for the get request
	 * @param parameters - map of parameters to be sent on the request
	 * @param sol - the sol on which the photos were taken
	 * @param eartDate - earth date on which the photo was taken
	 * @return the number of total photos taken by the rover camera on the specific sol and earth date
	 */
	public int getTotalPhotosFromManifest(String path, Map<String, String> parameters, int sol, String earthDate) {
		
		Response response = getMarsPhotos(path, parameters);
		
		JsonPath jsonpath = response.jsonPath();
		List<HashMap<String, Object>> photosManifest = jsonpath.getList("photo_manifest.photos");
		
		for (HashMap<String, Object> photo : photosManifest) {
			if(photo.get("sol").equals(sol) && photo.get("earth_date").equals(earthDate)) {
				return (int) photo.get("total_photos");
			}
		}		
		
		return 0;
	}

}