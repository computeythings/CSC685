package com.example.groupproject;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private Fragment entryFrag, journalsFrag, settingsFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        BottomNavigationView nav = findViewById(R.id.bottomNav);
        // Only create new Fragments on initial creation
        if (savedInstanceState == null) {
            entryFrag = new EntryFrag();
            journalsFrag = new JournalsFrag();
            settingsFrag = new SettingsFrag();

            setCurrentFragment(entryFrag);
        }

        nav.setOnItemSelectedListener(item -> {
            int itemID = item.getItemId();
            if (itemID == R.id.lans)
                setCurrentFragment(entryFrag);
            if (itemID == R.id.maps)
                setCurrentFragment(journalsFrag);
            if (itemID == R.id.settings)
                setCurrentFragment(settingsFrag);
            return true;
        });
    }
    private void setCurrentFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contentFrame, fragment)
                .commit();
    }
}