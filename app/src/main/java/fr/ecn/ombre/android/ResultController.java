package fr.ecn.ombre.android;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import android.graphics.Bitmap;

import fr.ecn.common.android.image.BitmapConvertor;
import fr.ecn.common.android.image.BitmapLoader;
import fr.ecn.common.core.geometry.Point;
import fr.ecn.common.core.image.Image;
import fr.ecn.common.core.imageinfos.Face;
import fr.ecn.common.core.imageinfos.ImageInfos;
import fr.ecn.ombre.core.shadows.Couple;
import fr.ecn.ombre.core.shadows.ShadowDrawingFace;
import fr.ecn.ombre.core.shadows.ShadowDrawing;
import fr.ecn.ombre.core.shadows.ShadowDrawingException;
import fr.ecn.ombre.core.shadows.ShadowDrawingFactory;

public class ResultController implements Callable<Void> {
	
	//Params
	protected ImageInfos imageInfos;
	protected Calendar time;
	protected boolean shadowsOnWalls;
	protected boolean expendToStreet;

	protected boolean evolution = false;
	protected int time_step_field;
	protected int time_step_value;
	
	protected Bitmap bitmap;
	
	//Results
	protected Point sunPosition;
	protected List<ShadowDrawingFace> shadows;
	
	private Future<Void> future;

	public ResultController(ImageInfos imageInfos, Calendar time, boolean shadowsOnWalls, boolean expendToStreet) {
		this.imageInfos = imageInfos;
		this.time = time;
		this.shadowsOnWalls = shadowsOnWalls;
		this.expendToStreet = expendToStreet;
		
		this.bitmap = BitmapLoader.loadResized(imageInfos.getPath(), BitmapLoader.maxDim).bitmap;
		
		this.startComputation();
	}

	public Void call() throws ShadowDrawingException {
		this.shadows = new LinkedList<ShadowDrawingFace>();
		
		// test if geometry is'nt empty
		if (this.imageInfos.getFaces().isEmpty()) {
			throw new ShadowDrawingException("Vous devez rentrer au moins une face");
		}
		
		//Creating the list of ShadowDrawingFace
		List<ShadowDrawingFace> faces = new LinkedList<ShadowDrawingFace>();
		for (Face f : imageInfos.getFaces()) {
			faces.add(new ShadowDrawingFace(f));
		}

		Image image = BitmapConvertor.bitmapToImage(this.bitmap);
		
		ShadowDrawingFactory sdf = new ShadowDrawingFactory(image, this.imageInfos, faces, time);

		ShadowDrawing shadowDrawing = sdf.getShadowDrawing();
		
		this.sunPosition = sdf.getSunPosition();
		
		// =============================================================//
		// CALCUL OF THE SHADOW //
		// =============================================================//
		
		
		for (ShadowDrawingFace f : faces) {

			// First calcul for the shadow projected on the ground, we test if the position of the ray
			// isn't good.. 
			ShadowDrawingFace faceOmbre = shadowDrawing.drawShadow(f);
			// if the shadow isn't in the right position, we stop.
			if (!faceOmbre.isOutside()) {
				continue;
			}
			
			this.shadows.add(f);
			
			/**
			 * We only make the next step if the "wall" case is checked
			 * (it means only if the user wants to see the shadow on the wall)
			 * if we have only one face, the calcul is useless
			 */
			if (shadowsOnWalls && imageInfos.getFaces().size() > 1) {
				// on boucle sur les faces autres que f :
				for (ShadowDrawingFace f2 : faces) {
					if (f2 != f) {
						// We make the calcul for the points which have their shadow on the face called "f2"
						// we put them ina vector untitled "vectPointOmbreF2"
						Couple[] vectPointOmbreF2 = shadowDrawing.calculOmbreMur(f, faceOmbre, f2);
						List<ShadowDrawingFace> ombre = shadowDrawing.determinationOmbreMur(f, faceOmbre, f2, vectPointOmbreF2,
								sdf.getCoupleSoleil());
						this.shadows.addAll(ombre);
					}
				} // end of the loop on an another face than f
				} else { // if not "wall", we just add the shadow
				this.shadows.add(faceOmbre);
			} // end if "wall"
		} 
		
		//Expend shadows to street if requested
		if (expendToStreet) {
			List<ShadowDrawingFace> expendedFaces = new LinkedList<ShadowDrawingFace>();
			for (ShadowDrawingFace face : this.shadows) {
				expendedFaces.add(face.expandToStreet(image));
			}
			this.shadows.addAll(expendedFaces);
		}
		
		return null;
	}
	
	public void startComputation() {
		this.future = Executors.newSingleThreadExecutor().submit(this);
	}
	
	public void waitComputation() throws ExecutionException {
		try {
			this.future.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean isEvolution() {
		return this.evolution;
	}
	
	public void setTimeStep(int time_step_field, int time_step_value) {
		this.time_step_field = time_step_field;
		this.time_step_value = time_step_value;
		this.evolution = true;
	}
	
	public void stepForward() {
		this.time.add(time_step_field, time_step_value);
		this.startComputation();
	}
	
	public void stepBackward() {
		this.time.add(time_step_field, -time_step_value);
		this.startComputation();
	}
	
	/**
	 * @return the imageInfos
	 */
	public ImageInfos getImageInfos() {
		return imageInfos;
	}

	/**
	 * @return the time
	 */
	public Calendar getTime() {
		return time;
	}

	/**
	 * @return the bitmap
	 */
	public Bitmap getBitmap() {
		return bitmap;
	}

	/**
	 * @return the position of the sun
	 */
	public Point getSunPosition() {
		return sunPosition;
	}

	/**
	 * @return the ombres
	 */
	public List<ShadowDrawingFace> getShadows() {
		return shadows;
	}

}
