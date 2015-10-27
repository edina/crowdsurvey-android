package uk.ac.edina.fieldtriplite;

import android.location.Location;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import uk.ac.edina.fieldtriplite.utils.LocationUtils;

/**
 * Created by benbutchart on 21/10/15.
 */



public class WebViewLocationAPI {


    private static final String LOG_TAG = "WebViewLocationAPI" ;
    private WebView webView ;
    private Location currentLocation = null ;
    private LocationServicesClient locationClient ;

    private boolean updatesRequested = false ;
    private boolean locationFixObtained = false ;
    private int updateInterval = 0 ;
    private boolean isCallbackScriptLoaded = false ;


    public WebViewLocationAPI(WebView webView)
    {
        this.webView = webView ;
        this.locationClient = new LocationServicesClient(webView.getContext(), this) ;

    }

    //  we need to check if the WebView has fully loaded the html page and javascript before we
    // start callback with location updates.

    public boolean isCallbackScriptLoaded() {

        Log.d(LOG_TAG, "isCallbackScriptLoaded:" + isCallbackScriptLoaded) ;
        return isCallbackScriptLoaded;
    }

    public void setCallbackScriptLoaded(boolean isCallbackScriptLoaded) {
        this.isCallbackScriptLoaded = isCallbackScriptLoaded;
    }

    public void setCurrentLocation(Location location)
    {

        this.currentLocation = location ;
    }


    /* the methods below are callable using Javascript from the WebView
    * for example calling <code>LocationAPI.isLocationFixObtained()</code>
    * from the Javascript will cause the public method below to execute
     */

    @JavascriptInterface
    public boolean isLocationFixObtained()
    {
        return this.locationFixObtained ;
    }

    @JavascriptInterface
    public void requestLocationFix(int numUpdates)
    {
        this.locationClient.requestLocationFix(numUpdates);

    }

    @JavascriptInterface
    public void requestLocationUpdates(int interval)
    {
        this.locationClient.requestLocationUpdates(interval);
        this.updatesRequested = true ;
        this.updateInterval = interval ;
    }


    @JavascriptInterface
    public boolean isUpdatesRequested()
    {
        return this.updatesRequested ;
    }


    @JavascriptInterface
    public int getUpdateInterval()
    {
        return this.updateInterval ;
    }


    @JavascriptInterface
    public void stopLocationUpdates()
    {
        locationClient.removeLocationUpdates();
        this.updatesRequested = false ;
    }


    /*
    *  The methods below allow us to notify the Javascript that an event has occured
     *  such as a location fix being obtained. WE use the WEbView.loadUrl method to invoke a Javascript method
      *  it is important that the webview has already loaded the Javascript before this method is called
    *
    */


    public void onLocationUpdate(Location location)
    {
        final Location updateLocation = location ;
        this.currentLocation = location ;
        this.locationFixObtained = true ;

        if(webView != null && isCallbackScriptLoaded ) {
            webView.post(new Runnable() {
                @Override
                public void run() {
                    String latlon = LocationUtils.getLocationAsGeoJSONPoint(updateLocation);
                    webView.loadUrl("javascript:onLocationUpdate('" + latlon + "');");

                }
            });

        }
    }


    public void onLocationFix(Location location)
    {
        final Location locationFix = location ;
        this.currentLocation = location ;
        this.locationFixObtained = true ;

        if(this.webView != null && isCallbackScriptLoaded ) {
            this.webView.post(new Runnable() {
                @Override
                public void run() {

                    final String latlon = LocationUtils.getLocationAsGeoJSONPoint(locationFix);
                    Log.d(LOG_TAG, "callback to onLocationFix() with latlon" + latlon) ;
                    webView.loadUrl("javascript:onLocationFix('" + latlon + "');");

                }
            });
        }
    }


}
