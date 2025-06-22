package com.example.groupproject;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class JournalAdapter extends ArrayAdapter<JournalEntry> {
    private final Context context;
    private final ArrayList<JournalEntry> entries;
    private final DBHelper dbHelper;

    public JournalAdapter(Context context, ArrayList<JournalEntry> entries, DBHelper dbHelper) {
        super(context, 0, entries);
        this.context = context;
        this.entries = entries;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        JournalEntry entry = entries.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.journal_list_item, parent, false);
        }

        TextView entryText = convertView.findViewById(R.id.entryText);
        Button editBtn = convertView.findViewById(R.id.editBtn);
        Button deleteBtn = convertView.findViewById(R.id.deleteBtn);

        entryText.setText(entry.title + "\n" + entry.data);

        // 🛠 Apply font preferences
        SettingManager settings = new SettingManager(context);

        // Font size
        switch (settings.getFontSize()) {
            case "Small":
                entryText.setTextSize(14);
                break;
            case "Large":
                entryText.setTextSize(20);
                break;
            default:
                entryText.setTextSize(16);
                break;
        }

        // Font style
        switch (settings.getFontStyle()) {
            case "Serif":
                entryText.setTypeface(Typeface.SERIF);
                break;
            case "Monospace":
                entryText.setTypeface(Typeface.MONOSPACE);
                break;
            case "Sans":
                entryText.setTypeface(Typeface.SANS_SERIF);
                break;
            default:
                entryText.setTypeface(Typeface.DEFAULT);
                break;
        }

        // Delete entry
        deleteBtn.setOnClickListener(v -> {
            if (dbHelper.deleteData(entry.id)) {
                entries.remove(position);
                notifyDataSetChanged();
                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
            }
        });

        // Edit entry
        editBtn.setOnClickListener(v -> showEditDialog(entry, position));

        return convertView;
    }

    private void showEditDialog(JournalEntry entry, int position) {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText titleBox = new EditText(context);
        titleBox.setHint("Title");
        titleBox.setText(entry.title);
        layout.addView(titleBox);

        final EditText dataBox = new EditText(context);
        dataBox.setHint("Data");
        dataBox.setText(entry.data);
        dataBox.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        layout.addView(dataBox);

        new AlertDialog.Builder(context)
                .setTitle("Edit Entry")
                .setView(layout)
                .setPositiveButton("Save", (dialog, which) -> {
                    String newTitle = titleBox.getText().toString().trim();
                    String newData = dataBox.getText().toString().trim();

                    if (!newTitle.isEmpty() && !newData.isEmpty()) {
                        if (dbHelper.updateData(entry.id, newTitle, newData)) {
                            entry.title = newTitle;
                            entry.data = newData;
                            notifyDataSetChanged();
                            Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
