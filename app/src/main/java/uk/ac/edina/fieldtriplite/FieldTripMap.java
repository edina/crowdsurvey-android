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


public class FieldTripMap extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private static final String LOG_TAG = "FieldtripMap Activity" ;
    WebView webView = null ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // setContentView(R.layout.activity_field_trip_map);


        //this.webView = new WebView(this) ;
        //this.locationAPI = new WebViewLocationAPI(this.webView);

      //  wv1.loadDataWithBaseURL(null, "HTML String", mimeType, encoding, null);

       // setContentView(this.webView);
        setContentView(R.layout.activity_field_trip_map);
        this.webView  = (WebView)findViewById(R.id.web);
        WebSettings webSettings = this.webView.getSettings() ;
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setBlockNetworkImage(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadsImagesAutomatically(true);

        //this.webView.addJavascriptInterface(this.locationAPI, "AndroidLocationAPI");


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            this.webView.setWebContentsDebuggingEnabled(true);
        }

        this.webView.setWebViewClient(new WebViewClient() {
            @Override

            public void onPageFinished(WebView view, String url) {

                if("file:///android_asset/html/map.html".equals(url))
                {
                    Log.d(LOG_TAG, " LOADED map.html") ;
                    //locationAPI.setCallbackScriptLoaded(true);
                    //locationAPI.requestLocationFix(3);
                    // request 3 updates initially to get more accurate initial fix

                }
                else
                {
                    Log.d(LOG_TAG, "onPageFinished:" + url ) ;
                }

            }
        });


        this.webView.post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl("file:///android_asset/html/map.html");
            }
        });



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
