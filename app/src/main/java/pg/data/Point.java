package pg.data;

import Jama.Matrix;

/**
 * A class to handle Points in projective geometry
 * @author Cedric Telegone, ECN 2010
 */
public class Point {
	protected Vector coord;
	protected boolean drawable = false;
	protected double mag;

	/**
	 * create a new Point from a vector
	 * @param v vector of coordinates
	 */
	public Point(Vector v) {
		this.coord = v;
	}

	/**
	 * create a new Point from 2 doubles (plan representation)
	 * @param x
	 * @param y
	 */
	public Point(double x, double y) {
		// creates the homogeneous representation from the inhomogeneous one
		coord = new Vector(x, y);
	}

	/**
	 * create a new Point from 3 doubles (spatial representation)
	 * @param x
	 * @param y
	 * @param z
	 */
	public Point(double x, double y, double z) {
		coord = new Vector(x, y, z);
	}

	/**
	 * Vector's Getter
	 * @return vector's coordinates
	 */
	public Vector getVec() {
		return coord;
	}

	/**
	 * Point's drawing is allowed
	 */
	public void drawable() {
		drawable = true;
	}

	/**
	 * Point's drawing is not allowed
	 */
	public void notDrawable() {
		drawable = false;
	}

	/**
	 * tell if a Point is going to be drawn
	 * @return true if the Point is going to be drawn
	 */
	public boolean isDrawable() {
		return drawable;
	}

	/**
	 * X coordinate's Getter
	 * @return X
	 */
	public double getX() {
		return coord.getX();
	}

	/**
	 * Y coordinate's Getter
	 * @return Y
	 */
	public double getY() {
		return coord.getY();
	}

	/**
	 * Z coordinate's Getter
	 * @return Z
	 */
	public double getZ() {
		return coord.getZ();
	}

	/**
	 * normalize the homogeneous coordinates (z coordinates = 1)
	 */
	public void normalize() {
		coord = coord.normalize();
	}

	/**
	 * tell if the Point belongs to a specified line
	 * @param l the line that may contain the Point
	 */
	public boolean liesOn(Line l) {
		if (coord.scalar(l.getVec()) == 0)
			return true;
		else
			return false;
	}

	/**
	 * tell if a segment and a double are aligned
	 * @param s, a segment
	 * @param t, a double
	 * @return true if there are aligned
	 */
	public boolean aligned(SegmentPG s, double t) {
		if (Math.abs(angle(s)) > 1 - t) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * give an angle
	 * @param s, a segment
	 * @return the angle
	 */
	public double angle(SegmentPG s) {
		double xa = coord.getX() - s.getP1().getX();
		double ya = coord.getY() - s.getP1().getY();
		double xb = s.getP2().getX() - s.getP1().getX();
		double yb = s.getP2().getY() - s.getP1().getY();
		double a_scalaire_b = xa * xb + ya * yb;
		double norme_a = Math.sqrt(xa * xa + ya * ya);
		double norme_b = Math.sqrt(xb * xb + yb * yb);
		double angle = (a_scalaire_b / norme_a) / norme_b;

		return angle;

	}

	/**
	 * get a Line from 2 points (current point and parameter point)
	 * @param p, the second point
	 * @return l the computed line
	 */
	public Line cross(Point p) {
		return new Line(coord.normalize().cross(p.getVec().normalize()));
	}

	/**
	 * give the distance from a point p
	 * @param p, the point
	 * @return the distance
	 */
	public double distance(Point p) {
		return coord.distance(p.getVec());
	}

	/***
	 * print on screen coordinates
	 */
	public void print() {
		coord.print();
	}

	/**
	 * Get the image of the point through an homography
	 * @param h, the homography
	 * @return the image point
	 */
	public Point homography(Homography h) {
		double[][] arrayH = h.getH().getArray();
		Vector l1 = new Vector(arrayH[0][0], arrayH[0][1], arrayH[0][2]);
		Vector l2 = new Vector(arrayH[1][0], arrayH[1][1], arrayH[1][2]);
		Vector l3 = new Vector(arrayH[2][0], arrayH[2][1], arrayH[2][2]);
		double x1 = l1.scalar(coord);
		double x2 = l2.scalar(coord);
		double x3 = l3.scalar(coord);

		return new Point(x1, x2, x3);
	}

	/**
	 * A Matrix between two point, specified by Zisserman in
	 * "Multiple view geometry in computer vision"
	 * @param p, a point
	 * @return
	 */
	public Matrix A(Point p) {

		// Le point courant est x, p est le point x'
		double[][] m = new double[2][9];

		m[0][0] = 0;
		m[0][1] = 0;
		m[0][2] = 0;
		m[0][3] = -p.getZ() * this.getX();
		m[0][4] = -p.getZ() * this.getY();
		m[0][5] = -p.getZ() * this.getZ();
		m[0][6] = p.getY() * this.getX();
		m[0][7] = p.getY() * this.getY();
		m[0][8] = p.getY() * this.getZ();

		m[1][0] = p.getZ() * this.getX();
		m[1][1] = p.getZ() * this.getY();
		m[1][2] = p.getZ() * this.getZ();
		m[1][3] = 0;
		m[1][4] = 0;
		m[1][5] = 0;
		m[1][6] = -p.getX() * this.getX();
		m[1][7] = -p.getX() * this.getY();
		m[1][8] = -p.getX() * this.getZ();

		return new Matrix(m);

	}

	/**
	 * transform into a pixel
	 * @return the pixel
	 */
	public Pixel toPixel() {
		normalize();
		// return new
		// Pixel((int)(Math.rint(coord.getX())),(int)(Math.rint(coord.getY())));
		Pixel p = new Pixel((int) coord.getX(), (int) coord.getY());
		return p;
	}

}
