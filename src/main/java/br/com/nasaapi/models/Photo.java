package br.com.nasaapi.models;

/**
 * This class allows to deserialize the photos returned by the endpoints into Photo objects.
 * @author Pamela Inacio Almeida
 */
public class Photo {
		
	private int id;
	private int sol;
	private Camera camera;
	private String img_src;
	private String earth_date;
	private Rover rover;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getSol() {
		return sol;
	}
	
	public void setSol(int sol) {
		this.sol = sol;
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	
	public String getImage() {
		return img_src;
	}
	
	public void setImage(String image) {
		this.img_src = image;
	}
	
	public String getEarth_date() {
		return earth_date;
	}
	
	public void setEarth_date(String earth_date) {
		this.earth_date = earth_date;
	}
	
	public Rover getRover() {
		return rover;
	}
	
	public void setRover(Rover rover) {
		this.rover = rover;
	}

}
