package pg.data;

/**
 * A class to handle pixel in projective geometry
 * @author Cedric Telegone, ECN 2010
 */
public class Pixel {
	protected int x;
	protected int y;

	/**
	 * create a pixel from his x and y coordinates
	 * @param x coordiante
	 * @param y coordiante
	 */
	public Pixel(int x, int y) {
		// translates from the homogeneous representation to the inhomogeneous' one
		this.x = x;
		this.y = y;
	}

	/**
	 * Homogeneous Vector's Getter
	 * get an homogeneous vector from the pixel coordinates
	 */
	public Vector getVec() {
		return new Vector((double) x, (double) y, 1);
	}

	/**
	 * X coordinate's Getter
	 * get x coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * Y coordiante's Getter
	 * get y coordinate
	 */
	public int getY() {
		return y;
	}

	/**
	 * Transform a pixel into a Point
	 * @return the transformed Point
	 */
	public Point toPoint() {
		return new Point(this.getVec());
	}

}
