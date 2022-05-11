package kg.junesqo.weatherapp.ui.weather_search;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import kg.junesqo.weatherapp.R;
import kg.junesqo.weatherapp.base.BaseFragment;
import kg.junesqo.weatherapp.databinding.FragmentWeatherSearchBinding;

public class WeatherSearchFragment extends BaseFragment<FragmentWeatherSearchBinding> implements OnMapReadyCallback {

    private GoogleMap gMap;
    private LocationManager locationManager;
    private final String[] PERMS = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    public static void grantAccessed() {

    }

    @Override
    protected FragmentWeatherSearchBinding bind() {
        return FragmentWeatherSearchBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        locationManager = (LocationManager) requireContext().getSystemService(Context.LOCATION_SERVICE);
//        ActivityCompat.requestPermissions(requireActivity(), PERMS, 1);
    }

    @Override
    protected void setupViews() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        mapFragment.getMapAsync(this);
    }



    @Override
    protected void callRequests() {
    }

    @Override
    protected void setupListeners() {
        binding.searchCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.findCityET.getText() != null) {
                    String cityName = binding.findCityET.getText().toString();
                    Log.e("Find city", String.valueOf(binding.findCityET.getText()));
//                    navController.navigate(WeatherSearchFragmentDirections
//                            .actionWeatherSearchFragmentToWeatherFragment(cityName));
                } else {
                    Toast.makeText(requireContext(), "Write city name!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void setupObservers() {

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;
//        Bundle bundle = new Bundle();
//        LatLng currentLatLng = bundle.getParcelable("Location");
//        gMap.addMarker(new MarkerOptions().position(currentLatLng));
        gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                MarkerOptions options = new MarkerOptions();
                options.position(latLng);
                gMap.clear();
                gMap.addMarker(options);
                gMap.animateCamera(
                        CameraUpdateFactory.newCameraPosition(
                                CameraPosition.builder()
                                        .zoom(10f)
                                        .target(latLng)
                                        //.bearing(100)
                                        // .tilt(30f)
                                        .build()

                        ));

                gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker) {
                        navController.navigate(WeatherSearchFragmentDirections
                                .actionWeatherSearchFragmentToWeatherFragment(String.valueOf(latLng.latitude), String.valueOf(latLng.longitude)));
                        return false;
                    }
                });

            }
        });
    }

}
