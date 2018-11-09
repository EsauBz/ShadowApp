package fr.ecn.common.core.geometry;

/**
 * @author jerome
 *
 * A class to compute distances between different objects.
 */
public class Distance {
	/**
	* @method distance : calculating the disance between two points
 	* @param p1 Point : first considered point
 	* @param p2 Point : second point to consider
 	* @return double : geometric distance between two points
 	* 
 	*/
	public static double distance(Point p1, Point p2) {
        double dx = p1.getX() - p2.getX();
        double dy = p1.getY() - p2.getY();
        return Math.sqrt( (dx * dx) + (dy * dy) );
	}
	/**
	* @method distance : calculating the disance between one point and a line
 	* @param p Point 
 	* @param l Line 
 	* @return double : distance from the point to the line
 	* 
 	*/
	public static double distance(Point p, Line l) {
		return Math.abs(l.a*p.x - p.y + l.b)/Math.sqrt(l.a*l.a + 1);
	}
}
