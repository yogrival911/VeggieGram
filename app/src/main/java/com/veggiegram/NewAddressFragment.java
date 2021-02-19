package com.veggiegram;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Looper;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.veggiegram.responses.AddAddressObject;
import com.veggiegram.responses.addaddress.AddAddressResponse;
import com.veggiegram.retrofit.RetrofitClientInstance;
import com.veggiegram.retrofit.RetrofitIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static androidx.core.content.ContextCompat.getSystemService;


public class NewAddressFragment extends Fragment {
Button saveAddress;
EditText etFirstName,etLastName,etHouse,etStreet,etPin,etLandmark,etMob;
Spinner spinnerState, spinnerLocality, spinnerCity;
boolean isAllFieldsChecked = false;
FusedLocationProviderClient mFusedLocationClient;
int PERMISSION_ID = 44;
String latitude, longitude;
Boolean fromCart;

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public NewAddressFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_address, container, false);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String user_id = sharedPreferences.getString("user_id", "");

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());

        getLastLocation();

        etFirstName = view.findViewById(R.id.etFirstName);
        etLastName = view.findViewById(R.id.etLastName);
        etHouse = view.findViewById(R.id.etHouse);
        etStreet = view.findViewById(R.id.etStreet);
        etPin = view.findViewById(R.id.etPin);
        etLandmark = view.findViewById(R.id.etLandmark);
        etMob = view.findViewById(R.id.etMob);
        spinnerState = view.findViewById(R.id.spinnerState);
        spinnerLocality = view.findViewById(R.id.spinnerLocality);
        spinnerCity = view.findViewById(R.id.spinnerCity);
        saveAddress = view.findViewById(R.id.saveAddress);

        fromCart = getActivity().getIntent().getBooleanExtra("fromCart", false);
        Log.i("yogfromcart", fromCart.toString());

        Retrofit retrofit = RetrofitClientInstance.getInstance();
        RetrofitIInterface retrofitIInterface = retrofit.create(RetrofitIInterface.class);

        saveAddress.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String firstName = etFirstName.getText().toString();
                String lastName = etLastName.getText().toString();
                String house = etHouse.getText().toString();
                String street = etStreet.getText().toString();
                String pin = etPin.getText().toString();
                String landmark = etLandmark.getText().toString();
                String mob = etMob.getText().toString();
                String state = spinnerState.getSelectedItem().toString();
                String locality = spinnerLocality.getSelectedItem().toString();
                String city = spinnerCity.getSelectedItem().toString();

                isAllFieldsChecked = CheckAllFields();
                if (isAllFieldsChecked) {
                    retrofitIInterface.addNewAddress(new AddAddressObject("", "", "",user_id,house,street,city,"punjab",state,mob,pin,firstName, lastName,landmark), user_id)
                            .enqueue(new Callback<AddAddressResponse>() {
                                @Override
                                public void onResponse(Call<AddAddressResponse> call, Response<AddAddressResponse> response) {
                                    Snackbar.make(getView(), "Address Saved", Snackbar.LENGTH_SHORT ).show();
//                                    NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.address_host_frag);
//                                    navHostFragment.getNavController().navigate(NewAddressFragmentDirections.actionNewAddressFragmentToMyAddressFragment());
                                    if(fromCart){
                                        Intent intent = new Intent(getContext(), CartActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        getActivity().startActivity(intent);
                                    }
                                    else {
                                        Intent intent = new Intent(getContext(), MyAddressActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        getActivity().startActivity(intent);
                                    }
                                }

                                @Override
                                public void onFailure(Call<AddAddressResponse> call, Throwable t) {

                                }
                            });
                }
            }
        });
        return view;
    }

    private boolean CheckAllFields() {
        if (etFirstName.length() < 4) {
            etFirstName.setError("Atleast 4 characters required");
            return false;
        }

        if (etLastName.length() < 4) {
            etLastName.setError("Atleast 4 characters required");
            return false;
        }

        if (etHouse.length() == 0) {
            etHouse.setError("House no. is required");
            return false;
        }

        if (etStreet.length() == 0) {
            etStreet.setError("Street is required");
            return false;
        }

        if (etStreet.length() == 0) {
                etStreet.setError("Street is required");
                return false;
        }

        if (etPin.length() < 6) {
            etPin.setError("Enter 6 digit pin code");
            return false;
        }

        if (etLandmark.length() == 0) {
            etLandmark.setError("Landmark is required");
            return false;
        }

        if (etMob.length() < 10) {
            etMob.setError("Enter 10 digit mobile number");
            return false;
        }

        return true;
    }


    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
//                            latitudeTextView.setText(location.getLatitude() + "");
//                            longitTextView.setText(location.getLongitude() + "");
                            Log.i("yogloc","Long: " + longitude + " Lat: " + latitude);

                            latitude = location.getLatitude()+"";
                            longitude = location.getLongitude()+"";
                            setLatitude(latitude);
                            setLongitude(longitude);
                        }
                    }
                });
            } else {
                Toast.makeText(getContext(), "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
//            latitudeTextView.setText("Latitude: " + mLastLocation.getLatitude() + "");
//            longitTextView.setText("Longitude: " + mLastLocation.getLongitude() + "");
            latitude = mLastLocation.getLatitude()+"";
            longitude = mLastLocation.getLongitude()+"";
            setLatitude(latitude);
            setLongitude(longitude);
        }
    };

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

}