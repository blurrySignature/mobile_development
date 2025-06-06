package ru.mirea.nesterovpv.mireaproject;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AltitudeFragment extends Fragment implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor pressureSensor;
    private TextView textAltitude;
    private TextView textAdvice;

    public AltitudeFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_altitude, container, false);

        textAltitude = view.findViewById(R.id.textAltitude);
        textAdvice = view.findViewById(R.id.textAdvice);

        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (pressureSensor != null) {
            sensorManager.registerListener(this, pressureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            textAltitude.setText("Барометр недоступен");
            textAdvice.setText("Невозможно измерить высоту");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float pressure = event.values[0];
        double altitude = SensorManager.getAltitude(SensorManager.PRESSURE_STANDARD_ATMOSPHERE, pressure);

        textAltitude.setText(String.format("Высота: %.1f м", altitude));

        if (altitude < 1000) {
            textAdvice.setText("Вы на безопасной высоте. Условия комфортные.");
        } else if (altitude < 2500) {
            textAdvice.setText("Средняя высота. Возможен дискомфорт.");
        } else {
            textAdvice.setText("Высокогорье! Возможны проблемы с самочувствием.");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}