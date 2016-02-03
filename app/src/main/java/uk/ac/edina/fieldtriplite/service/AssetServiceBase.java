package uk.ac.edina.fieldtriplite.service;

import android.content.Context;


abstract class AssetServiceBase implements AssetService{
    protected final Context context;

    public AssetServiceBase(Context context) {
        this.context = context;
    }
}
