package ru.mirea.nesterovpv.mireaproject;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.fragment.app.Fragment;

public class FilesFragment extends Fragment {

    public FilesFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_files, container, false);

        FloatingActionButton fab = view.findViewById(R.id.fab_file_action);
        fab.setOnClickListener(v -> showConversionDialog());

        return view;
    }

    private void showConversionDialog() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_convert, null);

        new AlertDialog.Builder(getContext())
                .setTitle("Конвертация файла")
                .setView(dialogView)
                .setPositiveButton("Конвертировать", (dialog, which) -> {
                    Toast.makeText(getContext(), "Файл успешно конвертирован", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Отмена", null)
                .show();
    }
}