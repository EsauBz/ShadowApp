package fr.ecn.common.core.image.utils;

import fr.ecn.common.core.image.ByteImage;
import fr.ecn.common.core.image.ColorImage;
import fr.ecn.common.core.image.FloatImage;
import fr.ecn.common.core.image.Image;
import fr.ecn.common.core.image.filters.ByteToColor;
import fr.ecn.common.core.image.filters.ColorToByte;
import fr.ecn.common.core.image.filters.FloatToByte;

public class ImageConvertor {
	/**
	*method turn image into Byteimage
	*@param image
	*@return Byteimage
	*/
	public static ByteImage toByte(Image image) {
		if (image instanceof ByteImage) {
			return (ByteImage) image;
		} else if (image instanceof ColorImage) {
			ColorToByte convertor = new ColorToByte();
			return convertor.convert((ColorImage) image);
		} else {
			throw new RuntimeException("Unsuported Image type");
		}
	}
	/**
	*method turn image into ColorImage only if image is an instance of ColorImage
	*@param image
	*@return Byteimage
	*/
	public static ColorImage toColor(Image image) {
		if (image instanceof ColorImage) {
			return (ColorImage) image;
		} else if (image instanceof ByteImage) {
			ByteToColor convertor = new ByteToColor();
			return convertor.convert((ByteImage) image);
		} else if (image instanceof FloatImage) {
			FloatToByte convertor1 = new FloatToByte();
			ByteToColor convertor2 = new ByteToColor();
			return convertor2.convert(convertor1.convert((FloatImage) image));
		} else {
			throw new RuntimeException("Unsuported Image type");
		}
	}
	
}
