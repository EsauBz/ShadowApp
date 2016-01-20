package fr.ecn.common.core.imageinfos;

/**
 * @author alexy
 *
 * An abstract class to store temporary informations to reuse previous informations
 * from other images
 */
public abstract class TemporaryImageInfos {

	// This boolean is used to know if there is previous information from a previous captured image
	private static boolean existence  = false;
	private static Coordinate latitude  = null;
	private static Coordinate longitude = null;	
	private static Double orientation = null;

	/**
	 * @return the existence
	 */
	public static boolean getExistence() {
		return existence;
	}

	/**
	 * @param existence the existence to set
	 */
	public static void setExistence(boolean existence) {
		TemporaryImageInfos.existence = existence;
	}
	
	/**
	 * @return the latitude
	 */
	public static Coordinate getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public static void setLatitude(Coordinate latitude) {
		TemporaryImageInfos.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public static Coordinate getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public static void setLongitude(Coordinate longitude) {
		TemporaryImageInfos.longitude = longitude;
	}

	public static Double getOrientation() {
		return orientation;
	}
	
	public static void setOrientation(Double orientation) {
		TemporaryImageInfos.orientation = orientation;
	}

	/**
	 * @param ImageInfos the ImageInfos used to set the temporary informations for future images
	 * when the informations are available
	 */
	public static void setAllInfos(ImageInfos imageInfos) {
		TemporaryImageInfos.setExistence(true);
		if (imageInfos.getLatitude() != null)
			TemporaryImageInfos.setLatitude(imageInfos.getLatitude());
		if (imageInfos.getLongitude() != null)
			TemporaryImageInfos.setLongitude(imageInfos.getLongitude());
		if (imageInfos.getOrientation() != null)
			TemporaryImageInfos.setOrientation(imageInfos.getOrientation());
	}	
}
