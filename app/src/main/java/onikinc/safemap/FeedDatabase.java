package onikinc.safemap;

import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Zishan on 11/21/2015.
 */
public class FeedDatabase extends Thread {
    String serverURL;
    ResultActivity parentAactivity;

    public FeedDatabase(String x, String y, String rating, ResultActivity parentAactivity) {
        serverURL = "http://www.elitefashionbd.com/app/feedme.php?x=" + x + "&y=" + y + "&status=" + rating;
        this.parentAactivity = parentAactivity;
    }

    @Override
    public void run() {
        try {
            URL url = new URL(serverURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");

            String serverOutput;
            String line = "";
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            while ((serverOutput = br.readLine()) != null) {
                line += serverOutput;
            }

            ShowToast(line);
        } catch (Exception ex) {
            ShowToast(ex.toString());
        }
    }

    private void ShowToast(final String msg) {
        parentAactivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(parentAactivity.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        });
    }
}
