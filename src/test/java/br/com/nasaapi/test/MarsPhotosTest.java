package br.com.nasaapi.test;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Assert;
import org.junit.Test;

import br.com.nasaapi.common.BaseTest;
import br.com.nasaapi.models.Photo;
import io.restassured.response.Response;


/**
 * This MarsPhotosTest class contains all the tests for Nasa API - Mars Rover Photos.
 * @author Pamela Inacio Almeida
 */
public class MarsPhotosTest extends BaseTest{
	
	/**
	 * The shouldRetrieveTheFirstTenPhotosMadeByCuriosityOn1000MartianSol test method sends a get request on mars-photos curiosity rover endpoint 
	 * sending the sol = 1000 and the requested api_key parameters, retrieves the first ten photos of the response and asserts that the ten photos has the expected data.
	 * 
	 * @author Pamela Inacio Almeida
	 */
	@Test
	public void shouldRetrieveTheFirstTenPhotosMadeByCuriosityOn1000MartianSol() {
		Map<String, String> parameters = new TreeMap<String, String>();
		parameters.put("sol", "1000");
		parameters.put("api_key", API_KEY);
		
		List<Photo> photos = getMarsPhotos(BASE_PATH_MARS_CURIOSITY, parameters).then().statusCode(200).extract().jsonPath().getList("photos", Photo.class);
		
		List<Photo> firstTenPhotos = photos.subList(0, 10);
		for (Photo photo : firstTenPhotos) {
			Assert.assertNotNull(photo.getId());
			Assert.assertNotNull(photo.getImage());
		}
		
	}
	
	/**
	 * The shouldRetrieveTheFirstTenPhotosMadeByCuriosityOnEarthDateEqualTo1000MartianSol test method sends a get request on mars-photos curiosity rover endpoint 
	 * sending the sol = 1000 and the requested api_key parameters to retrieves the earth_date when the pictures were taken on Martian sol 1000.
	 * Then it sends a new get request on mars-photos curiosity rover endpoint sending the sol = 1000, the retrieved earth_date, and the requested api_key parameters,
	 * retrieves the first ten photos of the response and asserts that the ten photos has the expected data.
	 * 
	 * @author Pamela Inacio Almeida
	 */
	@Test
	public void shouldRetrieveTheFirstTenPhotosMadeByCuriosityOnEarthDateEqualTo1000MartianSol() {
		Map<String, String> parameters = new TreeMap<String, String>();
		parameters.put("sol", "1000");
		parameters.put("api_key", API_KEY);
		
		String earthDate = getMarsPhotos(BASE_PATH_MARS_CURIOSITY, parameters).then().statusCode(200).extract().path("photos[0].earth_date");
		
		parameters.remove("sol", "1000");
		parameters.put("earth_date", earthDate);
		
		List<Photo> photosOnEarthDate = getMarsPhotos(BASE_PATH_MARS_CURIOSITY, parameters).then().statusCode(200).extract().jsonPath().getList("photos", Photo.class);
		
		List<Photo> firsTenPhotosOnEarth = photosOnEarthDate.subList(0, 10);
		for (Photo photo : firsTenPhotosOnEarth) {
			Assert.assertNotNull(photo.getId());
			Assert.assertNotNull(photo.getImage());
		}
		
	}
	
	/**
	 * The shouldRetrieveAndCompareTheFirst10MarsPhotosMadeByCuriosityOn1000SolAndOnEarthDateEqualTo1000MartianSol test method sends a get request on mars-photos curiosity rover endpoint 
	 * sending the sol = 1000 and the requested api_key parameters, retrieves the first ten photos of the response and the earth_date when the pictures were taken on Martian sol 1000.
	 * Then it sends a new get request on mars-photos curiosity rover endpoint sending the sol = 1000, the retrieved earth_date, and the requested api_key parameters, and retrieves the first ten photos of the response.
	 * Finally, it compares the 10 first photos made by curiosity camera on 1000 sol and the 10 first photos made by the same camera on Earth date equal to 1000 Martian sol and asserts that the photos are the same.
	 * 
	 * @author Pamela Inacio Almeida
	 */
	@Test
	public void shouldRetrieveAndCompareTheFirst10MarsPhotosMadeByCuriosityOn1000SolAndOnEarthDateEqualTo1000MartianSol() {
		Map<String, String> parameters = new TreeMap<String, String>();
		parameters.put("sol", "1000");
		parameters.put("api_key", API_KEY);
		
		Response marsPhotos = getMarsPhotos(BASE_PATH_MARS_CURIOSITY, parameters);
		
		List<Photo> martianSolPhotos = marsPhotos.then().statusCode(200).extract().jsonPath().getList("photos", Photo.class);
		
		List<Photo> firstTenMartianSolPhotos = martianSolPhotos.subList(0, 10);
		
		String earthDate = marsPhotos.then().extract().path("photos[0].earth_date");

		parameters.remove("sol", "1000");
		parameters.put("earth_date", earthDate);
		
		List<Photo> photosOnEarthDate = getMarsPhotos(BASE_PATH_MARS_CURIOSITY, parameters).then().statusCode(200).extract().jsonPath().getList("photos", Photo.class);
		
		List<Photo> firsTenPhotosOnEarth = photosOnEarthDate.subList(0, 10);
		
		for(int i = 0; i < 10; i++) {
			Assert.assertEquals(firstTenMartianSolPhotos.get(i).getId(), firsTenPhotosOnEarth.get(i).getId());
			Assert.assertEquals(firstTenMartianSolPhotos.get(i).getImage(), firstTenMartianSolPhotos.get(i).getImage());
		}
		
	}
	
