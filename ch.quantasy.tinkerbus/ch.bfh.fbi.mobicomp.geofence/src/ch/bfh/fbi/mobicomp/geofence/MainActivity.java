/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ch.bfh.fbi.mobicomp.geofence;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import ch.bfh.fbi.mobicomp.geofence.GeofenceUtils.REMOVE_TYPE;
import ch.bfh.fbi.mobicomp.geofence.GeofenceUtils.REQUEST_TYPE;

import com.example.android.geofence.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.Geofence;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * UI handler for the Location Services Geofence sample app.
 * Allow input of latitude, longitude, and radius for two geofences.
 * When registering geofences, check input and then send the geofences to Location Services.
 * Also allow removing either one of or both of the geofences.
 * The menu allows you to clear the screen or delete the geofences stored in persistent memory.
 */
public class MainActivity extends FragmentActivity {
    /*
     * Use to set an expiration time for a geofence. After this amount
     * of time Location Services will stop tracking the geofence.
     * Remember to unregister a geofence when you're finished with it.
     * Otherwise, your app will use up battery. To continue monitoring
     * a geofence indefinitely, set the expiration time to
     * Geofence#NEVER_EXPIRE.
     */
    private static final long GEOFENCE_EXPIRATION_IN_HOURS = 12;
    private static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS =
            GEOFENCE_EXPIRATION_IN_HOURS * DateUtils.HOUR_IN_MILLIS;

    // Store the current request
    private REQUEST_TYPE requestType;

    // Store the current type of removal
    private REMOVE_TYPE removeType;

    // Persistent storage for geofences
    private SimpleGeofenceStore geofenceSharedPreferences;

    // Store a list of geofences to add
    List<Geofence> currentGeofences;

    // Add geofences handler
    private GeofenceRequester geofenceRequester;
    // Remove geofences handler
    private GeofenceRemover mGeofenceRemover;
    // Handle to geofence 1 latitude in the UI
    private EditText latitude1;

    // Handle to geofence 1 longitude in the UI
    private EditText longitude1;

    // Handle to geofence 1 radius in the UI
    private EditText radius1;

    // Handle to geofence 2 latitude in the UI
    private EditText latitude2;

    // Handle to geofence 2 longitude in the UI
    private EditText longitude2;

    // Handle to geofence 2 radius in the UI
    private EditText radius2;

    /*
     * Internal lightweight geofence objects for geofence 1 and 2
     */
    private SimpleGeofence geofence1;
    private SimpleGeofence geofence2;

    // decimal formats for latitude, longitude, and radius
    private DecimalFormat latLngFormat;
    private DecimalFormat radiusFormat;

    /*
     * An instance of an inner class that receives broadcasts from listeners and from the
     * IntentService that receives geofence transition events
     */
    private GeofenceSampleReceiver geofenceBroadcastReceiver;

    // An intent filter for the broadcast receiver
    private IntentFilter geofenceIntentFilter;

    // Store the list of geofences to remove
    private List<String> geofenceIdsToRemove;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the pattern for the latitude and longitude format
        String latLngPattern = getString(R.string.lat_lng_pattern);

        // Set the format for latitude and longitude
        latLngFormat = new DecimalFormat(latLngPattern);

        // Localize the format
        latLngFormat.applyLocalizedPattern(latLngFormat.toLocalizedPattern());

        // Set the pattern for the radius format
        String radiusPattern = getString(R.string.radius_pattern);

        // Set the format for the radius
        radiusFormat = new DecimalFormat(radiusPattern);

        // Localize the pattern
        radiusFormat.applyLocalizedPattern(radiusFormat.toLocalizedPattern());

        // Create a new broadcast receiver to receive updates from the listeners and service
        geofenceBroadcastReceiver = new GeofenceSampleReceiver();

        // Create an intent filter for the broadcast receiver
        geofenceIntentFilter = new IntentFilter();

        // Action for broadcast Intents that report successful addition of geofences
        geofenceIntentFilter.addAction(GeofenceUtils.ACTION_GEOFENCES_ADDED);

