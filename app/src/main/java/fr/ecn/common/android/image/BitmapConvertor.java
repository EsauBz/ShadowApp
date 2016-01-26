package fr.ecn.common.android.image;

import android.graphics.Bitmap;

import fr.ecn.common.core.image.ColorImage;

/**
 * @author jerome
 *
 * A class that contains methods to get an image from a bitmap and a bitmap from an image
 */
public class BitmapConvertor {
	/**
	 * @author jerome
 	 * methods to get an image from a bitmap
 	 * @param bitmap type : Bitmap
 	 * @return an image with the same characteristics as bitmap
 	 */
	public static ColorImage bitmapToImage(Bitmap bitmap) {
        int width  = bitmap.getWidth();
        int height = bitmap.getHeight();
        
        ColorImage image = new ColorImage(width, height);
        bitmap.getPixels(image.getData(), 0, width, 0, 0, width, height);
        
        return image;
	}
	
	/**
	 * @author jerome
 	 * methods to get a bitmap from an image
 	 * @param image type : ColorImage
 	 * @return a Bitmap with the same characteristics as image
 	 */
	public static Bitmap imageToBitmap(ColorImage image) {
		return Bitmap.createBitmap(image.getData(), image.getWidth(), image.getHeight(), Bitmap.Config.ARGB_8888);
	}
	
}
