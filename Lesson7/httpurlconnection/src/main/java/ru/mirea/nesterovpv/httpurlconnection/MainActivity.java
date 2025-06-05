package ru.mirea.nesterovpv.httpurlconnection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView tvIp, tvCity, tvRegion, tvLatitude, tvLongitude, tvWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvIp = findViewById(R.id.tvIp);
        tvCity = findViewById(R.id.tvCity);
        tvRegion = findViewById(R.id.tvRegion);
        tvLatitude = findViewById(R.id.tvLatitude);
        tvLongitude = findViewById(R.id.tvLongitude);
        tvWeather = findViewById(R.id.tvWeather);
    }

    public void onClick(View view) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = null;
        if (connectivityManager != null) {
            networkinfo = connectivityManager.getActiveNetworkInfo();
        }

        if (networkinfo != null && networkinfo.isConnected()) {
            new DownloadPageTask().execute("https://ipinfo.io/json");
        } else {
            Toast.makeText(this, "Нет интернета", Toast.LENGTH_SHORT).show();
        }
    }

    private class DownloadPageTask extends AsyncTask<String, Void, String[]> {
        @Override
        protected String[] doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null)
                    response.append(line);
                reader.close();

                JSONObject json = new JSONObject(response.toString());
                String ip = json.getString("ip");
                String city = json.getString("city");
                String region = json.getString("region");
                String loc = json.getString("loc");
                String[] coords = loc.split(",");
                String lat = coords[0];
                String lon = coords[1];

                // Получаем погоду
                URL weatherUrl = new URL("https://api.open-meteo.com/v1/forecast?latitude=" + lat + "&longitude=" + lon + "&current_weather=true");
                HttpURLConnection weatherConnection = (HttpURLConnection) weatherUrl.openConnection();
                weatherConnection.setRequestMethod("GET");
                BufferedReader weatherReader = new BufferedReader(
                        new InputStreamReader(weatherConnection.getInputStream()));
                StringBuilder weatherResponse = new StringBuilder();
                while ((line = weatherReader.readLine()) != null)
                    weatherResponse.append(line);
                weatherReader.close();

                JSONObject weatherJson = new JSONObject(weatherResponse.toString());
                JSONObject currentWeather = weatherJson.getJSONObject("current_weather");
                String temperature = currentWeather.getString("temperature");
                String windspeed = currentWeather.getString("windspeed");

                return new String[]{ip, city, region, lat, lon, temperature, windspeed};

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] result) {
            if (result != null) {
                tvIp.setText("IP: " + result[0]);
                tvCity.setText("Город: " + result[1]);
                tvRegion.setText("Регион: " + result[2]);
                tvLatitude.setText("Широта: " + result[3]);
                tvLongitude.setText("Долгота: " + result[4]);
                tvWeather.setText("Температура: " + result[5] + "°C\nСкорость ветра: " + result[6] + " км/ч");
            } else {
                Toast.makeText(MainActivity.this, "Ошибка при загрузке данных", Toast.LENGTH_SHORT).show();
            }
        }
    }
}