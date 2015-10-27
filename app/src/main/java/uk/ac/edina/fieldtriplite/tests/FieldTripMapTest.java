package uk.ac.edina.fieldtriplite.tests;

import android.graphics.Bitmap;
import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;
import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.MediumTest;
import android.util.Log;
import android.view.InputEvent;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;

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
    private final MockWebViewClient mockWebViewClient = new MockWebViewClient();

    private enum CallbackStatus {
        OK,
        FAILED
    };

    private CallbackStatus callbackStatus = CallbackStatus.OK ;

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

    }


           public void testPreconditions() {
               assertNotNull("mFieldTripMapActivity is not null", mFieldTripMapActivity);
               assertNotNull("mWebView is not null", mWebView);

           }

         public void testInitialMapLoaded() {

             // relaod web page using mock web view client
             // the mock webview client will enable us to capture onReceoveError event if something goes wrong

             getInstrumentation().runOnMainSync(new WebViewPageReload(mWebView, mockWebViewClient)) ;
             // give the page a couple of seconds to load
             sleep(3000) ;

             Log.e("FieldTripMapTest", "testInitialMapLoaded pageLoaded:" + pageLoaded + " countTileLoaded:" + countTilesLoaded);
             assertFalse("error occured loading map", mockWebViewClient.mError);
             assertTrue("map.html page loaded", pageLoaded);
         }

        public void testCallbackScriptLoadedSet() {


            Field locationApiField = null;
            Field webViewClientField = null ;

            try {
                locationApiField = FieldTripMap.class.
                        getDeclaredField("locationAPI");

                webViewClientField = FieldTripMap.class.
                        getDeclaredField("webViewClient");


            } catch (NoSuchFieldException e) {
                fail("Java reflect Could not access private field " + e);
            }

            locationApiField.setAccessible(true);
            webViewClientField.setAccessible(true);
            WebViewLocationAPI locationAPI ;
            WebViewClient webViewClient ;


            try {
                // get the reference to lcoationAPI
                locationAPI = (WebViewLocationAPI) locationApiField.get(mFieldTripMapActivity);
                assertNotNull(locationAPI);
                // get the activity web view client
                webViewClient = (WebViewClient) webViewClientField.get(mFieldTripMapActivity);
                assertNotNull(webViewClient);
            } catch (IllegalAccessException e) {
                locationAPI = null;
                webViewClient = null ;
                fail("could not reflect locationAPI member variable on object mFieldTripMapActivity : " + e);
            }

            //  as we want to test a variable set by the actual webview client we need to reload
            // the page and use the FieldtripMap webViewClient rather than a mock as this will set
            // the variable in the pageFinished ccallback
            getInstrumentation().runOnMainSync(new WebViewPageReload(mWebView, webViewClient)) ;

            // give the page a couple of seconds to load
            sleep(3000) ;
            assertTrue("callbackScriptLoded flag was set by WebViewClient", locationAPI.isCallbackScriptLoaded() ) ;

        }

        public void testMapTilesLoaded() {


            Log.e("FieldTripMapTest", " testMApTileLoaded pageLoaded:" + pageLoaded + " countTileLoaded:" + countTilesLoaded);
            pageLoaded = false ;
            countTilesLoaded = 0 ;
            // reload page with MockWebViewClient to capture any onReceiveError callback

            getInstrumentation().runOnMainSync(new WebViewPageReload(mWebView, mockWebViewClient)) ;
           // give some time
            sleep(2000);
            Log.e("FieldTripMapTest", " testMApTileLoaded pageLoaded:" + pageLoaded + " countTileLoaded:" + countTilesLoaded);
            assertTrue("page reloaded", pageLoaded);
            assertFalse("error occured loading map", mockWebViewClient.mError);
            // assertEquals("Expect map tiles loaded", 6, countTilesLoaded) ;


        }

        public void testOnLocationFixCallback()
        {

            Log.e("FieldTripMapTest", " testOnLocationFixCallback:" + pageLoaded + " countTileLoaded:" + countTilesLoaded);

            countTilesLoaded = 0;

            Location location = LocationUtils.createLocation(50.34f, 2.3f, 1.0f);
            String locationJSON = LocationUtils.getLocationAsGeoJSONPoint(location);
            String loadUrl = "javascript:onLocationFix('" + locationJSON + "');";

            // use webchromclient to capture console message that occurs
            // if onLocationFix method is not defined
            getInstrumentation().runOnMainSync(new Runnable() {
                @Override
                public void run() {
                    mWebView.setWebChromeClient(new WebChromeClient() {
                        @Override
                        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                            if(consoleMessage.message().contains("onLocationFix") )
                            {
                                callbackStatus = CallbackStatus.FAILED ;
                            }
                            Log.e("FieldTripMapTest", "onConsoleMessage:" + consoleMessage.message());
                            return true ;
                        }
                    });
                }
            });


            // call onLocationFix Javascript function on the webview using mockWebViewClient
            getInstrumentation().runOnMainSync(new WebViewPageReload(mWebView, mockWebViewClient, loadUrl));


            // give a moment for tiles to laod
            sleep(3000);
            Log.e("FieldTripMapTest", " testOnLocationFixCallback:" + pageLoaded + " countTileLoaded:" + countTilesLoaded) ;
            assertTrue("Test Callback status OK", callbackStatus == CallbackStatus.OK) ;

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


       private class WebViewPageReload implements Runnable
       {
           private WebViewClient webViewClient ;
           private WebView webView ;
           private String loadUrl = null ;

           public WebViewPageReload(WebView webView, WebViewClient webViewClient)
           {
                this.webView = webView ;
                this.webViewClient = webViewClient ;

           }

           public WebViewPageReload(WebView webView, WebViewClient webViewClient, String loadUrl)
           {
               this.webView = webView ;
               this.webViewClient = webViewClient ;
               this.loadUrl = loadUrl ;

           }

            @Override
            public void run () {
                 mWebView.setWebViewClient(webViewClient);
                if(this.loadUrl == null ) {
                    // reload the page

                    Log.e("FieldTripMapTest", "WebViewPageReload:relaod()");
                    mWebView.clearCache(true);
                    mWebView.reload();

                }
                else
                {
                    // load specific page component e.g. JS function
                    Log.e("FieldTripMapTest", "WebViewPageReload:" + loadUrl);
                     try {
                         //mWebView.clearCache(true);
                         mWebView.loadUrl(this.loadUrl);
                     }catch(Error err)
                     {
                         Log.e("FieldTripMapTest", "WebViewPageReload:error " + err );

                     }

                }
            }
    }

}
