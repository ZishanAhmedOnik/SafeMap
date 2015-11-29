package onikinc.safemap;

import android.content.Intent;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    public static String USER_QUERY_LATITUDE_KEY = "LAT_KEY";
    public static String USER_QUERY_LONGTITUDE_KEY = "LONG_KEY";

    private GoogleMap mMap;
    private boolean positionSet;
    private  boolean queryPostionSet;
    private LatLng UserQueryPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        positionSet = false;
    }

    @Override
    public void onResume() {
        super.onResume();

        positionSet = false;
        queryPostionSet = false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);

        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                if (!positionSet) {
                    LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());

                    if (mMap != null) {
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 17));
                    }

                    positionSet = true;
                }
            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if(mMap != null) {
                    mMap.clear();
                    mMap.addMarker(new MarkerOptions().position(latLng));
                    UserQueryPosition = latLng;

                    queryPostionSet = true;
                }
            }
        });
    }

    public void StartResultActivity(View view) {
        if(queryPostionSet) {
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra(USER_QUERY_LATITUDE_KEY, UserQueryPosition.latitude);
            intent.putExtra(USER_QUERY_LONGTITUDE_KEY, UserQueryPosition.longitude);

            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Please Select a Position", Toast.LENGTH_LONG).show();
        }
    }
}
