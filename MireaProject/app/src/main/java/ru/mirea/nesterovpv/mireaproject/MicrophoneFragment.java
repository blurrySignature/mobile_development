package ru.mirea.nesterovpv.mireaproject;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.Random;

public class MicrophoneFragment extends Fragment {

    private ProgressBar volumeBar;
    private TextView volumeText;
    private Button fakeMeasureButton;
    private Handler handler = new Handler();
    private Random random = new Random();

    public MicrophoneFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_microphone, container, false);

        volumeBar = view.findViewById(R.id.volumeBar);
        volumeText = view.findViewById(R.id.volumeText);
        fakeMeasureButton = view.findViewById(R.id.btnFakeMeasure);

        fakeMeasureButton.setOnClickListener(v -> simulateFakeMeasurement());

        return view;
    }

    private void simulateFakeMeasurement() {

        int fakeVolume = random.nextInt(101);
        volumeBar.setProgress(fakeVolume);
        volumeText.setText("Громкость: " + fakeVolume + " дБ");

        handler.postDelayed(() -> {
            int newFakeVolume = random.nextInt(101);
            volumeBar.setProgress(newFakeVolume);
            volumeText.setText("Громкость: " + newFakeVolume + " дБ");
        }, 300);
        handler.postDelayed(() -> {
            int newFakeVolume = random.nextInt(101);
            volumeBar.setProgress(newFakeVolume);
            volumeText.setText("Громкость: " + newFakeVolume + " дБ");
        }, 600);
    }
}