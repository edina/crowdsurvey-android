package uk.ac.edina.fieldtriplite.tests;

import android.graphics.Bitmap;
import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.InputEvent;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;

import java.lang.reflect.Field;

import uk.ac.edina.fieldtriplite.R ;


import uk.ac.edina.fieldtriplite.FieldTripMap;
import uk.ac.edina.fieldtriplite.WebViewLocationAPI;
import uk.ac.edina.fieldtriplite.document.Record;
import uk.ac.edina.fieldtriplite.model.RecordModel;
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

    private String chromeClientConsoleMessage ;

    private enum ChromeClientConsoleCheckStatus {
        FOUND,
        NOT_FOUND
    };

    private ChromeClientConsoleCheckStatus chromeClientConsoleCheckStatus = ChromeClientConsoleCheckStatus.NOT_FOUND ;


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


        public void testWebSettings() {

            fail();
        }

        public void testIniitalLocationFix()
        {


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
            // and count map tiles loaded
            getInstrumentation().runOnMainSync(new WebViewPageReload(mWebView, mockWebViewClient)) ;
           // give it some time to load
            sleep(3000);
            Log.e("FieldTripMapTest", " testMApTileLoaded pageLoaded:" + pageLoaded + " countTileLoaded:" + countTilesLoaded);
            assertTrue("page reloaded", pageLoaded);
            assertFalse("error occured loading map", mockWebViewClient.mError);
            assertTrue("At least 6 map tiles loaded:actual loeaded" + countTilesLoaded, countTilesLoaded > 5) ;

        }

        public void testOnLocationFix_ReferenceError()
        {

            Log.e("FieldTripMapTest", " testOnLocationFix_ReferenceError:" + pageLoaded + " countTileLoaded:" + countTilesLoaded);

            countTilesLoaded = 0;

            Location location = LocationUtils.createLocation(50.34f, 2.3f, 1.0f);
            String locationJSON = LocationUtils.getLocationAsGeoJSONPoint(location);
            String loadUrl = "javascript:onLocationFix('" + locationJSON + "');";

            // use webchromclient to capture console message that occurs
            // if onLocationFix method is not defined
            ChromeClientConsoleMessageChecker chromeConsoleChecker = new ChromeClientConsoleMessageChecker("Uncaught ReferenceError") ;
            getInstrumentation().runOnMainSync(chromeConsoleChecker) ;


            // call onLocationFix Javascript function on the webview using mockWebViewClient
            getInstrumentation().runOnMainSync(new WebViewPageReload(mWebView, mockWebViewClient, loadUrl));


            // give a moment for tiles to laod
            sleep(3000);
            Log.e("FieldTripMapTest", " testOnLocationFix_ReferenceError:" + pageLoaded + " countTileLoaded:" + countTilesLoaded);
            assertTrue("Check message 'Uncaught ReferenceError' was not reported by Chrome Message Console:" + chromeClientConsoleMessage, chromeClientConsoleCheckStatus == ChromeClientConsoleCheckStatus.NOT_FOUND) ;

        }

    public void testOnLocationFix_TypeError()
    {

        Log.e("FieldTripMapTest", " testOnLocationFix_TypeError:" + pageLoaded + " countTileLoaded:" + countTilesLoaded);

        countTilesLoaded = 0;

        Location location = LocationUtils.createLocation(50.34f, 2.3f, 1.0f);
        String locationJSON = LocationUtils.getLocationAsGeoJSONPoint(location);
        String loadUrl = "javascript:onLocationFix('" + locationJSON + "');";

        // use webchromclient to capture console message that occurs
        // if onLocationFix method is not defined
        ChromeClientConsoleMessageChecker chromeConsoleChecker = new ChromeClientConsoleMessageChecker("Uncaught TypeError") ;
        getInstrumentation().runOnMainSync(chromeConsoleChecker) ;


        // call onLocationFix Javascript function on the webview using mockWebViewClient
        getInstrumentation().runOnMainSync(new WebViewPageReload(mWebView, mockWebViewClient, loadUrl));


        // give a moment for tiles to laod
        sleep(3000);
        Log.e("FieldTripMapTest", " testOnLocationFix_TypeErrork:" + pageLoaded + " countTileLoaded:" + countTilesLoaded);
        assertTrue("Check message 'Uncaught TypeError' was not reported by Chrome Message Console:" + chromeClientConsoleMessage,
                chromeClientConsoleCheckStatus == ChromeClientConsoleCheckStatus.NOT_FOUND) ;

    }



    public void testOnLocationUpdate_ReferenceError()
    {

        Log.e("FieldTripMapTest", " testOnLocationUpdate_ReferenceError:" + pageLoaded + " countTileLoaded:" + countTilesLoaded);

        countTilesLoaded = 0;

        Location location = LocationUtils.createLocation(50.34f, 2.3f, 1.0f);
        String locationJSON = LocationUtils.getLocationAsGeoJSONPoint(location);
        String loadUrl = "javascript:onLocationUpdate('" + locationJSON + "');";

        // use webchromclient to capture console message that occurs
        // if onLocationFix method is not defined
        ChromeClientConsoleMessageChecker chromeConsoleChecker = new ChromeClientConsoleMessageChecker("Uncaught ReferenceError") ;
        getInstrumentation().runOnMainSync(chromeConsoleChecker) ;
        // call onLocationFix Javascript function on the webview using mockWebViewClient
        getInstrumentation().runOnMainSync(new WebViewPageReload(mWebView, mockWebViewClient, loadUrl));


        // give a moment for tiles to laod
        sleep(3000);
        Log.e("FieldTripMapTest", " testOnLocationUpdate_ReferenceError:" + pageLoaded + " countTileLoaded:" + countTilesLoaded);

        assertTrue("Check message 'Uncaught ReferenceError' was not reported by Chrome Message Console: " + chromeClientConsoleMessage ,
                chromeClientConsoleCheckStatus == ChromeClientConsoleCheckStatus.NOT_FOUND) ;

    }



    public void testOnLocationUpdate_TypeError()
    {

        Log.e("FieldTripMapTest", " testOnLocationUpdate_TypeError:" + pageLoaded + " countTileLoaded:" + countTilesLoaded);

        countTilesLoaded = 0;

        Location location = LocationUtils.createLocation(50.34f, 2.3f, 1.0f);
        String locationJSON = LocationUtils.getLocationAsGeoJSONPoint(location);
        String loadUrl = "javascript:onLocationUpdate('" + locationJSON + "');";

        // use webchromclient to capture console message that occurs
        // if onLocationFix method is not defined
        ChromeClientConsoleMessageChecker chromeConsoleChecker = new ChromeClientConsoleMessageChecker("Uncaught TypeError") ;
        getInstrumentation().runOnMainSync(chromeConsoleChecker) ;
        // call onLocationFix Javascript function on the webview using mockWebViewClient
        getInstrumentation().runOnMainSync(new WebViewPageReload(mWebView, mockWebViewClient, loadUrl));


        // give a moment for tiles to laod
        sleep(3000);
        Log.e("FieldTripMapTest", " testOnLocationUpdate_TypeError:" + pageLoaded + " countTileLoaded:" + countTilesLoaded);
        assertTrue("Check message 'Uncaught TypeError' was not reported by Chrome Message Console: " + chromeClientConsoleMessage ,
                chromeClientConsoleCheckStatus == ChromeClientConsoleCheckStatus.NOT_FOUND) ;

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


        private class ChromeClientConsoleMessageChecker implements Runnable {

               private String searchString = "" ;

                public ChromeClientConsoleMessageChecker(String searchString)
                {
                    chromeClientConsoleCheckStatus = ChromeClientConsoleCheckStatus.NOT_FOUND ;
                    chromeClientConsoleMessage = "" ;
                    this.searchString = searchString ;
                }
                @Override
                public void run () {
                    mWebView.setWebChromeClient(new WebChromeClient() {
                        @Override
                        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                            Log.e("FieldTripMapTest", "onConsoleMessage" + consoleMessage.message());

                            if (consoleMessage.message().contains(searchString)) {
                                chromeClientConsoleCheckStatus = ChromeClientConsoleCheckStatus.FOUND ;
                                chromeClientConsoleMessage = consoleMessage.message() ;
                                Log.e("FieldTripMapTest", "onConsoleMessage: FOUND" + consoleMessage.message());
                            }

                            return true;
                        }
                    });
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
                         // mWebView.reload();
                         mWebView.loadUrl(this.loadUrl);
                     }catch(Error err)
                     {
                         Log.e("FieldTripMapTest", "WebViewPageReload:error " + err );

                     }

                }
            }
    }

}