        // Action for broadcast Intents that report successful removal of geofences
        geofenceIntentFilter.addAction(GeofenceUtils.ACTION_GEOFENCES_REMOVED);

        // Action for broadcast Intents containing various types of geofencing errors
        geofenceIntentFilter.addAction(GeofenceUtils.ACTION_GEOFENCE_ERROR);

        // All Location Services sample apps use this category
        geofenceIntentFilter.addCategory(GeofenceUtils.CATEGORY_LOCATION_SERVICES);

        // Instantiate a new geofence storage area
        geofenceSharedPreferences = new SimpleGeofenceStore(this);

        // Instantiate the current List of geofences
        currentGeofences = new ArrayList<Geofence>();

        // Instantiate a Geofence requester
        geofenceRequester = new GeofenceRequester(this);

        // Instantiate a Geofence remover
        mGeofenceRemover = new GeofenceRemover(this);

        // Attach to the main UI
        setContentView(R.layout.activity_main);

        // Get handles to the Geofence editor fields in the UI
        latitude1 = (EditText) findViewById(R.id.value_latitude_1);
        longitude1 = (EditText) findViewById(R.id.value_longitude_1);
        radius1 = (EditText) findViewById(R.id.value_radius_1);
        latitude2 = (EditText) findViewById(R.id.value_latitude_2);
        longitude2 = (EditText) findViewById(R.id.value_longitude_2);
        radius2 = (EditText) findViewById(R.id.value_radius_2);
    }

    /*
     * Handle results returned to this Activity by other Activities started with
     * startActivityForResult(). In particular, the method onConnectionFailed() in
     * GeofenceRemover and GeofenceRequester may call startResolutionForResult() to
     * start an Activity that handles Google Play services problems. The result of this
     * call returns here, to onActivityResult.
     * calls
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // Choose what to do based on the request code
        switch (requestCode) {

            // If the request code matches the code sent in onConnectionFailed
            case GeofenceUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST :

                switch (resultCode) {
                    // If Google Play services resolved the problem
                    case Activity.RESULT_OK:

                        // If the request was to add geofences
                        if (GeofenceUtils.REQUEST_TYPE.ADD == requestType) {

                            // Toggle the request flag and send a new request
                            geofenceRequester.setInProgressFlag(false);

                            // Restart the process of adding the current geofences
                            geofenceRequester.addGeofences(currentGeofences);

                        // If the request was to remove geofences
                        } else if (GeofenceUtils.REQUEST_TYPE.REMOVE == requestType ){

                            // Toggle the removal flag and send a new removal request
                            mGeofenceRemover.setInProgressFlag(false);

                            // If the removal was by Intent
                            if (GeofenceUtils.REMOVE_TYPE.INTENT == removeType) {

                                // Restart the removal of all geofences for the PendingIntent
                                mGeofenceRemover.removeGeofencesByIntent(
                                    geofenceRequester.getRequestPendingIntent());

                            // If the removal was by a List of geofence IDs
                            } else {

                                // Restart the removal of the geofence list
                                mGeofenceRemover.removeGeofencesById(geofenceIdsToRemove);
                            }
                        }
                    break;

                    // If any other result was returned by Google Play services
                    default:

                        // Report that Google Play services was unable to resolve the problem.
                        Log.d(GeofenceUtils.APPTAG, getString(R.string.no_resolution));
                }

            // If any other request code was received
            default:
               // Report that this Activity received an unknown requestCode
               Log.d(GeofenceUtils.APPTAG,
                       getString(R.string.unknown_activity_request_code, requestCode));

               break;
        }
    }

    /*
     * Whenever the Activity resumes, reconnect the client to Location
     * Services and reload the last geofences that were set
     */
    @Override
    protected void onResume() {
        super.onResume();
        // Register the broadcast receiver to receive status updates
        LocalBroadcastManager.getInstance(this).registerReceiver(geofenceBroadcastReceiver, geofenceIntentFilter);
        /*
         * Get existing geofences from the latitude, longitude, and
         * radius values stored in SharedPreferences. If no values
         * exist, null is returned.
         */
        geofence1 = geofenceSharedPreferences.getGeofence("1");
        geofence2 = geofenceSharedPreferences.getGeofence("2");
        /*
         * If the returned geofences have values, use them to set
         * values in the UI, using the previously-defined number
         * formats.
         */
        if (geofence1 != null) {
            latitude1.setText(
                    latLngFormat.format(
                            geofence1.getLatitude()));
            longitude1.setText(
                    latLngFormat.format(
                            geofence1.getLongitude()));
            radius1.setText(
                    radiusFormat.format(
                            geofence1.getRadius()));
        }
        if (geofence2 != null) {
            latitude2.setText(
                    latLngFormat.format(
                            geofence2.getLatitude()));
            longitude2.setText(
                    latLngFormat.format(
                            geofence2.getLongitude()));
            radius2.setText(
                    radiusFormat.format(
                            geofence2.getRadius()));
        }
    }

    /*
     * Inflate the app menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;

    }
    /*
     * Respond to menu item selections
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            // Request to clear the geofence1 settings in the UI
            case R.id.menu_item_clear_geofence1:
                latitude1.setText(GeofenceUtils.EMPTY_STRING);
                longitude1.setText(GeofenceUtils.EMPTY_STRING);
                radius1.setText(GeofenceUtils.EMPTY_STRING);
                return true;

            // Request to clear the geofence2 settings in the UI
            case R.id.menu_item_clear_geofence2:
                latitude2.setText(GeofenceUtils.EMPTY_STRING);
                longitude2.setText(GeofenceUtils.EMPTY_STRING);
                radius2.setText(GeofenceUtils.EMPTY_STRING);
                return true;

            // Request to clear both geofence settings in the UI
            case R.id.menu_item_clear_geofences:
                latitude1.setText(GeofenceUtils.EMPTY_STRING);
                longitude1.setText(GeofenceUtils.EMPTY_STRING);
                radius1.setText(GeofenceUtils.EMPTY_STRING);

                latitude2.setText(GeofenceUtils.EMPTY_STRING);
                longitude2.setText(GeofenceUtils.EMPTY_STRING);
                radius2.setText(GeofenceUtils.EMPTY_STRING);
                return true;

            // Remove all geofences from storage
            case R.id.menu_item_clear_geofence_history:
                geofenceSharedPreferences.clearGeofence("1");
                geofenceSharedPreferences.clearGeofence("2");
                return true;

            // Pass through any other request
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*
     * Save the current geofence settings in SharedPreferences.
     */
    @Override
    protected void onPause() {
        super.onPause();
        geofenceSharedPreferences.setGeofence("1", geofence1);
        geofenceSharedPreferences.setGeofence("2", geofence2);
    }

    /**
     * Verify that Google Play services is available before making a request.
     *
     * @return true if Google Play services is available, otherwise false
     */
    private boolean servicesConnected() {

        // Check that Google Play services is available
        int resultCode =
                GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        // If Google Play services is available
        if (ConnectionResult.SUCCESS == resultCode) {

            // In debug mode, log the status
            Log.d(GeofenceUtils.APPTAG, getString(R.string.play_services_available));

            // Continue
            return true;

        // Google Play services was not available for some reason
        } else {

            // Display an error dialog
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, this, 0);
            if (dialog != null) {
                ErrorDialogFragment errorFragment = new ErrorDialogFragment();
                errorFragment.setDialog(dialog);
                errorFragment.show(getSupportFragmentManager(), GeofenceUtils.APPTAG);
            }
            return false;
        }
    }

    /**
     * Called when the user clicks the "Remove geofences" button
     *
     * @param view The view that triggered this callback
     */
    public void onUnregisterByPendingIntentClicked(View view) {
        /*
         * Remove all geofences set by this app. To do this, get the
         * PendingIntent that was added when the geofences were added
         * and use it as an argument to removeGeofences(). The removal
         * happens asynchronously; Location Services calls
         * onRemoveGeofencesByPendingIntentResult() (implemented in
         * the current Activity) when the removal is done
         */

        /*
         * Record the removal as remove by Intent. If a connection error occurs,
         * the app can automatically restart the removal if Google Play services
         * can fix the error
         */
        // Record the type of removal
        removeType = GeofenceUtils.REMOVE_TYPE.INTENT;

        /*
         * Check for Google Play services. Do this after
         * setting the request type. If connecting to Google Play services
         * fails, onActivityResult is eventually called, and it needs to
         * know what type of request was in progress.
         */
        if (!servicesConnected()) {

            return;
        }

        // Try to make a removal request
        try {
        /*
         * Remove the geofences represented by the currently-active PendingIntent. If the
         * PendingIntent was removed for some reason, re-create it; since it's always
         * created with FLAG_UPDATE_CURRENT, an identical PendingIntent is always created.
         */
        mGeofenceRemover.removeGeofencesByIntent(geofenceRequester.getRequestPendingIntent());

        } catch (UnsupportedOperationException e) {
            // Notify user that previous request hasn't finished.
            Toast.makeText(this, R.string.remove_geofences_already_requested_error,
                        Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Called when the user clicks the "Remove geofence 1" button
     * @param view The view that triggered this callback
     */
    public void onUnregisterGeofence1Clicked(View view) {
        /*
         * Remove the geofence by creating a List of geofences to
         * remove and sending it to Location Services. The List
         * contains the id of geofence 1 ("1").
         * The removal happens asynchronously; Location Services calls
         * onRemoveGeofencesByPendingIntentResult() (implemented in
         * the current Activity) when the removal is done.
         */

        // Create a List of 1 Geofence with the ID "1" and store it in the global list
        geofenceIdsToRemove = Collections.singletonList("1");

        /*
         * Record the removal as remove by list. If a connection error occurs,
         * the app can automatically restart the removal if Google Play services
         * can fix the error
         */
        removeType = GeofenceUtils.REMOVE_TYPE.LIST;

        /*
         * Check for Google Play services. Do this after
         * setting the request type. If connecting to Google Play services
         * fails, onActivityResult is eventually called, and it needs to
         * know what type of request was in progress.
         */
        if (!servicesConnected()) {

            return;
        }

        // Try to remove the geofence
        try {
            mGeofenceRemover.removeGeofencesById(geofenceIdsToRemove);

        // Catch errors with the provided geofence IDs
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (UnsupportedOperationException e) {
            // Notify user that previous request hasn't finished.
            Toast.makeText(this, R.string.remove_geofences_already_requested_error,
                        Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Called when the user clicks the "Remove geofence 2" button
     * @param view The view that triggered this callback
     */
    public void onUnregisterGeofence2Clicked(View view) {
        /*
         * Remove the geofence by creating a List of geofences to
         * remove and sending it to Location Services. The List
         * contains the id of geofence 2, which is "2".
         * The removal happens asynchronously; Location Services calls
         * onRemoveGeofencesByPendingIntentResult() (implemented in
         * the current Activity) when the removal is done.
         */

        /*
         * Record the removal as remove by list. If a connection error occurs,
         * the app can automatically restart the removal if Google Play services
         * can fix the error
         */
        removeType = GeofenceUtils.REMOVE_TYPE.LIST;

        // Create a List of 1 Geofence with the ID "2" and store it in the global list
        geofenceIdsToRemove = Collections.singletonList("2");

        /*
         * Check for Google Play services. Do this after
         * setting the request type. If connecting to Google Play services
         * fails, onActivityResult is eventually called, and it needs to
         * know what type of request was in progress.
         */
        if (!servicesConnected()) {

            return;
        }

        // Try to remove the geofence
        try {
            mGeofenceRemover.removeGeofencesById(geofenceIdsToRemove);

        // Catch errors with the provided geofence IDs
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (UnsupportedOperationException e) {
            // Notify user that previous request hasn't finished.
            Toast.makeText(this, R.string.remove_geofences_already_requested_error,
                        Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Called when the user clicks the "Register geofences" button.
     * Get the geofence parameters for each geofence and add them to
     * a List. Create the PendingIntent containing an Intent that
     * Location Services sends to this app's broadcast receiver when
     * Location Services detects a geofence transition. Send the List
     * and the PendingIntent to Location Services.
     */
    public void onRegisterClicked(View view) {

        /*
         * Record the request as an ADD. If a connection error occurs,
         * the app can automatically restart the add request if Google Play services
         * can fix the error
         */
        requestType = GeofenceUtils.REQUEST_TYPE.ADD;

        /*
         * Check for Google Play services. Do this after
         * setting the request type. If connecting to Google Play services
         * fails, onActivityResult is eventually called, and it needs to
         * know what type of request was in progress.
         */
        if (!servicesConnected()) {

            return;
        }

        /*
         * Check that the input fields have values and that the values are with the
         * permitted range
         */
        if (!checkInputFields()) {
            return;
        }

        /*
         * Create a version of geofence 1 that is "flattened" into individual fields. This
         * allows it to be stored in SharedPreferences.
         */
        geofence1 = new SimpleGeofence(
            "1",
            // Get latitude, longitude, and radius from the UI
            Double.valueOf(latitude1.getText().toString()),
            Double.valueOf(longitude1.getText().toString()),
            Float.valueOf(radius1.getText().toString()),
            // Set the expiration time
            GEOFENCE_EXPIRATION_IN_MILLISECONDS,
            // Only detect entry transitions
            Geofence.GEOFENCE_TRANSITION_ENTER);

        // Store this flat version in SharedPreferences
        geofenceSharedPreferences.setGeofence("1", geofence1);

        /*
         * Create a version of geofence 2 that is "flattened" into individual fields. This
         * allows it to be stored in SharedPreferences.
         */
        geofence2 = new SimpleGeofence(
            "2",
            // Get latitude, longitude, and radius from the UI
            Double.valueOf(latitude2.getText().toString()),
            Double.valueOf(longitude2.getText().toString()),
            Float.valueOf(radius2.getText().toString()),
            // Set the expiration time
            GEOFENCE_EXPIRATION_IN_MILLISECONDS,
            // Detect both entry and exit transitions
            Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT
            );

        // Store this flat version in SharedPreferences
        geofenceSharedPreferences.setGeofence("2", geofence2);

        /*
         * Add Geofence objects to a List. toGeofence()
         * creates a Location Services Geofence object from a
         * flat object
         */
        currentGeofences.add(geofence1.toGeofence());
        currentGeofences.add(geofence2.toGeofence());

        // Start the request. Fail if there's already a request in progress
        try {
            // Try to add geofences
            geofenceRequester.addGeofences(currentGeofences);
        } catch (UnsupportedOperationException e) {
            // Notify user that previous request hasn't finished.
            Toast.makeText(this, R.string.add_geofences_already_requested_error,
                        Toast.LENGTH_LONG).show();
        }
    }
    /**
     * Check all the input values and flag those that are incorrect
     * @return true if all the widget values are correct; otherwise false
     */
    private boolean checkInputFields() {
        // Start with the input validity flag set to true
        boolean inputOK = true;

        /*
         * Latitude, longitude, and radius values can't be empty. If they are, highlight the input
         * field in red and put a Toast message in the UI. Otherwise set the input field highlight
         * to black, ensuring that a field that was formerly wrong is reset.
         */
        if (TextUtils.isEmpty(latitude1.getText())) {
            latitude1.setBackgroundColor(Color.RED);
            Toast.makeText(this, R.string.geofence_input_error_missing, Toast.LENGTH_LONG).show();

            // Set the validity to "invalid" (false)
            inputOK = false;
        } else {

            latitude1.setBackgroundColor(Color.BLACK);
        }

        if (TextUtils.isEmpty(longitude1.getText())) {
            longitude1.setBackgroundColor(Color.RED);
            Toast.makeText(this, R.string.geofence_input_error_missing, Toast.LENGTH_LONG).show();

            // Set the validity to "invalid" (false)
            inputOK = false;
        } else {

            longitude1.setBackgroundColor(Color.BLACK);
        }
        if (TextUtils.isEmpty(radius1.getText())) {
            radius1.setBackgroundColor(Color.RED);
            Toast.makeText(this, R.string.geofence_input_error_missing, Toast.LENGTH_LONG).show();

            // Set the validity to "invalid" (false)
            inputOK = false;
        } else {

            radius1.setBackgroundColor(Color.BLACK);
        }

        if (TextUtils.isEmpty(latitude2.getText())) {
            latitude2.setBackgroundColor(Color.RED);
            Toast.makeText(this, R.string.geofence_input_error_missing, Toast.LENGTH_LONG).show();

            // Set the validity to "invalid" (false)
            inputOK = false;
        } else {

            latitude2.setBackgroundColor(Color.BLACK);
        }
        if (TextUtils.isEmpty(longitude2.getText())) {
            longitude2.setBackgroundColor(Color.RED);
            Toast.makeText(this, R.string.geofence_input_error_missing, Toast.LENGTH_LONG).show();

            // Set the validity to "invalid" (false)
            inputOK = false;
        } else {

            longitude2.setBackgroundColor(Color.BLACK);
        }
        if (TextUtils.isEmpty(radius2.getText())) {
            radius2.setBackgroundColor(Color.RED);
            Toast.makeText(this, R.string.geofence_input_error_missing, Toast.LENGTH_LONG).show();

            // Set the validity to "invalid" (false)
            inputOK = false;
        } else {

            radius2.setBackgroundColor(Color.BLACK);
        }

        /*
         * If all the input fields have been entered, test to ensure that their values are within
         * the acceptable range. The tests can't be performed until it's confirmed that there are
         * actual values in the fields.
         */
        if (inputOK) {

            /*
             * Get values from the latitude, longitude, and radius fields.
             */
            double lat1 = Double.valueOf(latitude1.getText().toString());
            double lng1 = Double.valueOf(longitude1.getText().toString());
            double lat2 = Double.valueOf(latitude1.getText().toString());
            double lng2 = Double.valueOf(longitude1.getText().toString());
            float rd1 = Float.valueOf(radius1.getText().toString());
            float rd2 = Float.valueOf(radius2.getText().toString());

            /*
             * Test latitude and longitude for minimum and maximum values. Highlight incorrect
             * values and set a Toast in the UI.
             */

            if (lat1 > GeofenceUtils.MAX_LATITUDE || lat1 < GeofenceUtils.MIN_LATITUDE) {
                latitude1.setBackgroundColor(Color.RED);
                Toast.makeText(
                        this,
                        R.string.geofence_input_error_latitude_invalid,
                        Toast.LENGTH_LONG).show();

                // Set the validity to "invalid" (false)
                inputOK = false;
            } else {

                latitude1.setBackgroundColor(Color.BLACK);
            }

            if ((lng1 > GeofenceUtils.MAX_LONGITUDE) || (lng1 < GeofenceUtils.MIN_LONGITUDE)) {
                longitude1.setBackgroundColor(Color.RED);
                Toast.makeText(
                        this,
                        R.string.geofence_input_error_longitude_invalid,
                        Toast.LENGTH_LONG).show();

                // Set the validity to "invalid" (false)
                inputOK = false;
            } else {

                longitude1.setBackgroundColor(Color.BLACK);
            }

            if (lat2 > GeofenceUtils.MAX_LATITUDE || lat2 < GeofenceUtils.MIN_LATITUDE) {
                latitude2.setBackgroundColor(Color.RED);
                Toast.makeText(
                        this,
                        R.string.geofence_input_error_latitude_invalid,
                        Toast.LENGTH_LONG).show();

                // Set the validity to "invalid" (false)
                inputOK = false;
            } else {

                latitude2.setBackgroundColor(Color.BLACK);
            }

            if ((lng2 > GeofenceUtils.MAX_LONGITUDE) || (lng2 < GeofenceUtils.MIN_LONGITUDE)) {
                longitude2.setBackgroundColor(Color.RED);
                Toast.makeText(
                        this,
                        R.string.geofence_input_error_longitude_invalid,
                        Toast.LENGTH_LONG).show();

                // Set the validity to "invalid" (false)
                inputOK = false;
            } else {

                longitude2.setBackgroundColor(Color.BLACK);
            }
            if (rd1 < GeofenceUtils.MIN_RADIUS) {
                radius1.setBackgroundColor(Color.RED);
                Toast.makeText(
                        this,
                        R.string.geofence_input_error_radius_invalid,
                        Toast.LENGTH_LONG).show();

                // Set the validity to "invalid" (false)
                inputOK = false;
            } else {

                radius1.setBackgroundColor(Color.BLACK);
            }
            if (rd2 < GeofenceUtils.MIN_RADIUS) {
                radius2.setBackgroundColor(Color.RED);
                Toast.makeText(
                        this,
                        R.string.geofence_input_error_radius_invalid,
                        Toast.LENGTH_LONG).show();

                // Set the validity to "invalid" (false)
                inputOK = false;
            } else {

                radius2.setBackgroundColor(Color.BLACK);
            }
        }

        // If everything passes, the validity flag will still be true, otherwise it will be false.
        return inputOK;
    }

    /**
     * Define a Broadcast receiver that receives updates from connection listeners and
     * the geofence transition service.
     */
    public class GeofenceSampleReceiver extends BroadcastReceiver {
        /*
         * Define the required method for broadcast receivers
         * This method is invoked when a broadcast Intent triggers the receiver
         */
        @Override
        public void onReceive(Context context, Intent intent) {

            // Check the action code and determine what to do
            String action = intent.getAction();

            // Intent contains information about errors in adding or removing geofences
            if (TextUtils.equals(action, GeofenceUtils.ACTION_GEOFENCE_ERROR)) {

                handleGeofenceError(context, intent);

            // Intent contains information about successful addition or removal of geofences
            } else if (
                    TextUtils.equals(action, GeofenceUtils.ACTION_GEOFENCES_ADDED)
                    ||
                    TextUtils.equals(action, GeofenceUtils.ACTION_GEOFENCES_REMOVED)) {

                handleGeofenceStatus(context, intent);

            // Intent contains information about a geofence transition
            } else if (TextUtils.equals(action, GeofenceUtils.ACTION_GEOFENCE_TRANSITION)) {

                handleGeofenceTransition(context, intent);

            // The Intent contained an invalid action
            } else {
                Log.e(GeofenceUtils.APPTAG, getString(R.string.invalid_action_detail, action));
                Toast.makeText(context, R.string.invalid_action, Toast.LENGTH_LONG).show();
            }
        }

        /**
         * If you want to display a UI message about adding or removing geofences, put it here.
         *
         * @param context A Context for this component
         * @param intent The received broadcast Intent
         */
        private void handleGeofenceStatus(Context context, Intent intent) {

        }

        /**
         * Report geofence transitions to the UI
         *
         * @param context A Context for this component
         * @param intent The Intent containing the transition
         */
        private void handleGeofenceTransition(Context context, Intent intent) {
            /*
             * If you want to change the UI when a transition occurs, put the code
             * here. The current design of the app uses a notification to inform the
             * user that a transition has occurred.
             */
        }

        /**
         * Report addition or removal errors to the UI, using a Toast
         *
         * @param intent A broadcast Intent sent by ReceiveTransitionsIntentService
         */
        private void handleGeofenceError(Context context, Intent intent) {
            String msg = intent.getStringExtra(GeofenceUtils.EXTRA_GEOFENCE_STATUS);
            Log.e(GeofenceUtils.APPTAG, msg);
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }
    }
    /**
     * Define a DialogFragment to display the error dialog generated in
     * showErrorDialog.
     */
    public static class ErrorDialogFragment extends DialogFragment {

        // Global field to contain the error dialog
        private Dialog mDialog;

        /**
         * Default constructor. Sets the dialog field to null
         */
        public ErrorDialogFragment() {
            super();
            mDialog = null;
        }

        /**
         * Set the dialog to display
         *
         * @param dialog An error dialog
         */
        public void setDialog(Dialog dialog) {
            mDialog = dialog;
        }

        /*
         * This method must return a Dialog to the DialogFragment.
         */
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return mDialog;
        }
    }
}
