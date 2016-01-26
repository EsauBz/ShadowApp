package fr.ecn.common.core.geometry;

/**
 * 
 * @author Claire Cervera
 * @author Julien Bardonnet
 */
public class Line {

	// =============================================================================//
	// Attributes:
	// We have represented the non-vertical lines with the equation y=ax+b
	// 
	// Line's attributes will be coefficients a and b.
	// =============================================================================//
	protected double a;
	protected double b;

	// =============================================================================//
	// Constructors
	// =============================================================================//
	/**
	 * Construction of a line from two points
	 * 
	 * @param P1
	 * @param P2
	 */
	public Line(Point P1, Point P2) {
		this.a = (P1.getY() - P2.getY()) / (P1.getX() - P2.getX());
		this.b = (P1.getY() - this.a * P1.getX());
	}

	/**
	 * base constructor :  horizontale line passing through (0,0).
	 */
	public Line() {
		this.a = 0;
		this.b = 0;
	}

	/**
	 * Constructor with a and b :
	 */
	public Line(double a, double b) {
		this.a = a;
		this.b = b;
	}

	/**
	 * @return the slope of the line
	 */
	public double getA() {
		return a;
	}

	/**
	 * @return the the y-intercept of the line
	 */
	public double getB() {
		return b;
	}

	/**
	 * Calculation of y the ordinate of a point on the straight line and the abscissa x .
	 * 
	 * @param x
	 * @return a point
	 */
	public Point calculY(double x) {
		double y = this.a * x + this.b;
		return new Point(x, y);
	}

	/**
	 * Calculating x the abscissa of a point belonging to the right and y-coordinate . 
	 * 
	 * @param y
	 * @return a point
	 */

	public Point calculX(double y) {
		double x = 0;
		if (this.a != 0) {
			x = 1 / (this.a) * (y - this.b);
		}
		// In the case where a=0, we have a horizontal line,
		// so we can have any x values, we choose
		// arbitrarily 0.
		return new Point(x, y);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Line [a=" + a + ", b=" + b + "]";
	}
}
