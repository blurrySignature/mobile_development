package ru.mirea.nesterovpv.mireaproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {
    private EditText editName, editAge, editHobby;
    private Button btnSave;

    public static final String PREFS_NAME = "user_profile";

    public ProfileFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        editName = view.findViewById(R.id.editName);
        editAge = view.findViewById(R.id.editAge);
        editHobby = view.findViewById(R.id.editHobby);
        btnSave = view.findViewById(R.id.btnSaveProfile);

        loadProfile();

        btnSave.setOnClickListener(v -> saveProfile());

        return view;
    }

    private void loadProfile() {
        SharedPreferences prefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editName.setText(prefs.getString("name", ""));
        editAge.setText(prefs.getString("age", ""));
        editHobby.setText(prefs.getString("hobby", ""));
    }

    private void saveProfile() {
        String name = editName.getText().toString();
        String age = editAge.getText().toString();
        String hobby = editHobby.getText().toString();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(age)) {
            Toast.makeText(getContext(), "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences.Editor editor = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putString("name", name);
        editor.putString("age", age);
        editor.putString("hobby", hobby);
        editor.apply();

        Toast.makeText(getContext(), "Профиль сохранён", Toast.LENGTH_SHORT).show();
    }
}