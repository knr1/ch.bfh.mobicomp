package ch.bfh.fbi.mobicomp.geofence;

import com.example.android.geofence.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationStatusCodes;
import com.google.android.gms.location.LocationClient.OnRemoveGeofencesResultListener;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

/**
 * Class for connecting to Location Services and removing geofences.
 * <p>
 * <b>
 * Note: Clients must ensure that Google Play services is available before removing geofences.
 * </b> Use GooglePlayServicesUtil.isGooglePlayServicesAvailable() to check.
 * <p>
 * To use a GeofenceRemover, instantiate it, then call either RemoveGeofencesById() or
 * RemoveGeofencesByIntent(). Everything else is done automatically.
 *
 */
public class GeofenceRemover implements
            ConnectionCallbacks,
            OnConnectionFailedListener,
            OnRemoveGeofencesResultListener {

    // Storage for a context from the calling client
    private Context context;

    // Stores the current list of geofences
    private List<String> currentGeofenceIds;

    // Stores the current instantiation of the location client
    private LocationClient locationClient;

    // The PendingIntent sent in removeGeofencesByIntent
    private PendingIntent currentIntent;

    /*
     *  Record the type of removal. This allows continueRemoveGeofences to call the appropriate
     *  removal request method.
     */
    private GeofenceUtils.REMOVE_TYPE requestType;

    /*
     * Flag that indicates whether an add or remove request is underway. Check this
     * flag before attempting to start a new request.
     */
    private boolean isInProgress;

    /**
     * Construct a GeofenceRemover for the current Context
     *
     * @param context A valid Context
     */
    public GeofenceRemover(Context context) {
        // Save the context
        this.context = context;

        // Initialize the globals to null
        currentGeofenceIds = null;
        locationClient = null;
        isInProgress = false;
    }

    /**
     * Set the "in progress" flag from a caller. This allows callers to re-set a
     * request that failed but was later fixed.
     *
     * @param flag Turn the in progress flag on or off.
     */
    public void setInProgressFlag(boolean flag) {
        // Set the "In Progress" flag.
        isInProgress = flag;
    }

    /**
     * Get the current in progress status.
     *
     * @return The current value of the in progress flag.
     */
    public boolean getInProgressFlag() {
        return isInProgress;
    }

    /**
     * Remove the geofences in a list of geofence IDs. To remove all current geofences associated
     * with a request, you can also call removeGeofencesByIntent.
     * <p>
     * <b>Note: The List must contain at least one ID, otherwise an Exception is thrown</b>
     *
     * @param geofenceIds A List of geofence IDs
     */
    public void removeGeofencesById(List<String> geofenceIds) throws
        IllegalArgumentException, UnsupportedOperationException {
        // If the List is empty or null, throw an error immediately
        if ((null == geofenceIds) || (geofenceIds.size() == 0)) {
            throw new IllegalArgumentException();

        // Set the request type, store the List, and request a location client connection.
        } else {

            // If a removal request is not already in progress, continue
            if (!isInProgress) {
                requestType = GeofenceUtils.REMOVE_TYPE.LIST;
                currentGeofenceIds = geofenceIds;
                requestConnection();

            // If a removal request is in progress, throw an exception
            } else {
                throw new UnsupportedOperationException();
            }
        }
    }

    /**
     * Remove the geofences associated with a PendIntent. The PendingIntent is the one used
     * in the request to add the geofences; all geofences in that request are removed. To remove
     * a subset of those geofences, call removeGeofencesById().
     *
     * @param requestIntent The PendingIntent used to request the geofences
     */
    public void removeGeofencesByIntent(PendingIntent requestIntent) {

        // If a removal request is not in progress, continue
        if (!isInProgress) {
            // Set the request type, store the List, and request a location client connection.
            requestType = GeofenceUtils.REMOVE_TYPE.INTENT;
            currentIntent = requestIntent;
            requestConnection();

        // If a removal request is in progress, throw an exception
        } else {

            throw new UnsupportedOperationException();
        }
    }

    /**
     * Once the connection is available, send a request to remove the Geofences. The method
     * signature used depends on which type of remove request was originally received.
     */
    private void continueRemoveGeofences() {
        switch (requestType) {

            // If removeGeofencesByIntent was called
            case INTENT :
                locationClient.removeGeofences(currentIntent, this);
                break;

            // If removeGeofencesById was called
            case LIST :
                locationClient.removeGeofences(currentGeofenceIds, this);
                break;
        }
    }

    /**
     * Request a connection to Location Services. This call returns immediately,
     * but the request is not complete until onConnected() or onConnectionFailure() is called.
     */
    private void requestConnection() {
        getLocationClient().connect();
    }

    /**
     * Get the current location client, or create a new one if necessary.
     *
     * @return A LocationClient object
     */
    private GooglePlayServicesClient getLocationClient() {
        if (locationClient == null) {

            locationClient = new LocationClient(context, this, this);
        }
        return locationClient;
    }

    /**
     * When the request to remove geofences by PendingIntent returns, handle the result.
     *
     * @param statusCode the code returned by Location Services
     * @param requestIntent The Intent used to request the removal.
     */
    @Override
    public void onRemoveGeofencesByPendingIntentResult(int statusCode,
            PendingIntent requestIntent) {

        // Create a broadcast Intent that notifies other components of success or failure
        Intent broadcastIntent = new Intent();

        // If removing the geofences was successful
        if (statusCode == LocationStatusCodes.SUCCESS) {

            // In debug mode, log the result
            Log.d(GeofenceUtils.APPTAG,
                    context.getString(R.string.remove_geofences_intent_success));

            // Set the action and add the result message
            broadcastIntent.setAction(GeofenceUtils.ACTION_GEOFENCES_REMOVED);
            broadcastIntent.putExtra(GeofenceUtils.EXTRA_GEOFENCE_STATUS,
                    context.getString(R.string.remove_geofences_intent_success));

        // If removing the geocodes failed
        } else {

            // Always log the error
            Log.e(GeofenceUtils.APPTAG,
                    context.getString(R.string.remove_geofences_intent_failure, statusCode));

            // Set the action and add the result message
            broadcastIntent.setAction(GeofenceUtils.ACTION_GEOFENCE_ERROR);
            broadcastIntent.putExtra(GeofenceUtils.EXTRA_GEOFENCE_STATUS,
                    context.getString(R.string.remove_geofences_intent_failure, statusCode));
        }

        // Broadcast the Intent to all components in this app
        LocalBroadcastManager.getInstance(context).sendBroadcast(broadcastIntent);

        // Disconnect the location client
        requestDisconnection();
    }

    /**
     * When the request to remove geofences by IDs returns, handle the result.
     *
     * @param statusCode The code returned by Location Services
     * @param geofenceRequestIds The IDs removed
     */
    @Override
    public void onRemoveGeofencesByRequestIdsResult(int statusCode, String[] geofenceRequestIds) {

        // Create a broadcast Intent that notifies other components of success or failure
        Intent broadcastIntent = new Intent();

        // Temp storage for messages
        String msg;

        // If removing the geocodes was successful
        if (LocationStatusCodes.SUCCESS == statusCode) {

            // Create a message containing all the geofence IDs removed.
            msg = context.getString(R.string.remove_geofences_id_success,
                    Arrays.toString(geofenceRequestIds));

            // In debug mode, log the result
            Log.d(GeofenceUtils.APPTAG, msg);

            // Create an Intent to broadcast to the app
            broadcastIntent.setAction(GeofenceUtils.ACTION_GEOFENCES_REMOVED)
                           .addCategory(GeofenceUtils.CATEGORY_LOCATION_SERVICES)
                           .putExtra(GeofenceUtils.EXTRA_GEOFENCE_STATUS, msg);

        } else {
        // If removing the geocodes failed

            /*
             * Create a message containing the error code and the list
             * of geofence IDs you tried to remove
             */
            msg = context.getString(
                    R.string.remove_geofences_id_failure,
                    statusCode,
                    Arrays.toString(geofenceRequestIds)
            );

            // Log an error
            Log.e(GeofenceUtils.APPTAG, msg);

            // Create an Intent to broadcast to the app
            broadcastIntent.setAction(GeofenceUtils.ACTION_GEOFENCE_ERROR)
                           .addCategory(GeofenceUtils.CATEGORY_LOCATION_SERVICES)
                           .putExtra(GeofenceUtils.EXTRA_GEOFENCE_STATUS, msg);
        }

        // Broadcast whichever result occurred
        LocalBroadcastManager.getInstance(context).sendBroadcast(broadcastIntent);

        // Disconnect the location client
        requestDisconnection();
    }

    /**
     * Get a location client and disconnect from Location Services
     */
    private void requestDisconnection() {

        // A request is no longer in progress
        isInProgress = false;

        getLocationClient().disconnect();
        /*
         * If the request was done by PendingIntent, cancel the Intent. This prevents problems if
         * the client gets disconnected before the disconnection request finishes; the location
         * updates will still be cancelled.
         */
        if (requestType == GeofenceUtils.REMOVE_TYPE.INTENT) {
            currentIntent.cancel();
        }

    }

    /*
     * Called by Location Services once the location client is connected.
     *
     * Continue by removing the requested geofences.
     */
    @Override
    public void onConnected(Bundle arg0) {
        // If debugging, log the connection
        Log.d(GeofenceUtils.APPTAG, context.getString(R.string.connected));

        // Continue the request to remove the geofences
        continueRemoveGeofences();
    }

    /*
     * Called by Location Services if the connection is lost.
     */
    @Override
    public void onDisconnected() {

        // A request is no longer in progress
        isInProgress = false;

        // In debug mode, log the disconnection
        Log.d(GeofenceUtils.APPTAG, context.getString(R.string.disconnected));

        // Destroy the current location client
        locationClient = null;
    }

    /*
     * Implementation of OnConnectionFailedListener.onConnectionFailed
     * If a connection or disconnection request fails, report the error
     * connectionResult is passed in from Location Services
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        // A request is no longer in progress
        isInProgress = false;

        /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {

            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult((Activity) context,
                    GeofenceUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST);

            /*
             * Thrown if Google Play services canceled the original
             * PendingIntent
             */
            } catch (SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }

        /*
         * If no resolution is available, put the error code in
         * an error Intent and broadcast it back to the main Activity.
         * The Activity then displays an error dialog.
         * is out of date.
         */
        } else {

            Intent errorBroadcastIntent = new Intent(GeofenceUtils.ACTION_CONNECTION_ERROR);
            errorBroadcastIntent.addCategory(GeofenceUtils.CATEGORY_LOCATION_SERVICES)
                                .putExtra(GeofenceUtils.EXTRA_CONNECTION_ERROR_CODE,
                                        connectionResult.getErrorCode());
            LocalBroadcastManager.getInstance(context).sendBroadcast(errorBroadcastIntent);
        }
    }
}
