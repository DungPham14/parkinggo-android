/*
 * ******************************************************************************
 *  Copyright Ⓒ 2017. All rights reserved
 *  Author HoanDC. Create on 05/04/2017.
 * ******************************************************************************
 */
package parkinggo.com.ui.main.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v13.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.realm.RealmList;
import parkinggo.com.R;
import parkinggo.com.data.model.Address;
import parkinggo.com.data.model.Parking;
import parkinggo.com.ui.base.BaseFragmentWithDialog;
import parkinggo.com.ui.main.MainActivity;
import parkinggo.com.ui.main.interfaces.OnDrawerLayoutListener;
import parkinggo.com.util.Utils;
import parkinggo.com.util.permission.PermissionUtils;

import static android.support.v4.content.PermissionChecker.checkSelfPermission;


public class HomeFragment extends BaseFragmentWithDialog implements HomeMvpView, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        ResultCallback<LocationSettingsResult>,
        OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback,
        GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    private static final int ZOOM_SIZE = 14;
    /**
     * Request code for location permission request.
     *
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private static final long INTERVAL = 1000 * 60 * 5; //5 minute
    private static final long FASTEST_INTERVAL = 1000 * 60 * 10; //10 minute
    private static final long UPDATE_LOCATION_INTERVAL = 1000 * 60; //5 minute
    private static final long UPDATE_LOCATION_DISTANCE = 500; //500 meters.
    protected LocationRequest locationRequest;
    int REQUEST_CHECK_SETTINGS = 100;
    @Inject
    HomePresenter presenter;
    OnDrawerLayoutListener onDrawerLayoutListener;
    /**
     * View
     */
    View mapView;
    @BindView(R.id.bottom_sheet)
    View bottomSheet;
    @BindView(R.id.fab)
    View fab;
    @BindView(R.id.toolbar_expand)
    Toolbar toolbar;
    @BindView(R.id.parking_title)
    TextView tvTitle;
    /**
     * Map
     */
    private GoogleMap googleMap = null;
    private GoogleApiClient mGoogleApiClient;
    private LocationManager locationManager;
    //    private Location location;
    private LatLng currentLatLng;
    private LatLng selectLatLng;
    /**
     * List marker will be added on map.
     */
    private Map<Marker, Parking> parkingMap = new HashMap<>();
    /**
     * Keeps track of the selected marker.
     */
    private Marker currentMarker;
    private Marker oldMarker;
    private boolean permissionDenied = false;
    private BottomSheetBehavior bottomSheetBehavior;

    public static HomeFragment getInstance() {
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof MainActivity) {
            try {
                onDrawerLayoutListener = (OnDrawerLayoutListener) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString()
                        + " must implement OnDrawerLayoutListener");
            }
        }
    }

    @Override
    protected boolean bindView() {
        return true;
    }

    @Override
    protected int addContextView() {
        return R.layout.fragment_home;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivityComponent().inject(this);
        presenter.initialView(this);
        initialMap();
    }

    private void initialMap() {
        SupportMapFragment mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map));
        mapFragment.getMapAsync(this);
        mapView = mapFragment.getView();
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(getActivity(), 1, this)
                .build();
        initialLocationRequest();
        initBottomSheet();
    }

    private void initialLocationRequest() {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
    }

    private void initBottomSheet() {
        fab.setVisibility(View.GONE);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_DRAGGING:
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        fab.setVisibility(View.VISIBLE);
                        toolbar.setNavigationIcon(R.color.toolbarTransparent);
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        fab.setVisibility(View.GONE);
                        toolbar.setNavigationIcon(R.color.toolbarTransparent);
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        fab.setVisibility(View.VISIBLE);
                        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onSlide(View bottomSheet, float slideOffset) {
            }
        });
        initialToolbar();
    }

    private void initialToolbar() {
        mActivity.setSupportActionBar(toolbar);
        mActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        mActivity.getSupportActionBar().setTitle("");
        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        tvTitle.setText(getString(R.string.forgot_title));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(mActivity.getSupportFragmentManager(), "dialog");
    }

    @OnClick({R.id.img_menu, R.id.fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_menu:
                Utils.hideKeyboardMachine(mActivity);
                if (onDrawerLayoutListener != null) {
                    onDrawerLayoutListener.onMenuClickListener();
                }
                break;
            case R.id.fab:
                if (selectLatLng != null && currentLatLng != null) {
                    String address = String.format("http://maps.google.com/maps?saddr=%f,%f&daddr=%f,%f",
                            currentLatLng.latitude, currentLatLng.longitude, selectLatLng.latitude, selectLatLng.longitude);
                    Uri gmmIntentUri = Uri.parse(address);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                    PackageManager packageManager = getActivity().getPackageManager();

                    if (mapIntent.resolveActivity(packageManager) != null) {
                        startActivity(mapIntent);
                    } else {
                        Log.d("Start Map", "No Intent available to handle action");
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.stopAutoManage(getActivity());
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (permissionDenied) {
            showMissingPermissionError();
            permissionDenied = false;
        }
    }

    private void initialPendingLocation() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        mGoogleApiClient,
                        builder.build()
                );
        result.setResultCallback(this);

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        initialPendingLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        // Show camera current location of device.
        if (location != null) {
            currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
            googleMap.moveCamera(CameraUpdateFactory
                    .newLatLngZoom(currentLatLng, ZOOM_SIZE));
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        currentMarker = marker;
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
            showParking(marker);
        } else {
            if (oldMarker == currentMarker) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            } else {
                showParking(marker);
            }
        }
        oldMarker = currentMarker;
        return false;
    }

    private void showParking(Marker marker) {
        Parking parking = parkingMap.get(marker);
        if (parking != null) {
            selectLatLng = new LatLng(parking.getAddress().getLatitude(), parking.getAddress().getLongitude());
            bottomSheetBehavior.setPeekHeight(400);
            toolbar.setNavigationIcon(R.color.toolbarTransparent);
            tvTitle.setText(parking.getName());
            fab.setVisibility(View.VISIBLE);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnMapClickListener(this);

        //Disable Map Toolbar:
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.getUiSettings().setZoomControlsEnabled(false);
        enableMyLocation();
    }

    private void myLocationButton() {
        if (mapView != null &&
                mapView.findViewById(Integer.parseInt("1")) != null) {
            // Get the button view
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            // and next place it, on bottom right (as Google Maps app)
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            // position on right bottom
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 30, 300);
        }
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // Access to the location has been granted to the app.
            if (googleMap != null && currentLatLng == null) {
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                myLocationButton();
                googleMap.getUiSettings().setIndoorLevelPickerEnabled(true);
                locationManager = (LocationManager) mActivity.getSystemService(Context.LOCATION_SERVICE);
                locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER, UPDATE_LOCATION_INTERVAL, UPDATE_LOCATION_DISTANCE, presenter);
                Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(
                        mGoogleApiClient);
                if (lastLocation != null) {
                    currentLatLng = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
                    searchParkingByLocation(currentLatLng);
                }
            }
        }
    }

    private void searchParkingByLocation(LatLng latLng) {
        // moving camera and adding parking list based on current location of device.
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (latLng != null) {
                    googleMap.moveCamera(CameraUpdateFactory
                            .newLatLngZoom(latLng, ZOOM_SIZE));
                    presenter.getListParkingByLocation(latLng);
                }
            }
        });
    }

    @Override
    public void makeMarker(RealmList<Parking> parkings) {
        createMarker(parkings);
    }

    void createMarker(RealmList<Parking> parkings) {
        if (mActivity == null || parkings == null || parkings.size() == 0) {
            return;
        }
        googleMap.clear();
        parkingMap.clear();
        for (Parking parking : parkings) {
            Address address = parking.getAddress();
            Marker marker = googleMap.addMarker(new MarkerOptions().position(
                    new LatLng(address.getLatitude(), address.getLongitude())).icon(
                    BitmapDescriptorFactory.defaultMarker()));
            parkingMap.put(marker, parking);
        }
    }

    @Override
    public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
        final Status status = locationSettingsResult.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                enableMyLocation();
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                try {
                    status.startResolutionForResult(mActivity, REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
                break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                // Location settings are unavailable so not possible to show any dialog now
                break;
        }
    }

    @Override
    public void onStop() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroyView();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.stopAutoManage(getActivity());
            mGoogleApiClient.disconnect();
        }
    }
}
