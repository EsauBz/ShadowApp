/**
 * 
 */
package fr.ecn.common.core.imageinfos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fr.ecn.common.core.geometry.Point;

/**
 * A class to store information about a face
 * 
 * @author jerome
 *
 */
public class Face implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected List<Point> points;
	
	//This boolean indicates if the face is finished or not.
	protected boolean complete;

	public Face() {
		this(false);
	}

	/**
	 * @param complete
	 */
	public Face(boolean complete) {
		super();
		this.points = new ArrayList<Point>();
		this.complete = complete;
	}

	/**
	 * @return true if this face is complete
	 */
	public boolean isComplete() {
		return complete;
	}

	/**
	 * indicate that the face is complete
	 */
	public void completed() {
		this.complete = true;
	}
	
	/**
	 * @return the points
	 */
	public List<Point> getPoints() {
		return points;
	}
}
