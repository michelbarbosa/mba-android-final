package com.example.michelbarbosa.trabalhoconclusao;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.michelbarbosa.trabalhoconclusao.dao.UsuarioDao;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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
        final int animationTime = 3500;
        new Handler().postDelayed(new AfterAnimation(), animationTime);
    }

    private class AfterAnimation implements Runnable {

        @Override
        public void run() {
            new Query().execute("http://www.mocky.io/v2/58b9b1740f0000b614f09d2f");
        }
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

        @Override
        protected void onPostExecute(String s) {
            if (s == null) {
                Toast.makeText(SplashScreenActivity.this, "Houve um problema ao carregar o usuário", Toast.LENGTH_LONG).show();
            }
            try {
                JSONObject json = new JSONObject(s);
                String usuario = json.getString("usuario");
                String senha = json.getString("senha");
                new UsuarioDao(SplashScreenActivity.this).save(usuario, senha);
                Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                SplashScreenActivity.this.finish();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
