package com.emprendesoft.madridshops.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.emprendesoft.madridshops.R;
import com.emprendesoft.madridshops.navigator.Navigator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.activity_main__shops_button)
    Button shopsButton;
    @BindView(R.id.activity_main__activities_button)
    Button activitiesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        shopsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MainActivity.class.getCanonicalName(), "Hello");

//                Intent i = new Intent(MainActivity.this, ShopListActivity.class);
//                startActivity(i);
                Navigator.navigateFromMainActivityToShopListActivity(MainActivity.this);
            }
        });

        activitiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MainActivity.class.getCanonicalName(), "Hello Activities");
            }
        });

//        launchInBackgroundThread();
    }

    private void launchInBackgroundThread() {

        // onPreExecute
        // ...

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() { // doInBackground
                Log.d("HILO", Thread.currentThread().getName());
                final String s = testMultithread();

                // onPostExecute (going to main thread)

                // method 1
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        shopsButton.setText(s);
//                    }
//                });

                // method 2
//                MainThread.run(new Runnable() {
//                    @Override
//                    public void run() {
//                        shopsButton.setText(s);
//                    }
//                });
            }
        });

        thread.start();
    }

    private String testMultithread() {

        final String web = "http://freegeoip.net/json/";
        String result = null;

        try {
            URL url = new URL(web);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();

            InputStream is = (InputStream) request.getContent();
            result = streamToString(is);

            Log.d("WEB", result);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    String streamToString(InputStream in) throws IOException {

        StringBuilder out = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        for (String line = br.readLine(); line != null; line = br.readLine())
            out.append(line);
        br.close();

        return out.toString();
    }
}






























