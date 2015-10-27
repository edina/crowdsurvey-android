package uk.ac.edina.fieldtriplite.tests;

import android.graphics.Bitmap;
import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;
import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.MediumTest;
import android.util.Log;
import android.view.InputEvent;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import uk.ac.edina.fieldtriplite.R ;


import uk.ac.edina.fieldtriplite.FieldTripMap;
import uk.ac.edina.fieldtriplite.WebViewLocationAPI;
import uk.ac.edina.fieldtriplite.utils.LocationUtils;

/**
 * Created by benbutchart on 25/10/15.
 */
public class FieldTripMapTest extends ActivityInstrumentationTestCase2<FieldTripMap> {

    private FieldTripMap mFieldTripMapActivity;
    private WebView mWebView;
    private int countTilesLoaded = 0 ;
    private boolean pageLoaded = false ;
    private MockWebViewClient mockWebViewClient ;

    public FieldTripMapTest() {
        super(FieldTripMap.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();


        // Starts the activity under test using the default Intent with:
        // action = {@link Intent#ACTION_MAIN}
        // flags = {@link Intent#FLAG_ACTIVITY_NEW_TASK}
        // All other fields are null or empty.
        mFieldTripMapActivity = getActivity();
        mWebView = (WebView) mFieldTripMapActivity.findViewById(R.id.web) ;

        assertNotNull("mFieldTripMapActivity is not null", mFieldTripMapActivity);
        assertNotNull("mWebView is not null", mWebView);


        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {

                mockWebViewClient = new MockWebViewClient();
                // set mock WebViewClient to handle callback requests
                mWebView.setWebViewClient(mockWebViewClient);
            }
        });

            // give it some time for map to load
            sleep(6000);
    }




           public void testPreconditions() {
               //Try to add a message to add context to your assertions. These messages will be shown if
               //a tests fails and make it easy to understand why a test failed
               assertNotNull("mFieldTripMapActivity is not null", mFieldTripMapActivity);
               assertNotNull("mWebView is not null", mWebView);

           }

         public void testInitialMapLoaded() {

             Log.e("FieldTripMapTest", "testInitialMapLoaded pageLoaded:" + pageLoaded + " countTileLoaded:" + countTilesLoaded);
             assertFalse("error occured loading map", mockWebViewClient.mError);
             assertTrue("map.html page loaded", pageLoaded);
         }

        public void testCallbackScriptLoadedSet() {

            Field locationApiField = null;
            Method isCallbackScriptLoaded = null ;
            try {
                locationApiField = FieldTripMap.class.
                        getDeclaredField("locationAPI");

                isCallbackScriptLoaded = WebViewLocationAPI.class.getDeclaredMethod("isCallbackScriptLoaded", null)  ;

            } catch (NoSuchFieldException e) {
                fail("Could not access private field locationAPI " + e);
            } catch (NoSuchMethodException e) {
                fail("Could not access private field locationAPI" + e);

            }

            locationApiField.setAccessible(true);
            isCallbackScriptLoaded.setAccessible(true) ;
            WebViewLocationAPI locationAPI ;

            boolean isLoaded = false ;

            try {
                locationAPI = (WebViewLocationAPI) locationApiField.get(mFieldTripMapActivity);
                 assertNotNull(locationAPI);
                 isLoaded = (boolean) isCallbackScriptLoaded.invoke(locationAPI,null) ;

            } catch (IllegalAccessException e) {
                locationAPI = null ;
                fail("could not reflect locationAPI member variable on object mFieldTripMapActivity : " +  e ) ;
            } catch (InvocationTargetException e) {
                locationAPI = null ;
                fail("could not reflect locationAPI method  on object mFieldTripMapActivity : " +  e ) ;
            }


            assertTrue("callbackScriptLoded flag was set", isLoaded ) ;

        }

        public void testMapTilesLoaded() {


            Log.e("FieldTripMapTest", " testMApTileLoaded pageLoaded:" + pageLoaded + " countTileLoaded:" + countTilesLoaded);
            pageLoaded = false ;

            getInstrumentation().runOnMainSync(new Runnable() {
                @Override
                public void run() {

                    Location location = LocationUtils.createLocation(55.954364f, -3.186198f, 1.0f);
                    String locationJSON = LocationUtils.getLocationAsGeoJSONPoint(location);
                    mWebView.reload();

                }
            });

            sleep(2000);
            Log.e("FieldTripMapTest", " testMApTileLoaded pageLoaded:" + pageLoaded + " countTileLoaded:" + countTilesLoaded);
            assertTrue("page reloaded", pageLoaded);
            assertFalse("error occured loading map", mockWebViewClient.mError);


        }

        public void testOnLocationFixCallback()
        {

            Log.e("FieldTripMapTest", " testOnLocationFixCallback:" + pageLoaded + " countTileLoaded:" + countTilesLoaded);

            countTilesLoaded = 0;
            getInstrumentation().runOnMainSync(new Runnable() {
                @Override
                public void run() {

                    Location location = LocationUtils.createLocation(50.34f, 2.3f, 1.0f);
                    String locationJSON = LocationUtils.getLocationAsGeoJSONPoint(location);
                    mWebView.loadUrl("javascript:onLocationFix('" + locationJSON + "');");

                }
            });

            sleep(2000);
            Log.e("FieldTripMapTest", " testOnLocationFixCallback:" + pageLoaded + " countTileLoaded:" + countTilesLoaded) ;
            assertEquals("Expect map tiles loaded", 6 ,countTilesLoaded) ;


        }


           private void sleep(long millis) {
               try {
                   Thread.sleep(millis);
               } catch (InterruptedException e) {
                   fail(e.getMessage());
               }
           }

           private class MockWebViewClient extends WebViewClient {
               boolean mError;

               @Override
               public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                   super.onReceivedError(view, request, error);
                   ;
                   Log.e("FieldTripMapTest", "onReceivedError");
                   mError = true;

               }

               @Override
               public void onPageStarted(WebView view, String url, Bitmap favicon) {
                   super.onPageStarted(view, url, favicon);
                   Log.e("FieldTripMapTest", "onPageStarted: " + url);
               }

               @Override
               public void onReceivedError(WebView view, int errorCode,
                                           String description, String failingUrl) {
                   super.onReceivedError(view, errorCode, description, failingUrl);

                   Log.e("FieldTripMapTest", "onReceivedError deprecated");
                   mError = true;
               }

               @Override
               public void onPageFinished(WebView view, String url) {
                   super.onPageFinished(view, url);
                   Log.e("FieldTripMapTest", "onPageFinished " + url);
                   pageLoaded = true;
               }

               @Override
               public void onLoadResource(WebView view, String url) {
                   super.onLoadResource(view, url);
                   Log.e("FieldTripMapTest", "onLoadResource:" + url);

                   if (url.endsWith("png")) {
                       countTilesLoaded++;
                   }
               }

               @Override
               public void onUnhandledInputEvent(WebView view, InputEvent event) {
                   super.onUnhandledInputEvent(view, event);
                   Log.e("FieldTripMapTest", "onUnhandledInputEvent");
               }
           }

       }
