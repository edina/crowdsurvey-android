package uk.ac.edina.fieldtriplite.survey;

import android.app.Activity;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import uk.ac.edina.fieldtriplite.activity.SurveyActivity;

/**
 * Created by murrayking on 14/01/2016.
 */
public class CameraFieldHelper implements Observer {

    //Request codes for child activities
    private int REQUEST_IMAGE_CAPTURE = 100;
    private int REQUEST_LOAD_IMAGE = 101;
    private ImageView thumbImage;
    private static String LOG_TAG = "camera";
    private Activity activity;
    private URI photoLocation;

    public CameraFieldHelper(SurveyActivity activity, ImageView thumbImage) {

        activity.getObservableCameraChange().deleteObservers();
        activity.getObservableCameraChange().addObserver(this);

        this.activity = activity;
        this.thumbImage = thumbImage;
    }

    public void takePhoto() {
        if (activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            try {
                photoLocation = this.createImageFile().toURI();
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoLocation );
                if (intent.resolveActivity(activity.getPackageManager()) != null) {
                    activity.startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                    Log.d(LOG_TAG, "takePicture intent launched");
                } else {
                    Log.d(LOG_TAG, "No activity can handle takePicture");
                }
            } catch (IOException e) {
                Log.d(LOG_TAG, e.getMessage());
            }
        } else
            Log.d(LOG_TAG, "The device does not have camera");
    }

    /**
     * This method creates a File under primary_external_storage_for_app/files/pictures
     *
     * @return File descriptor
     * @throws IOException if external storage is not available, the folder CROWD_SURVEY does not exist or
     *                     the file cannot be created
     */
    private File createImageFile() throws IOException {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            //File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "CROWD_SURVEY");
            File folder = new File(activity.getExternalFilesDir(null), "pictures");
            Log.d(LOG_TAG, folder.getAbsolutePath());
            if (!folder.exists()) {
                folder.mkdirs();
            }
            if (folder.exists()) {
                String fileName = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
                File image = File.createTempFile(fileName, ".jpg", folder);
                return image;
            }
            throw new IOException("Directory CROWD_SURVEY does not exist");

        }
        throw new IOException("The external storage is not available for writing");
    }

    /**
     * Open Image application for images located under external storage.
     */
    public void chooseFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivityForResult(intent, REQUEST_LOAD_IMAGE);
        } else
            Log.d(LOG_TAG, "There is no activity to handle Pick an item from the data");
    }


    @Override
    public void update(Observable observable, Object data) {
        SurveyActivity.ActivityResult r = (SurveyActivity.ActivityResult) data;
        if (r.getRequestCode() == REQUEST_IMAGE_CAPTURE) {
            if (r.getResultCode() == Activity.RESULT_OK) {
                Log.d(LOG_TAG, "takePicture result OK");

                Bundle extras = r.getData().getExtras();
                URI test = (URI)extras.get(MediaStore.EXTRA_OUTPUT);


                Bitmap imageBitmap = (Bitmap) extras.get("data");
                thumbImage.setImageBitmap(imageBitmap);
                Log.d(LOG_TAG, "Image has been created at " + photoLocation.getPath());
                thumbImage.setTag(photoLocation.getPath());


            } else if (r.getResultCode() == Activity.RESULT_CANCELED) {
                Log.d(LOG_TAG, "takePicture result CANCEL. Picture might be taken but cancelled later");
            }
        } else if (r.getRequestCode() == REQUEST_LOAD_IMAGE) {
            if (r.getResultCode() == Activity.RESULT_OK) {
                Uri selectedImageUri = r.getData().getData();
                Log.d(LOG_TAG, "Image has been loaded from " + selectedImageUri.getPath());


                Bitmap thumbNail = getThumbNail(selectedImageUri);
                thumbImage.setImageBitmap(thumbNail);

                thumbImage.setTag(selectedImageUri.getPath());
            } else if (r.getResultCode() == Activity.RESULT_CANCELED) {
                Log.d(LOG_TAG, "No image has been chosen");
            }
        }
    }

    private Bitmap getThumbNail(Uri selectedImageUri) {

        String[] projection = {MediaStore.MediaColumns.DATA};
        CursorLoader cursorLoader = new CursorLoader(activity, selectedImageUri, projection, null, null,
                null);
        Cursor cursor = cursorLoader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        String selectedImagePath = cursor.getString(column_index);
        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);
        return bm;
    }
}
