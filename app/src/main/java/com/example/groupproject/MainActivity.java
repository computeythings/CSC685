package com.example.groupproject;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private Fragment entryFrag, journalsFrag, settingsFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SettingManager settingsManager = new SettingManager(this);
        if (settingsManager.isDarkMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        BottomNavigationView nav = findViewById(R.id.bottomNav);

        if (savedInstanceState == null) {
            entryFrag = new EntryFrag();
            journalsFrag = new JournalsFrag();
            settingsFrag = new SettingsFrag();

            setCurrentFragment(entryFrag);
        }

        nav.setOnItemSelectedListener(item -> {
            int itemID = item.getItemId();
            if (itemID == R.id.nav_entry)
                setCurrentFragment(entryFrag);
            else if (itemID == R.id.nav_journal)
                setCurrentFragment(journalsFrag);
            else if (itemID == R.id.nav_settings)
                setCurrentFragment(settingsFrag);
            return true;
        });
    }

    public void setCurrentFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contentFrame, fragment)
                .commit();
    }
}
