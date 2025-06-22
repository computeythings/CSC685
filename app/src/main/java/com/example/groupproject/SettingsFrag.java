package com.example.groupproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.Arrays;

public class SettingsFrag extends Fragment {
    private SettingManager settingsManager;

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch themeSwitch;
    private RadioGroup fontSizeGroup;
    private Spinner fontSpinner;

    private final String[] fontOptions = {"Default", "Serif", "Monospace", "Sans"};

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.settings_frag, container, false);


        settingsManager = new SettingManager(requireContext());


        themeSwitch = root.findViewById(R.id.themeSwitch);
        fontSizeGroup = root.findViewById(R.id.fontSizeGroup);
        fontSpinner = root.findViewById(R.id.fontSpinner);

        setupInitialValues();
        setupListeners();

        return root;
    }

    private void setupInitialValues() {

        themeSwitch.setChecked(settingsManager.isDarkMode());


        String savedFontSize = settingsManager.getFontSize();
        int sizeId = R.id.fontMedium; // default
        if ("Small".equals(savedFontSize)) sizeId = R.id.fontSmall;
        else if ("Large".equals(savedFontSize)) sizeId = R.id.fontLarge;
        fontSizeGroup.check(sizeId);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_dropdown_item, fontOptions);
        fontSpinner.setAdapter(adapter);

        int selected = Arrays.asList(fontOptions).indexOf(settingsManager.getFontStyle());
        fontSpinner.setSelection(Math.max(selected, 0));
    }

    private void setupListeners() {
        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            settingsManager.setDarkMode(isChecked);
            Toast.makeText(getContext(), "Restarting to apply theme...", Toast.LENGTH_SHORT).show();
            requireActivity().recreate();
        });

        fontSizeGroup.setOnCheckedChangeListener((group, checkedId) -> {
            String selectedSize = "Medium";
            if (checkedId == R.id.fontSmall) selectedSize = "Small";
            else if (checkedId == R.id.fontLarge) selectedSize = "Large";
            settingsManager.setFontSize(selectedSize);
        });

        fontSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                settingsManager.setFontStyle(fontOptions[position]);
            }

            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });
    }
}
