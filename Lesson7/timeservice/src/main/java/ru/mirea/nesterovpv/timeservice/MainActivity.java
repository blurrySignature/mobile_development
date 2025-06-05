package ru.mirea.nesterovpv.timeservice;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import ru.mirea.nesterovpv.timeservice.databinding.ActivityMainBinding;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private final String host = "time.nist.gov";
    private final int port = 13;
    private static final String TAG = "TimeService";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetTimeTask().execute();
            }
        });
    }

    private class GetTimeTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            try (Socket socket = new Socket(host, port);
                 BufferedReader reader = SocketUtils.getReader(socket)) {

                reader.readLine();
                String response = reader.readLine();
                Log.d(TAG, "Response: " + response);
                return parseDateTime(response);

            } catch (UnknownHostException e) {
                Log.e(TAG, "Unknown host", e);
                return "Ошибка: неизвестный хост";
            } catch (IOException e) {
                Log.e(TAG, "IO Exception", e);
                return "Ошибка соединения";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            binding.textView.setText(result);
        }

        private String parseDateTime(String raw) {
            if (raw == null || raw.length() < 17) return "Неверный формат данных";

            try {
                String[] parts = raw.split(" ");
                String date = parts[1];
                String time = parts[2];
                return "Дата: " + date + "\nВремя: " + time;
            } catch (Exception e) {
                return "Ошибка разбора времени";
            }
        }
    }
}