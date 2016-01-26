package fr.ecn.ombre.android;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

import fr.ecn.common.core.imageinfos.ImageInfos;

/**
 * An activity to select faces
 * 
 * @author jerome
 *
 */
public class FacesSimpleActivity extends Activity implements OnTouchListener {

	private static final int MENU_EDIT_LAST_FACE = Menu.FIRST + 4;
	private static final int MENU_REMOVE_LAST_FACE = Menu.FIRST;
	private static final int MENU_VALIDATE = Menu.FIRST + 1;
	
	private static final int MENU_END_FACE = Menu.FIRST + 2;
	private static final int MENU_CANCEL_FACE = Menu.FIRST + 3;
	
	protected ImageInfos imageInfos;

	protected FacesSimpleController controller;

	protected Matrix matrix;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle extras = getIntent().getExtras();
		final ImageInfos imageInfos = (ImageInfos) extras
				.getSerializable("ImageInfos");
		this.imageInfos = imageInfos;

		this.controller = (FacesSimpleController) this.getLastNonConfigurationInstance();
		
		if (this.controller == null) {
			this.controller = new FacesSimpleController(imageInfos);
			this.controller.startFace();
		}

		this.setUp();
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onRetainNonConfigurationInstance()
	 */
	@Override
	public Object onRetainNonConfigurationInstance() {
		return this.controller;
	}

	/**
	 * set up views based on controller infos
	 */
	protected void setUp() {
		this.setContentView(R.layout.image);

		ImageView imageView = (ImageView) findViewById(R.id.image);

		this.matrix = null;

		Drawable[] drawables = {
				new BitmapDrawable(this.controller.getBitmap()),
				new FacesSimpleDrawable(this.controller) };
		imageView.setImageDrawable(new LayerDrawable(drawables));
		imageView.setOnTouchListener(this);
	}

	public boolean onTouch(View v, MotionEvent event) {

		if (this.matrix == null) {
			this.matrix = new Matrix();

			((ImageView) findViewById(R.id.image)).getImageMatrix().invert(
					this.matrix);
		}

		float[] point = { event.getX(), event.getY() };

		// Converting the point in image coordinate system
		this.matrix.mapPoints(point);

		if (this.controller.isCreate()) {
			if (event.getAction() != MotionEvent.ACTION_DOWN) {
				return false;
			}
			
			this.controller.addPoint(point[0], point[1]);
		} else {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				this.controller.selectPoint(point[0], point[1]);
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				this.controller.deselectPoint();
			} else {
				this.controller.movePoint(point[0], point[1]);
			}
		}

		this.findViewById(R.id.image).invalidate();

		return true;
	}

	//New method onPrepareOptionsMenu to limit simplify the method and to add the possibility to create
	//faces with more than 4 points
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		
		switch (this.controller.getMode()) {
		case FacesSimpleController.MODE_CREATE:
			boolean hasFaces = this.controller.getFaces().size() > 0;

			menu.add(0, MENU_EDIT_LAST_FACE, 0, R.string.edit_last_face).setEnabled(hasFaces && this.controller.getCurrentFace().getPoints().size()==0);
			menu.add(0, MENU_REMOVE_LAST_FACE, 0, R.string.remove_last_face).setEnabled(hasFaces && this.controller.getCurrentFace().getPoints().size()==0);
			if (hasFaces && this.controller.getCurrentFace().getPoints().size()==0) {
				menu.add(0, MENU_VALIDATE, 0, R.string.menu_validate).setEnabled(true);
			} else {
				menu.add(0, MENU_VALIDATE, 0, R.string.menu_validate).setEnabled(false);
			}
			menu.add(0, MENU_CANCEL_FACE, 0, R.string.cancel_face).setEnabled(this.controller.getCurrentFace().getPoints().size()!=0);
			break;
		case FacesSimpleController.MODE_EDIT:
			menu.add(0, MENU_END_FACE, 0, R.string.end_face);
			break;
		}

		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_EDIT_LAST_FACE:
			this.controller.editLastFace();

			this.findViewById(R.id.image).invalidate();
			
			return true;
		case MENU_REMOVE_LAST_FACE:
			this.controller.removeLastFace();

			this.findViewById(R.id.image).invalidate();
			
			return true;
		case MENU_VALIDATE:
			// we set the current face of the controller to null and we start the activity to draw the shadows
			this.controller.endCreationMode();
			this.imageInfos.setFaces(this.controller.getFaces());
			
			Intent i = new Intent(this, OptionsActivity.class);
			i.putExtra("ImageInfos", this.imageInfos);
			this.startActivity(i);
			
			return true;
			
		case MENU_END_FACE:
			this.controller.endFace();

			this.findViewById(R.id.image).invalidate();
			
			return true;
		case MENU_CANCEL_FACE:
			this.controller.cancelFace();
			
			this.findViewById(R.id.image).invalidate();
			
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
