package com.example.michelbarbosa.trabalhoconclusao;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animacao_splash);
        animation.reset();
        ImageView logo = (ImageView) findViewById(R.id.splash);
        logo.clearAnimation();
        logo.startAnimation(animation);
    }

    private class Query extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(1_000);
                conn.setConnectTimeout(1_000);
                conn.setRequestMethod("GET");
                conn.setDoOutput(true);
                if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return null;
                }
                InputStream is = conn.getInputStream();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = buffer.readLine()) != null) {
                    result.append(line);
                }
                conn.disconnect();
                return result.toString();
            } catch (java.io.IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
