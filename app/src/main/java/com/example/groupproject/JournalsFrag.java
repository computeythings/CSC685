package com.example.groupproject;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class JournalsFrag extends Fragment {
    private ListView journalList;
    private TextView emptyMessage;
    private EditText searchField;
    private DBHelper dbHelper;
    private ArrayList<JournalEntry> allEntries; // Store all entries for filtering

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.journals_frag, container, false);
        journalList = root.findViewById(R.id.journalList);
        emptyMessage = root.findViewById(R.id.emptyMessage);
        searchField = root.findViewById(R.id.searchField);
        if (dbHelper == null) {
            dbHelper = new DBHelper(inflater.getContext());
        }
        allEntries = new ArrayList<>();
        setupSearchListener();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadJournalEntries(""); // Load all entries initially
        searchField.setText(""); // Clear search field on resume
    }

    private void setupSearchListener() {
        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                loadJournalEntries(s.toString().trim().toLowerCase());
            }
        });
    }

    public void loadJournalEntries(String query) {
        ArrayList<JournalEntry> entryList = new ArrayList<>();
        if (query.isEmpty()) {
            // Load all entries from database if query is empty
            Cursor cursor = dbHelper.getAllData();
            allEntries.clear();
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
                    String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                    String data = cursor.getString(cursor.getColumnIndexOrThrow("data"));
                    JournalEntry entry = new JournalEntry(id, title, data);
                    allEntries.add(entry);
                    entryList.add(entry);
                } while (cursor.moveToNext());
                cursor.close();
            }
        } else {
            // Filter entries based on query
            for (JournalEntry entry : allEntries) {
                if (entry.title.toLowerCase().contains(query) || entry.data.toLowerCase().contains(query)) {
                    entryList.add(entry);
                }
            }
        }

        // Show/hide the empty message
        if (entryList.isEmpty()) {
            emptyMessage.setVisibility(View.VISIBLE);
            journalList.setVisibility(View.GONE);
        } else {
            emptyMessage.setVisibility(View.GONE);
            journalList.setVisibility(View.VISIBLE);
        }

        JournalAdapter adapter = new JournalAdapter(requireContext(), entryList, dbHelper);
        journalList.setAdapter(adapter);
    }
}