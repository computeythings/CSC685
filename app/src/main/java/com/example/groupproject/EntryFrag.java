package com.example.groupproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class EntryFrag extends Fragment {
    private EditText titleField, dataField;
    private Button submitBtn;
    private ToggleButton button;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.entry_frag, container, false);
        titleField = root.findViewById(R.id.journalTitle);
        dataField = root.findViewById(R.id.journalData);
        submitBtn = root.findViewById(R.id.submitBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleField.setText("");
                dataField.setText("");
            }
        });
        return root;
    }
}
