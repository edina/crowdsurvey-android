package uk.ac.edina.fieldtriplite.service;

import android.os.Looper;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.strongloop.android.loopback.callbacks.ObjectCallback;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import uk.ac.edina.fieldtriplite.FieldTripMap;
import uk.ac.edina.fieldtriplite.model.AssetModel;
import uk.ac.edina.fieldtriplite.model.AssetModelRepository.DownloadCallback;

public class AssetServiceTest extends ActivityInstrumentationTestCase2<FieldTripMap> {

    private static final String LOG_TAG = "AssetServiceTest";
    private static final String TEST_ASSET_ID = "56ab5e55d4d0916c09e878a4";
    private static final String TEST_NON_ASSET_ID = "00000000000000000000000";

    private AssetService assetService;

    public AssetServiceTest() {
        super(FieldTripMap.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        assetService = new AssetServiceImpl(getActivity());
    }

    @Test
    public void testGetAssetInfo() {
        final CountDownLatch latch = new CountDownLatch(1);

        // Run the download in a new thread
        new Thread(new Runnable() {
            public void run() {
                Looper.prepare();

                // The test
                assetService.getInfo(TEST_ASSET_ID, new ObjectCallback<AssetModel>() {

                    @Override
                    public void onSuccess(AssetModel asset) {
                        assertEquals(TEST_ASSET_ID, asset.getId());

                        latch.countDown();
                    }

                    @Override
                    public void onError(Throwable t) {
                        fail(t.getMessage());
                        latch.countDown();
                    }
                });
                Looper.loop();
            }
        }).start();

        try {
            latch.await(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAssetDownload() {
        final CountDownLatch latch = new CountDownLatch(1);

        // Run the download in a new thread
        new Thread(new Runnable() {
            public void run() {
                Looper.prepare();

                // The test
                assetService.downloadAsset(TEST_ASSET_ID, new DownloadCallback() {
                    @Override
                    public void onSuccess(byte[] content, String contentType) {
                        Log.d(LOG_TAG, contentType);
                        assertTrue(content.length > 0);
                        latch.countDown();
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e(LOG_TAG, t.toString());
                        fail(t.getMessage());
                        latch.countDown();
                    }
                });

                Looper.loop();
            }
        }).start();

        try {
            latch.await(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testNotExistentAssetDownload() {
        final CountDownLatch latch = new CountDownLatch(1);

        // Run the download in a new thread
        new Thread(new Runnable() {
            public void run() {
                Looper.prepare();

                assetService.downloadAsset(TEST_NON_ASSET_ID, new DownloadCallback() {
                    @Override
                    public void onSuccess(byte[] content, String contentType) {
                        Log.d(LOG_TAG, contentType);
                        fail();
                        latch.countDown();
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.d(LOG_TAG, t.toString());
                        assertTrue(t.getMessage().equals("Not Found"));
                        latch.countDown();
                    }
                });
                Looper.loop();
            }
        }).start();

        try {
            latch.await(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
