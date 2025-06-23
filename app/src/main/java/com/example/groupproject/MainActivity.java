package com.example.groupproject;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private Fragment currentFragment, entryFrag, journalsFrag, settingsFrag;
    private BottomNavigationView nav;
    public static final String ENTRY_TAG = "ENTRY";
    public static final String JOURNALS_TAG = "JOURNALS";
    public static final String SETTINGS_TAG = "SETTINGS";
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

        nav = findViewById(R.id.bottomNav);
        // Create new fragments on initial creation
        if (savedInstanceState == null) {
            entryFrag = new EntryFrag();
            journalsFrag = new JournalsFrag();
            settingsFrag = new SettingsFrag();
            setCurrentFragment(entryFrag, ENTRY_TAG);
        } else {
            // Use existing fragments on resume
            entryFrag = getSupportFragmentManager().findFragmentByTag(ENTRY_TAG);
            journalsFrag = getSupportFragmentManager().findFragmentByTag(JOURNALS_TAG);
            settingsFrag = getSupportFragmentManager().findFragmentByTag(SETTINGS_TAG);
        }

        nav.setOnItemSelectedListener(item -> {
            int itemID = item.getItemId();
            if (itemID == R.id.nav_entry)
                setCurrentFragment(entryFrag, ENTRY_TAG);
            else if (itemID == R.id.nav_journal)
                setCurrentFragment(journalsFrag, JOURNALS_TAG);
            else if (itemID == R.id.nav_settings)
                setCurrentFragment(settingsFrag, SETTINGS_TAG);
            return true;
        });
    }

    public void setScreen(String tag) {
        switch (tag) {
            case ENTRY_TAG:
                nav.setSelectedItemId(R.id.nav_entry);
                break;
            case JOURNALS_TAG:
                nav.setSelectedItemId(R.id.nav_journal);
                break;
            case SETTINGS_TAG:
                nav.setSelectedItemId(R.id.nav_settings);
                break;
        }
    }

    public void setCurrentFragment(Fragment fragment, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (currentFragment != null) {
            // detach current fragment
            transaction.detach(currentFragment);
        }
        currentFragment = fragment;
        // Add the fragment to the fragment manager if not already added
        if (!fragment.isAdded()) {
            transaction.add(R.id.contentFrame, fragment, tag);
        }
        // Show fragment
        transaction.attach(fragment);
        transaction.commit();
    }
}
