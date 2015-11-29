package onikinc.safemap;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Zishan on 11/21/2015.
 */
public class GetFromDatabase extends Thread {
    String serverURL;
    ResultActivity parentActivity;
    JSONObject jsonObject;

    public GetFromDatabase(String x, String y, ResultActivity parentActivity) {
        serverURL = "http://www.elitefashionbd.com/app/query.php?x=" + x + "&y=" + y;
        this.parentActivity = parentActivity;
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

            jsonObject = new JSONObject(line);

            ShowToast(jsonObject.getString("status"));

        } catch (Exception ex) {
            ShowToast(ex.toString());
        }
    }

    void ShowToast(final String res) {
        parentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                parentActivity.setText(res);
            }
        });
    }
}