	/**
	 * The shouldValidateThatTheAmountOfPicturesTakenByEachCuriosityCameraIsNotGreaterThan10TimesTheAmountOfPicturesTakenByOtherCamerasOnTheSameDate test method sends a get request on mars-photos curiosity rover endpoint 
	 * sending the sol = 1000 and the requested api_key parameters to retrieves the earth_date when the pictures were taken on Martian sol 1000.
	 * Then, it sends a request on mars-photos curiosity rover endpoint to get the amount of pictures taken by each Curiosity camera and map those quantities to a map.
	 * After that, it retrieves the amount of pictures taken by Opportunity rover cameras and Spirit rover cameras.
	 * Finally, it validates the amount of pictures taken by each curiosity camera against the amount of pictures taken by all other cameras on the same date on Martian sol 1000.
	 * 
	 * @author Pamela Inacio Almeida
	 */
	@Test
	public void shouldValidateThatTheAmountOfPicturesTakenByEachCuriosityCameraIsNotGreaterThan10TimesTheAmountOfPicturesTakenByOtherCamerasOnTheSameDate() {
		Map<String, String> parameters = new TreeMap<String, String>();
		parameters.put("sol", "1000");
		parameters.put("api_key", API_KEY);
		
		String earthDate = getMarsPhotos(BASE_PATH_MARS_CURIOSITY, parameters).then().statusCode(200).extract().path("photos[0].earth_date");
		
		parameters.remove("sol", "1000");
		parameters.put("earth_date", earthDate);
		
		String[] curiosityCameras = {"FHAZ", "RHAZ", "CHEMCAM", "MAHLI", "MARDI", "NAVCAM", "MAST"};
		
		Map<String, Integer> amountPhotosCuriosityCamerasMap = new TreeMap<String, Integer>();
		
		int totalPhotosCuriosityCameras = 0;
		
		for(int i=0; i < curiosityCameras.length; i++) {
			parameters.put("camera", curiosityCameras[i]);
			int amountPhotosByCamera = getMarsPhotos(BASE_PATH_MARS_CURIOSITY, parameters).then().statusCode(200).extract().jsonPath().getList("photos", Photo.class).size();
			amountPhotosCuriosityCamerasMap.put(curiosityCameras[i], amountPhotosByCamera);
			totalPhotosCuriosityCameras = totalPhotosCuriosityCameras + amountPhotosByCamera;
		}
		
		int amountPhotosOpportunity = getTotalPhotosFromManifest(BASE_PATH_MANIFEST_OPPORTUNITY, parameters, 1000, earthDate);
		
		int amountPhotosSpirit = getTotalPhotosFromManifest(BASE_PATH_MANIFEST_SPIRIT, parameters, 1000, earthDate);
		
		int totalAmountPhotosOnEarthDate = totalPhotosCuriosityCameras + amountPhotosOpportunity + amountPhotosSpirit;
		
		//IMPORTANT NOTE: The test asks to validate that the amounts of pictures that each "Curiosity" camera took on 1000 Mars sol is not greater than 10 times the amount taken by other cameras on the same date.
		//However, the MAST Curiosity camera has taken an amount of picture that is greater than 10 times the amount of pictures taken by other cameras. For that reason, the test is falling when executing the last assert.
		//If there was a business rule that no camera should be allowed to take an amount of picture greater than 10 times the amount of pictures taken by other cameras, then we would have a defect here.
		for (Map.Entry<String, Integer> curiosityCamera : amountPhotosCuriosityCamerasMap.entrySet()) {
			int amountPhotosOtherCameras = totalAmountPhotosOnEarthDate - curiosityCamera.getValue();
			Assert.assertTrue("The amount of pictures took by camera " + curiosityCamera.getKey() + " (" + curiosityCamera.getValue() + " photos) is 10 times greater than the amount of photos taken by other cameras (" + amountPhotosOtherCameras + " photos) on the same date (" + earthDate + ").", (amountPhotosOtherCameras * 10) > curiosityCamera.getValue());
		}
		
	}
	
}
