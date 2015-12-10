package uk.ac.edina.fieldtriplite;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;


public class FieldTripMap extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    public static final String LOG_TAG = "FieldtripMap Activity" ;
    WebView webView = null ;
    WebViewLocationAPI locationAPI = null ;
    private WebViewClient webViewClient = null ;

    // Couchbase properties
    private static final String DATABASE_NAME = "crowdsurveydb";
    private Database database;
    private Manager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_field_trip_map);
        this.webView  = (WebView)findViewById(R.id.web);
        this.locationAPI = new WebViewLocationAPI(this.webView);


        WebSettings webSettings = this.webView.getSettings() ;
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setBlockNetworkImage(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadsImagesAutomatically(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            this.webView.setWebContentsDebuggingEnabled(true);
        }


        /*
        *  set the Javascript interface to the instance of WebViewLcoationAPI and set the
        *  name for this intefeace used in the WebView to "NativeLocationAPI"
        *  so in the Javascript you can call the method requestLocationFix() in WebViewLocationAPI class
        *  with NativeLocationAPI.onLocationFix()
        *
        */

        this.webView.addJavascriptInterface(this.locationAPI, "NativeLocationAPI");


        // we only want to start usimg native location API once the map html has loaded
        // otherwise we will end up calling Javascript callbacks before the JS is loaded into webview
        // the web view client allows us to capture the onPageFinished event where we can kick off location requests
        this.webViewClient =    new WebViewClient() {
            @Override

            public void onPageFinished(WebView view, String url) {

                if("file:///android_asset/html/map.html".equals(url))
                {
                    Log.d(LOG_TAG, " LOADED map.html") ;
                    locationAPI.setCallbackScriptLoaded(true);
                    locationAPI.isCallbackScriptLoaded();
                    // request 3 updates initially to get more accurate initial fix
                    locationAPI.requestLocationFix(3);


                }
                else
                {
                    Log.d(LOG_TAG, "onPageFinished:" + url ) ;
                }

            }
        };

        this.webView.setWebViewClient(this.webViewClient) ;

        // load the html page (and linked JS files) asynchronously
        // the onPageFinished callback above get called when the oage is loaded into the WebView
        this.webView.post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl("file:///android_asset/html/map.html");
            }
        });

        // setup toolbar, floating action button drawer and navigation view
       viewInit();

        // Init the couchbase database
        initDatabase();
    }

    /**
     * Initialize the couchbase database
     */
    private void initDatabase() {
        try {

            Manager.enableLogging(LOG_TAG, com.couchbase.lite.util.Log.VERBOSE);
            Manager.enableLogging(com.couchbase.lite.util.Log.TAG, com.couchbase.lite.util.Log.VERBOSE);
            Manager.enableLogging(com.couchbase.lite.util.Log.TAG_SYNC_ASYNC_TASK, com.couchbase.lite.util.Log.VERBOSE);
            Manager.enableLogging(com.couchbase.lite.util.Log.TAG_SYNC, com.couchbase.lite.util.Log.VERBOSE);
            Manager.enableLogging(com.couchbase.lite.util.Log.TAG_QUERY, com.couchbase.lite.util.Log.VERBOSE);
            Manager.enableLogging(com.couchbase.lite.util.Log.TAG_VIEW, com.couchbase.lite.util.Log.VERBOSE);
            Manager.enableLogging(com.couchbase.lite.util.Log.TAG_DATABASE, com.couchbase.lite.util.Log.VERBOSE);

            manager = new Manager(new AndroidContext(getApplicationContext()), Manager.DEFAULT_OPTIONS);
        } catch (IOException e) {
            com.couchbase.lite.util.Log.e(LOG_TAG, "Cannot create Manager object", e);
            return;
        }

        try {
            database = manager.getDatabase(DATABASE_NAME);
        } catch (CouchbaseLiteException e) {
            com.couchbase.lite.util.Log.e(LOG_TAG, "Cannot get Database", e);
            return;
        }
    }

    /**
     * Getter for the application database
     *
     * @return current instance of the application database
     */
    public Database getDatabase() {
        return this.database;
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

        } else if (id == R.id.show_tracks) {

        } else if (id == R.id.list_tracks) {

        }
        else if (id == R.id.delete_tracks) {

        }else if (id == R.id.map_overlays) {

        }else if (id == R.id.pois) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
