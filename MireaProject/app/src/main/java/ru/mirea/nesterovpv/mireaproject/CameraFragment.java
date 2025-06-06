package ru.mirea.nesterovpv.mireaproject;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.Fragment;

public class CameraFragment extends Fragment {

    private ImageView imageView;
    private EditText nameEditText;
    private TextView savedProfileText;
    private Bitmap profilePhoto;

    public CameraFragment() {}

    private final ActivityResultLauncher<Intent> cameraLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Bundle extras = result.getData().getExtras();
                    profilePhoto = (Bitmap) extras.get("data");
                    imageView.setImageBitmap(profilePhoto);
                }
            });

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);

        imageView = view.findViewById(R.id.imageViewProfile);
        nameEditText = view.findViewById(R.id.editTextName);
        savedProfileText = view.findViewById(R.id.textViewSavedProfile);
        Button btnTakePhoto = view.findViewById(R.id.btnTakePhoto);
        Button btnSaveProfile = view.findViewById(R.id.btnSaveProfile);

        btnTakePhoto.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                    == PermissionChecker.PERMISSION_GRANTED) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraLauncher.launch(cameraIntent);
            } else {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 101);
            }
        });

        btnSaveProfile.setOnClickListener(v -> {
            String name = nameEditText.getText().toString();
            if (name.isEmpty() || profilePhoto == null) {
                savedProfileText.setText("Заполните имя и сделайте фото.");
            } else {
                savedProfileText.setText("Профиль сохранён:\nИмя: " + name);
            }
        });

        return view;
    }
}