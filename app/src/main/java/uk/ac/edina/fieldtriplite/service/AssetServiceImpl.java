package uk.ac.edina.fieldtriplite.service;

import android.content.Context;
import android.os.Looper;

import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.loopback.callbacks.ObjectCallback;

import uk.ac.edina.fieldtriplite.BuildConfig;
import uk.ac.edina.fieldtriplite.model.AssetModel;
import uk.ac.edina.fieldtriplite.model.AssetModelRepository;
import uk.ac.edina.fieldtriplite.model.AssetModelRepository.DownloadCallback;

public class AssetServiceImpl extends AssetServiceBase {

    private static final String url = BuildConfig.API_URL;

    public AssetServiceImpl(Context context) {
        super(context);
    }

    @Override
    public void downloadAsset(final String assetId, final DownloadCallback callback) {
        RestAdapter api = new RestAdapter(context, url);
        final AssetModelRepository repository = api.createRepository(AssetModelRepository.class);
        repository.downloadAsset(assetId, callback);
    }

    @Override
    public void getInfo(final String assetId, final ObjectCallback<AssetModel> callback) {
        RestAdapter api = new RestAdapter(context, url);
        final AssetModelRepository repository = api.createRepository(AssetModelRepository.class);
        repository.findById(assetId, callback);
    }
}
