package uk.ac.edina.fieldtriplite;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

/**
 * Created by benbutchart on 21/10/15.
 */


    public class LocationServicesClient implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


        private GoogleApiClient apiClient ;
        private WebViewLocationAPI locationAPI ;
        private static final String LOG_TAG = "LocationClient" ;
        private boolean updatesPending = false ;
        private int updatesPendingInterval = 0 ;
        private boolean locationFixPending = false ;

        LocationFixListener fixListener ;
        LocationUpdateListener updateListener ;



        public LocationServicesClient(Context appContext, WebViewLocationAPI locationAPI) {


            this.apiClient = new GoogleApiClient.Builder(appContext)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();

            this.locationAPI = locationAPI ;
            this.fixListener = new LocationFixListener() ;
            this.updateListener = new LocationUpdateListener() ;

            this.apiClient.connect();
        }




        // GoogleAPIClient callback methods

        @Override
        public void onConnectionSuspended(int i) {

            Log.d(LOG_TAG, "onConnectionSuspended") ;
        }

        @Override
        public void onConnectionFailed(ConnectionResult connectionResult) {

            Log.d(LOG_TAG, "onConnectionFailed") ;
        }

        @Override
        public void onConnected(Bundle bundle) {

            // we've connected to GoogleApiClient so can now use FusedLocationAPI
            Log.d(LOG_TAG, "onConnected") ;

            // check if request for continuous loation updates are pending
            if(this.updatesPending)
            {
                this.requestLocationUpdates(this.updatesPendingInterval);
                this.updatesPending = false ;
                this.updatesPendingInterval = 0 ;
                Log.d(LOG_TAG ," pending location updates requested") ;
            }

            // check
            if(this.locationFixPending)
            {
                this.requestLocationFix(1);
                this.locationFixPending = false ;
                Log.d(LOG_TAG, " pending location fix requested") ;
            }

        }


        public void requestLocationFix(int numUpdates)
        {

            if(apiClient.isConnected() == false)
            {
                this.locationFixPending = true ;
                Log.d(LOG_TAG, "postponing location fix until apiClient connected") ;
                return ;
            }


            // request a new location fix
            LocationRequest request = LocationRequest.create();
            request.setNumUpdates(numUpdates);
            request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            LocationServices.FusedLocationApi.requestLocationUpdates(this.apiClient, request, this.fixListener);
            Log.d(LOG_TAG, "requested requestLocationFix") ;
        }


        public void requestLocationUpdates(int interval)
        {

            if(apiClient.isConnected() == false)
            {
                this.updatesPending = true ;
                this.updatesPendingInterval = interval ;
                Log.d(LOG_TAG, "postponing request location updates until apiClient connected") ;
                return ;
            }

            // request ongoing location updates
            LocationRequest request = LocationRequest.create();
            request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            request.setInterval(interval) ;
            LocationServices.FusedLocationApi.requestLocationUpdates(apiClient, request, this.updateListener);
            Log.d(LOG_TAG, "requested location updates") ;
        }


        public void removeLocationUpdates()
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(apiClient, this.updateListener) ;
        }



        public void removeGeofence(int id)
        {
            // TODO remove geofence
        }











        public class LocationUpdateListener implements LocationListener {
            @Override
            public void onLocationChanged(Location location) {
                Log.d(LOG_TAG, "LocationUpdateListener:" + location) ;
                locationAPI.onLocationUpdate(location);

            }
        }


        public class LocationFixListener implements LocationListener
        {
            @Override
            public void onLocationChanged(Location location) {

                Log.d("LocationFixListener", "onLocationChanged") ;
                if(locationAPI != null) {
                    Log.d("LocationFixListener", "set CurrentLocation" ) ;
                    locationAPI.setCurrentLocation(location) ;
                    Log.d("LocationFixListener", "onLocationFix" ) ;
                    locationAPI.onLocationFix(location);
                }
            }
        }




}
