package onikinc.safemap;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ResultActivity extends FragmentActivity implements OnMapReadyCallback {
    LatLng UserQueryPosition;
    TextView testTextView;
    Button operationButton;
    RatingBar ratingBar;

    static boolean UPLOAD_TO_SERVER = false;

    double lat;
    double lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.ResultMap);
        mapFragment.getMapAsync(this);

        testTextView = (TextView) findViewById(R.id.coordTextView);
        operationButton = (Button) findViewById(R.id.actionButton);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        lat = this.getIntent().getDoubleExtra(MapsActivity.USER_QUERY_LATITUDE_KEY, 0);
        lng = this.getIntent().getDoubleExtra(MapsActivity.USER_QUERY_LONGTITUDE_KEY, 0);

        UserQueryPosition = new LatLng(lat, lng);

        testTextView.setText(UserQueryPosition.latitude + " " + UserQueryPosition.longitude);

        if(UPLOAD_TO_SERVER) {
            operationButton.setText("UPLOAD");
        }
        else {
            operationButton.setText("GET RATING");
            ratingBar.setIsIndicator(true);
            ratingBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(googleMap != null) {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(UserQueryPosition, 17));
            googleMap.addMarker(new MarkerOptions().position(UserQueryPosition));
        }
    }

    public void CommunicateWithServer(View view) {
        if(UPLOAD_TO_SERVER) {
            FeedDatabase feedDatabase = new FeedDatabase(lat + "", lng + "", ratingBar.getRating() + "", this);
            feedDatabase.start();
        }
        else {
            GetFromDatabase getFromDatabase = new GetFromDatabase(lat + "", lng + "", this);
            getFromDatabase.start();
        }
    }

    public void setText(String res) {
        testTextView.setText(res);

        float rating = Float.parseFloat(res);
        if(rating != -1) {
            ratingBar.setRating(rating);
            ratingBar.setVisibility(View.VISIBLE);
        }
        else {
            testTextView.setText("No Record Found");
        }
    }
}
