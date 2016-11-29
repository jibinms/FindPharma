package wear.sunshine.android.example.com.capstone_1.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;


import wear.sunshine.android.example.com.capstone_1.R;
import wear.sunshine.android.example.com.capstone_1.adapter.PharmacyAdapter;
import wear.sunshine.android.example.com.capstone_1.response.Medicine;
import wear.sunshine.android.example.com.capstone_1.response.Pharmacy;

public class MedicineDetailsActivity extends BaseActivity implements LocationListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int CALL_REQUEST = 150;
    private static final int LOCATION_REQUEST = 152;
    private static final long POLLING_FREQ = 1000 * 30;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    private static String sMapUrl = "http://maps.google.com/maps?saddr=%s,%s &daddr=%s,%s &mode=d";
    private static String sMapPackage = "com.google.android.apps.maps";
    private ImageView mProductImage;
    private TextView mDetails;
    private TextView mRate;
    private TextView mPharmacy;
    private TextView mProducer;
    private TextView mContents;
    private TextView mTiming;
    private ImageView mMapIcon;
    private String mPhNo;
    private String mLat;
    private String mLongi;

    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_details);

        initViews();
    }

    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mContents = (TextView) findViewById(R.id.contents);
        mDetails = (TextView) findViewById(R.id.details);
        mRate = (TextView) findViewById(R.id.rate);
        mPharmacy = (TextView) findViewById(R.id.pharmacyDetails);
        mProducer = (TextView) findViewById(R.id.producer);
        mTiming = (TextView) findViewById(R.id.timing);
        mMapIcon = (ImageView) findViewById(R.id.map);
        mProductImage = (ImageView) findViewById(R.id.imageProd);

        setSupportActionBar(toolbar);

        try {
            setPharmacyDetails((Pharmacy) getIntent().getParcelableExtra(PharmacyAdapter.INTENT_DATA));
        } catch (ClassCastException e) {
            setMedicineDetails((Medicine) getIntent().getParcelableExtra(PharmacyAdapter.INTENT_DATA));
        }


    }

    private void setPharmacyDetails(Pharmacy pharmacy) {

        if (pharmacy.getName() != null) {
            setTitle(pharmacy.getName());
        }

        if (pharmacy.imageUrl != null)
            Glide.with(getApplicationContext()).load(pharmacy.imageUrl).placeholder(android.R.drawable.ic_menu_crop).into(mProductImage);

        if (!TextUtils.isEmpty(pharmacy.getStartingTime()) && !TextUtils.isEmpty(pharmacy.getClosingTime())) {
            mTiming.setText(Html.fromHtml(getString(R.string.timing) + "\n <font color=\"red\">" + pharmacy.getStartingTime() + " - " + pharmacy.getClosingTime() + "</font>"));
        }
        mDetails.setText(R.string.address);

        if (!TextUtils.isEmpty(pharmacy.getStreet())) {
            mRate.setText("     " + pharmacy.getStreet());
        }
        if (!TextUtils.isEmpty(pharmacy.getArea())) {
            mPharmacy.setText("     " + pharmacy.getArea());
        }

        if (!TextUtils.isEmpty(pharmacy.getRegion())) {
            mProducer.setText("     " + pharmacy.getRegion());
        }

        if (!TextUtils.isEmpty(pharmacy.getPhone())) {
            mPhNo = pharmacy.getPhone();
        }
        if (!TextUtils.isEmpty(pharmacy.getLatitude())) {
            mLat = pharmacy.getLatitude();
        }

        if (!TextUtils.isEmpty(pharmacy.getLongitude())) {
            mLongi = pharmacy.getLongitude();
        }
    }

    public void mapViewClicked(View v) {
        checkPermissions(Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_REQUEST);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (mPhNo != null) {
            getMenuInflater().inflate(R.menu.call, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.call) {
            checkPermissions(Manifest.permission.CALL_PHONE, CALL_REQUEST);
        }

        if (item.getItemId() == R.id.share) {
            Intent intent2 = new Intent();
            intent2.setAction(Intent.ACTION_SEND);
            intent2.setType("text/plain");
            intent2.putExtra(Intent.EXTRA_TEXT, getTitle() + ": " + mPhNo);
            startActivity(Intent.createChooser(intent2, "Share via"));
        }
        return super.onOptionsItemSelected(item);
    }

    private void setMedicineDetails(Medicine medicine) {

        TextView title = (TextView) findViewById(R.id.nameT);
        if (medicine.getTradeName() != null) {
            title.setText(medicine.getTradeName());
        }
        mMapIcon.setVisibility(View.GONE);
        if (medicine.imageUrl != null)
            Glide.with(getApplicationContext()).load(medicine.imageUrl).placeholder(android.R.drawable.ic_menu_crop).into(mProductImage);

        if (!TextUtils.isEmpty(medicine.getCompany())) {
            mProducer.setText("     " + medicine.getCompany());
        }

        if (!TextUtils.isEmpty(medicine.getLocalAgent())) {
            mTiming.setText(Html.fromHtml(getString(R.string.available_pharmacy) + ": \n <font color=\"black\">" + medicine.getLocalAgent() + "</font>"));
        }
        if (!TextUtils.isEmpty(medicine.getRate())) {
            mPharmacy.setText("     " + medicine.getRate());
        }

        if (!TextUtils.isEmpty(medicine.getPackSize())) {
            mRate.setText("     " + medicine.getPackSize());
        }


    }

    /**
     * Permission check to handle marshmallow and above versions permission issue
     */
    public void checkPermissions(String permission, int reqstCode) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(this, new String[]{permission}, reqstCode);

        } else {
            int[] result = {PackageManager.PERMISSION_GRANTED};

            onRequestPermissionsResult(reqstCode, null, result);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == CALL_REQUEST && grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent mIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mPhNo));
             startActivity(mIntent);
        } else if (requestCode == LOCATION_REQUEST && grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            accessCurrentLocation();
        } else {
            Snackbar.make(findViewById(android.R.id.content), requestCode == LOCATION_REQUEST ? getString(R.string.failed_map) : getString(R.string.cant_make_call), Snackbar.LENGTH_SHORT).show();
            //  Toast.makeText(this, R.string.cant_make_call, Toast.LENGTH_SHORT).show();
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * Checking status of google api client for accessing location
     */
    private void accessCurrentLocation() {

        initApiClient();
        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
            return;
        }
        invokeLocationRequest();
    }

    /**
     * Checking gps status and location request is build here
     */
    private void invokeLocationRequest() {
        if (!isGPSEnabled()) {
            locationRequestBuilder();
        } else {
            initLocationRequest();
            LocationServices.FusedLocationApi
                    .requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    /**
     * Initializing api client for accessing user location
     */
    private void initApiClient() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API).build();
        }
    }

    /**
     * Once the loction is identified we will launch the map screen
     */
    @Override
    public void onConnected(Bundle bundle) {
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (lastLocation != null) {
            loadMapIntent(lastLocation);
            return;
        }
        invokeLocationRequest();
    }

    /**
     * If location request is canceled showing error dialog
     */
    @Override
    public void onConnectionSuspended(int i) {

        Snackbar.make(findViewById(android.R.id.content), getString(R.string.failed_map), Snackbar.LENGTH_SHORT).show();
    }

    /**
     * In case location request is failed re-requesting the location request
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, 9000);

            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            Snackbar.make(findViewById(android.R.id.content), getString(R.string.failed_map), Snackbar.LENGTH_SHORT).show();

        }
    }

    /**
     * Stopping the location request and nullifying the google client
     */

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient != null) {
            if (mGoogleApiClient.isConnected()) {
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            }
            mGoogleApiClient.disconnect();
        }
        mGoogleApiClient = null;
    }

    /**
     * Location callback. Here we will get the current location data
     *
     * @param location current user location
     */
    @Override
    public void onLocationChanged(Location location) {

        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        loadMapIntent(lastLocation);
    }

    /**
     * Launching the map application with user current location
     *
     * @param lastLocation current user location
     */
    private void loadMapIntent(Location lastLocation) {
        dismissPendingProgressDialog();
        if (lastLocation != null) {
            Uri gmmIntentUri = Uri.parse(
                    String.format(sMapUrl, lastLocation.getLatitude(), lastLocation.getLongitude(), mLat,
                            mLongi));
            Intent intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            intent.setPackage(sMapPackage);

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);

                return;
            }
        }

        Snackbar.make(findViewById(android.R.id.content), getString(R.string.failed_map), Snackbar.LENGTH_SHORT).show();
    }

    /**
     * Checking gps enabled or not
     */
    private boolean isGPSEnabled() {
        LocationManager locationManager =
                (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

    }

    /**
     * Creating location request
     * adding all the properties like frequency and priority
     */
    private void initLocationRequest() {
        if (mLocationRequest == null) {
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(POLLING_FREQ);
            mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        }
    }

    /**
     * Building the location request
     */
    private void locationRequestBuilder() {
        initLocationRequest();
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);

        builder.setAlwaysShow(true); //this is the key ingredient

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        LocationServices.FusedLocationApi
                                .requestLocationUpdates(mGoogleApiClient, mLocationRequest, MedicineDetailsActivity.this);
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                        try {

                            status.startResolutionForResult(MedicineDetailsActivity.this, LOCATION_REQUEST);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:

                        break;
                }
            }
        });
    }

    /**
     * Activity result callback methods. This method will get callback from 2 activities
     * 1. Settings
     * 2.Time-off
     * <p/>
     * requestCode will identify from which activity this method got the callback
     *
     * @param requestCode which activity request result is coming
     * @param resultCode  result of the activity
     * @param data        intent data sent from the activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {

                case LOCATION_REQUEST:

                    initLocationRequest();
                    showProgressDialog();
                    LocationServices.FusedLocationApi
                            .requestLocationUpdates(mGoogleApiClient, mLocationRequest, MedicineDetailsActivity.this);
                    break;

            }
        }
    }

}
