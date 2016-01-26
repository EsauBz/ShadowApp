package com.ei3info.gsun;

/**
 * class referring to the user's GPS positionning
 */
public class PositionUtilisateur {


	public double latitude; // in degrees
	public double longitude; // in degrees
	

	/**
	 * constructor with latitude and longitude parameters
	 * @param lat
	 * @param lon
	 */
	public PositionUtilisateur(double lat, double lon){
		this.latitude = lat;
		this.longitude = lon;
	}


	/**
	 * Latitude's Getter
	 * @return latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * Latitude's Setter
	 * @param latitude
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * Longitude's Getter
	 * @return longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * Latitude's Setter
	 * @param longitude
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

}
