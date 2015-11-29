package onikinc.safemap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startCheckSafetyActivity(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void startInsertRating(View view) {
        ResultActivity.UPLOAD_TO_SERVER = true;
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}
