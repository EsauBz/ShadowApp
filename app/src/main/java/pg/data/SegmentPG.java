package pg.data;

/**
 * A class to handle segments in projective geometry
 * @author Cedric Telegone, ECN 2010
 */
public class SegmentPG {
	protected Point p1;
	protected Point p2;

	/**
	 * create a new SegmentPG from 2 points
	 * @param p1, the first point
	 * @param p2, the second point
	 */
	public SegmentPG(Point p1, Point p2) {

		this.p1 = p1;
		this.p2 = p2;
	}

	/**
	 * allow the segment to be drawn
	 */
	public void drawable() {
		p1.drawable();
		p2.drawable();
	}

	/**
	 * P1's Getter (get the first segment point)
	 * @return P1
	 */
	public Point getP1() {
		return p1;
	}

	/**
	 * P2's Getter (get the second segment point)
	 * @return P2
	 */
	public Point getP2() {
		return p2;
	}

	/**
	 * give the distance of the nearest point (P1 or P2)
	 * @return this distance
	 */
	public double distance(SegmentPG s) {
		double d1 = Math.min(p1.distance(s.getP1()), p1.distance(s.getP2()));
		double d2 = Math.min(p2.distance(s.getP1()), p2.distance(s.getP2()));
		return Math.min(d1, d2);

	}

	/**
	 * display segment informations
	 */
	public void print() {

		p1.print();
		p2.print();
	}

}
