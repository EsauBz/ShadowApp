package fr.ecn.ombre.android;

import java.util.LinkedList;
import java.util.List;

import android.graphics.Bitmap;

import fr.ecn.common.android.image.BitmapLoader;
import fr.ecn.common.core.geometry.Point;
import fr.ecn.common.core.imageinfos.Face;
import fr.ecn.common.core.imageinfos.ImageInfos;

public class FacesSimpleController {
	
	protected Bitmap bitmap;
	
	// We explicitly need a LinkedList here because we need the capacity to
	// remove the last element of the list
	// In fact what we need is only something that implements the Deque and the
	// List interfaces
	protected LinkedList<Face> faces = new LinkedList<Face>();
	
	protected Face currentFace = null;
	protected Point currentPoint = null;
	
	protected int mode = MODE_EDIT;
	
	public static final int MODE_EDIT = 1;
	public static final int MODE_CREATE = 2;

	public FacesSimpleController(ImageInfos imageInfos) {
		this.bitmap = BitmapLoader.loadResized(imageInfos.getPath(), BitmapLoader.maxDim).bitmap;
	}
	
	/**
	 * @return the mode
	 */
	public int getMode() {
		return mode;
	}
	
	/**
	 * @return true if the controller is in face creation mode
	 */
	public boolean isCreate() {
		return this.mode == MODE_CREATE;
	}
	
	/**
	 * @return true if the controller is in face edition mode
	 */
	public boolean isEdit() {
		return this.mode == MODE_EDIT;
	}
	
	/**
	 * Start creation mode
	 */
	public void startFace() {
		this.mode = MODE_CREATE;
		this.currentFace = new Face(false);
	}
	
	/**
	 * End creation mode
	 */
	public void endCreationMode() {
		this.currentFace = null;
	}
	
	/**
	 * Start edition mode
	 */
	public void editLastFace() {
		this.mode = MODE_EDIT;
		this.currentFace = this.faces.getLast();
	}
	
	/**
	 * Cancel edition or creation of the current face
	 */
	public void cancelFace() {
		this.mode = MODE_CREATE;
		this.currentFace = new Face(false);
		this.currentPoint = null;
	}
	
	/**
	 * End the current Face
	 */
	public void endFace() {
		//If we are in creation mode, add the face to the list of faces and indicate that the face has been completed
		if (this.isCreate()) {
			this.currentFace.completed();
			this.faces.add(this.currentFace);
		}
		
		this.mode = MODE_CREATE;
		this.currentFace = new Face(false);
		this.currentPoint = null;
	}

	/**
	 * Add a point to the current face
	 * 
	 * @param x
	 * @param y
	 */
	public void addPoint(float x, float y) {
		//With this, we can create faces with more than 4 points
		//To end the face the selected point must be the first one only if there is more than 3 points
		if (this.currentFace.getPoints().size() >= 3) {
			Point p = this.currentFace.getPoints().get(0);
			double delta = 25;
			if (p.getX() < x + delta && p.getX() > x - delta && p.getY() < y + delta && p.getY() > y - delta) {
				this.endFace();
			} else {
				this.currentFace.getPoints().add(new Point(x, y));
			}
		} else {
			this.currentFace.getPoints().add(new Point(x, y));
		}
	}
	
	/**
	 * Select a point on the current face based on x and y coordinates 
	 * 
	 * @param x
	 * @param y
	 */
	public void selectPoint(float x, float y) {
		for (Point point : this.currentFace.getPoints()) {
			double delta = 25;
			if (point.getX() < x + delta && point.getX() > x - delta
					&& point.getY() < y + delta && point.getY() > y - delta) {
				this.currentPoint = point;
			}
		}
	}
	
	/**
	 * Deselect the current point
	 */
	public void deselectPoint() {
		this.currentPoint = null;
	}
	
	/**
	 * Move the current point to the give coordinates
	 * 
	 * @param x
	 * @param y
	 */
	public void movePoint(float x, float y) {
		if (this.currentPoint == null) {
			return;
		}
		
		this.currentPoint.setX(x);
		this.currentPoint.setY(y);
	}
	
	/**
	 * Remove the last face added
	 */
	public void removeLastFace() {
		this.faces.removeLast();
	}

	/**
	 * @return the bitmap
	 */
	public Bitmap getBitmap() {
		return bitmap;
	}
	
	/**
	 * @return the faces
	 */
	public List<Face> getFaces() {
		return faces;
	}

	/**
	 * @return the face
	 */
	public Face getCurrentFace() {
		return currentFace;
	}

}
