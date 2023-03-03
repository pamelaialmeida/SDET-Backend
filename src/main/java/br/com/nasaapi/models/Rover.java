package br.com.nasaapi.models;

/**
 * This class allows to deserialize the information about the rover cameras returned by the endpoints into Rover objects.
 * @author Pamela Inacio Almeida
 */
public class Rover {
	
	private int id;
	private String name;
	private String landing_date;
	private String launch_date;
	private String status;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLanding_date() {
		return landing_date;
	}
	
	public void setLanding_date(String landing_date) {
		this.landing_date = landing_date;
	}
	
	public String getLaunch_date() {
		return launch_date;
	}
	
	public void setLaunch_date(String launch_date) {
		this.launch_date = launch_date;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

}
