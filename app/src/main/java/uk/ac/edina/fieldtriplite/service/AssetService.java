package uk.ac.edina.fieldtriplite.service;

import com.strongloop.android.loopback.callbacks.ObjectCallback;

import uk.ac.edina.fieldtriplite.model.AssetModel;
import uk.ac.edina.fieldtriplite.model.AssetModelRepository;

public interface AssetService {
    void downloadAsset(String assetId, AssetModelRepository.DownloadCallback callback);
    void getInfo(String assetId, ObjectCallback<AssetModel> callback);
}
