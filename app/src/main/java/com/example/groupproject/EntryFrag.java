package com.example.groupproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class EntryFrag extends Fragment {

    private EditText titleField, dataField;
    private DBHelper dbHelper;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_entry, container, false);

        titleField = root.findViewById(R.id.journalTitle);
        dataField = root.findViewById(R.id.journalData);
        Button submitBtn = root.findViewById(R.id.submitBtn);
        dbHelper = new DBHelper(requireContext()); // Initialize DBHelper

        submitBtn.setOnClickListener(v -> {
            String title = titleField.getText().toString().trim();
            String data = dataField.getText().toString().trim();

            if (!title.isEmpty() && !data.isEmpty()) {
                boolean success = dbHelper.insertData(title, data);  // <-- Save entry
                if (success) {
                    Toast.makeText(getContext(), "Entry saved!", Toast.LENGTH_SHORT).show();
                    titleField.setText("");
                    dataField.setText("");

                    ((MainActivity) requireActivity()).setScreen(MainActivity.JOURNALS_TAG);
                } else {
                    Toast.makeText(getContext(), "Failed to save entry", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}
