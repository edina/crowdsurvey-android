package uk.ac.edina.fieldtriplite;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.views.MapView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import uk.ac.edina.fieldtriplite.activity.SurveyActivity;
import uk.ac.edina.fieldtriplite.service.SurveyServiceImpl;


public class FieldTripMap extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    public static final String LOG_TAG = "FieldtripMap Activity" ;
    private static final int PERMISSIONS_LOCATION = 0;
    MapView mv;

    //Request codes for child activities
    private int REQUEST_IMAGE_CAPTURE = 100;
    private int REQUEST_LOAD_IMAGE = 101;

    private String APP_FOLDER_NAME = "CROWN_SURVEY";

    @SuppressWarnings("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_field_trip_map);



        mv = (MapView) findViewById(R.id.mapview);

        mv.setStyle(this.getString(R.string.styleUrl));
        mv.setCenterCoordinate(new LatLng(0, 0));

        // Show user location (purposely not in follow mode)
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_LOCATION);
        } else {
            mv.setMyLocationEnabled(true);
        }

//		mv.loadFromGeoJSONURL("https://gist.githubusercontent.com/tmcw/10307131/raw/21c0a20312a2833afeee3b46028c3ed0e9756d4c/map.geojson");
        mv.addMarker(new MarkerOptions().title("Edinburgh").snippet("Scotland").position(new LatLng(55.94629, -3.20777)));
        mv.addMarker(new MarkerOptions().title("Stockholm").snippet("Sweden").position(new LatLng(59.32995, 18.06461)));
        mv.addMarker(new MarkerOptions().title("Prague").snippet("Czech Republic").position(new LatLng(50.08734, 14.42112)));
        mv.addMarker(new MarkerOptions().title("Athens").snippet("Greece").position(new LatLng(37.97885, 23.71399)));
        mv.addMarker(new MarkerOptions().title("Tokyo").snippet("Japan").position(new LatLng(35.70247, 139.71588)));
        mv.addMarker(new MarkerOptions().title("Ayacucho").snippet("Peru").position(new LatLng(-13.16658, -74.21608)));
        mv.addMarker(new MarkerOptions().title("Nairobi").snippet("Kenya").position(new LatLng(-1.26676, 36.83372)));
        mv.addMarker(new MarkerOptions().title("Canberra").snippet("Australia").position(new LatLng(-35.30952, 149.12430)));


        mv.onCreate(savedInstanceState);

        // setup toolbar, floating action button drawer and navigation view
       viewInit();

        Intent intent = getIntent();
        Log.d(LOG_TAG, intent.toString());
        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            downloadSurvey(intent.getData());
        }
    }


    private void downloadSurvey(Uri uri) {
        String surveyId = uri.getQueryParameter("surveyId");
        SurveyServiceImpl surveyService = new SurveyServiceImpl(this);

        if (surveyId != null) {
            Log.d(LOG_TAG, "Activating surveyId: " + surveyId);
            surveyService.downloadSurvey(surveyId);
        }
    }


    private void viewInit()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



    }

    /**
     * Dispatch onStart() to all fragments.  Ensure any created loaders are
     * now started.
     */
    @Override
    protected void onStart() {
        super.onStart();
        mv.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mv.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mv.onDestroy();
    }

    /**
     * Dispatch onResume() to fragments.  Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are <em>not</em> resumed.  This means
     * that in some cases the previous state may still be saved, not allowing
     * fragment transactions that modify the state.  To correctly interact
     * with fragments in their proper state, you should instead override
     * {@link #onResumeFragments()}.
     */
    @Override
    protected void onResume() {
        super.onResume();
        mv.onResume();
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        mv.onPause();
    }

    /**
     * Save all appropriate fragment state.
     *
     * @param outState outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mv.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.field_trip_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.show_records) {
            // Handle the camera action
        } else if (id == R.id.upload_records) {

        } else if (id == R.id.list_records) {

        } else if (id == R.id.delete_records) {

        } else if (id == R.id.take_photo) {
            this.takePhoto();
        } else if (id == R.id.choose_gallery){
            this.chooseFromGallery();
        }
        else if (id == R.id.show_tracks) {

        } else if (id == R.id.list_tracks) {

        }
        else if (id == R.id.delete_tracks) {

        }else if (id == R.id.map_overlays) {

        }else if (id == R.id.pois) {

        } else if (id == R.id.custom_form){
            Intent customSurveyForm = new Intent(this.getBaseContext(), SurveyActivity.class);
            this.startActivity(customSurveyForm);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void takePhoto() {
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            try {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, this.createImageFile().toURI());
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                    Log.d(LOG_TAG, "takePicture intent launched");
                } else
                    Log.d(LOG_TAG, "No activity can handle takePicture");
            } catch (IOException e) {
                Log.d(LOG_TAG, e.getMessage());
            }
        } else
            Log.d(LOG_TAG, "The device does not have camera");
    }
    /**
     * This method creates an empty File on the External Storage created for the app
     * @return File descriptor
     * @throws IOException if the External Storage is not available for writing
     * the file cannot be created
     */
    private File createImageFile() throws IOException{
        this.createAppFolderOnExternalStorage();
        String fileName = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        File image = File.createTempFile(fileName, ".jpg", new File(Environment.getExternalStorageDirectory() + "/"+APP_FOLDER_NAME));
        Log.d(LOG_TAG, "Empty file created in:" + image.getAbsolutePath());
        return image;
    }

    /**
     * Open the gallery filtered to choose only images
     */
    private void chooseFromGallery(){
        try {
            this.createAppFolderOnExternalStorage();
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/"+APP_FOLDER_NAME+"/"));
            intent.setDataAndType(uri,"image/*");
            Log.d(LOG_TAG, "Directory of data to pick up an image:" + intent.getData().toString());
            if(intent.resolveActivity(getPackageManager()) != null){
                startActivityForResult(Intent.createChooser(intent,"Choose an image"), REQUEST_LOAD_IMAGE);
            }
            else
                Log.d(LOG_TAG, "There is no activity to handle ACTION_PICK image from the external storage");
        }catch(Exception e){
            Log.d(LOG_TAG,e.getMessage());
        }
    }

    /**
     * Creates a folder for the app on the External Storage of the device.
     * @return True if created or False if already exists.
     * @throws IOException if the External Storage is not available for writing
     */
    private Boolean createAppFolderOnExternalStorage() throws IOException{
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            File f = new File(Environment.getExternalStorageDirectory(),APP_FOLDER_NAME);
            if(!f.exists()) {
                f.mkdirs();
                return true;
            }
            return false;
        }
        throw new IOException("The external storage is not available for writing");
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE){
            if(resultCode == RESULT_OK)
                Log.d(LOG_TAG,"takePicture result OK");
            else if(resultCode == RESULT_CANCELED)
                Log.d(LOG_TAG,"takePicture result CANCEL. Picture might be taken but cancelled later");
        }
        else if(requestCode == REQUEST_LOAD_IMAGE){
            if(resultCode == RESULT_OK){
                Uri uri = data.getData();
                Log.d(LOG_TAG,"Image has been loaded from "+uri.getPath());
            }
            else if(resultCode == RESULT_CANCELED){
                Log.d(LOG_TAG,"No image has been chosen");
            }
        }
    }
}
