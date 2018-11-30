package fr.ecn.ombre.android;

import java.io.File;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.content.PermissionChecker;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fr.ecn.common.core.imageinfos.ImageInfos;
import fr.ecn.ombre.core.utils.ExifReader;

import static android.content.ContentValues.TAG;

public class OmbreActivity extends Activity {

    private static final int ACTIVITY_LOAD = 0;
    private static final int ACTIVITY_CAPTURE = 1;

    /**
     * permissions for the app
     **/
    private static final String[] INITIAL_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final int INITIAL_REQUEST = 1337;
    /**                   **/

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main);

        Button loadButton = (Button) findViewById(R.id.load_image);
        loadButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(i, ACTIVITY_LOAD);
            }
        });

        Button takePictureButton = (Button) findViewById(R.id.take_picture);
        takePictureButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                i.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(OmbreActivity.this, "com.my.domain.ebz.provider", new File("/sdcard/tmp.jpg")));
                startActivityForResult(i, ACTIVITY_CAPTURE);
            }
        });

        /** request for permissions **/
        if (canAccessLocation() || canAccessCamera() || canAccessStorage()) {
            ActivityCompat.requestPermissions(this, INITIAL_PERMS, INITIAL_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ImageInfos imageInfos = null;

        switch (requestCode) {
            case ACTIVITY_LOAD:
                if (resultCode == Activity.RESULT_OK) {
                    //Finding the image absolute file path
                    Cursor cursor = this.getContentResolver().query(data.getData(), null, null, null, null);
                    cursor.moveToFirst();
                    int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    String absoluteFilePath = cursor.getString(idx);

                    imageInfos = new ImageInfos(absoluteFilePath);

                    //Read data from the exif
                    ExifReader.readExif(imageInfos);

                    Intent i = new Intent(this, ImageInfosActivity.class);
                    i.putExtra("ImageInfos", imageInfos);
                    i.putExtra("LoadedImage", true);
                    this.startActivity(i);
                }
                break;
            case ACTIVITY_CAPTURE:
                if (resultCode == Activity.RESULT_OK) {
                    File f = new File("/sdcard/tmp.jpg");

                    imageInfos = new ImageInfos(f.getAbsolutePath());

                    Intent i = new Intent(this, SensorActivity.class);
                    i.putExtra("ImageInfos", imageInfos);
                    this.startActivity(i);
                }
                break;
        }
    }

    /**
     * @return
     */
    private boolean canAccessLocation() {
        return (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED);
    }

    /**
     * @return
     */
    private boolean canAccessCamera() {
        return (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED);
    }

    /**
     * @return
     */
    private boolean canAccessStorage() {
        return (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED);
    }

}	