package uk.ac.edina.fieldtriplite.model;

import com.google.common.collect.ImmutableMap;
import com.strongloop.android.loopback.ModelRepository;
import com.strongloop.android.remoting.adapters.Adapter;
import com.strongloop.android.remoting.adapters.RestContract;
import com.strongloop.android.remoting.adapters.RestContractItem;

public class AssetModelRepository extends ModelRepository<AssetModel> {

    public AssetModelRepository() {
        super("asset", "assets", AssetModel.class);
    }

    public RestContract createContract() {
        RestContract contract = super.createContract();

        String className = getClassName();

        contract.addItem(new RestContractItem("/" + getNameForRestUrl() + "/download/:id", "GET"),
                className + ".downloadAsset");
        return contract;
    }


    public interface DownloadCallback {
        void onSuccess(byte[] content, String contentType);
        void onError(Throwable t);
    }

    public void downloadAsset(String assetId, final DownloadCallback callback) {
        invokeStaticMethod(
                "downloadAsset",
                ImmutableMap.of("id", assetId),
                new Adapter.BinaryCallback() {
                    @Override
                    public void onSuccess(byte[] content, String contentType) {
                        callback.onSuccess(content, contentType);
                    }

                    @Override
                    public void onError(Throwable t) {
                        callback.onError(t);
                    }
                }
        );
    }
}
