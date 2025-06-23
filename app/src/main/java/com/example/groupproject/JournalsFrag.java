package com.example.groupproject;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class JournalsFrag extends Fragment {
    private ListView journalList;
    private TextView emptyMessage;
    private DBHelper dbHelper;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.journals_frag, container, false);
        journalList = root.findViewById(R.id.journalList);
        emptyMessage = root.findViewById(R.id.emptyMessage);
        if (dbHelper == null) {
            dbHelper = new DBHelper(inflater.getContext());
        }
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadJournalEntries();
    }

    public void loadJournalEntries() {
        ArrayList<JournalEntry> entryList = new ArrayList<>();
        Cursor cursor = dbHelper.getAllData();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String data = cursor.getString(cursor.getColumnIndexOrThrow("data"));
                entryList.add(new JournalEntry(id, title, data));
            } while (cursor.moveToNext());
            cursor.close();
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
