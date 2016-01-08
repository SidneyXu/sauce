package com.bookislife.sauce.sample.future;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by SidneyXu on 2016/01/08.
 */
public class LocationHelper implements LocationListener {

    interface GetLocationCallback {
        void done(Location location);
    }

    private static final int TWO_MINUTES = 1000 * 60 * 2;

    private Context context;
    private Location currentBestLocation;
    private Handler handler;

    private GetLocationCallback getLocationCallback;

    private class LocationAdapter implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    }

    public LocationHelper(Context context) {
        this.context = context;
        handler = new Handler(Looper.getMainLooper());
    }

    public void getCurrentLocationInBackground(long timeout) {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.NO_REQUIREMENT);
        criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
        getCurrentLocationInBackground(timeout, criteria);
    }

    public void getCurrentLocationInBackground(long timeout,
                                               Criteria criteria) {
        final LocationManager locationManager = ((LocationManager) context.getSystemService(
                Context.LOCATION_SERVICE));

        List<String> enabledProviders = locationManager.getProviders(true);
        if (enabledProviders == null || enabledProviders.isEmpty()) {
            // TODO: 1/8/16 getLastKnownLocation and return
            return;
        }
        if (enabledProviders.contains(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    0, 0, this);


            Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    locationManager.removeUpdates(LocationHelper.this);
                    // TODO: 1/8/16 timeout exception
                }
            };
            timer.schedule(timerTask, timeout);
        } else if (enabledProviders.contains(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    0, 0, this);
        } else {
            // TODO: 1/8/16 getLastKnownLocation and return
        }
    }

    private void getLastKnownLocation(LocationManager locationManager) {
        Location bestLocation;
        Location gpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location networkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (gpsLocation != null && networkLocation != null) {
            if (gpsLocation.getTime() >= networkLocation.getTime()) {
                bestLocation = gpsLocation;
            } else {
                bestLocation = networkLocation;
            }
        } else {
            bestLocation = gpsLocation == null ? networkLocation : gpsLocation;
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        if (location == null) return;
        if (isBetterLocation(location, currentBestLocation)) {
            currentBestLocation = location;
        }
        LocationManager locationManager = ((LocationManager) context.getSystemService(
                Context.LOCATION_SERVICE));
        locationManager.removeUpdates(this);
        handler.post(new Runnable() {
            @Override
            public void run() {
                getLocationCallback.done(currentBestLocation);
            }
        });
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    private boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            return true;
        }
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;
        if (isSignificantlyNewer) {
            return true;
        } else if (isSignificantlyOlder) {
            return false;
        }
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;
        boolean isFromSameProvider = isSameProvider(location.getProvider(), currentBestLocation.getProvider());
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }


    private static boolean isSameProvider(final String provider1,
                                          final String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }

}
