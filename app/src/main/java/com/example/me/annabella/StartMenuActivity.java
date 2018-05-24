package com.example.me.annabella;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;

public class StartMenuActivity extends AppCompatActivity {
    private Button studyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_layout);

        studyButton = findViewById(R.id.button_study);

        Data.getInstance().setStudyList(getStudyListFromDatabase());
        toggleEnableStudyButton();
        Data.getInstance().setAllVerbs(getAllVerbsBasicInfoFromDatabase());
    }

    private void toggleEnableStudyButton() {
        int verbsInStudyList;
        if ((verbsInStudyList = Data.getInstance().getStudyList().size()) > 0) {
            studyButton.setText("Study " + verbsInStudyList + " verbs");
            studyButton.setEnabled(true);
        } else {
            studyButton.setText("Study");
            studyButton.setEnabled(false);
        }
    }

    private ArrayList<String[]> getAllVerbsBasicInfoFromDatabase() {
        ArrayList<String[]> verbsListData = new ArrayList<>();
        Uri verbs = Uri.parse("content://com.example.me.annabella.RussianConjugationProvider");
        String[] projection = new String[] {
                RussianConjugationProvider._ID,
                RussianConjugationProvider.INF_IMPF,
                RussianConjugationProvider.INF_PF,
                RussianConjugationProvider.MEANING
        };
        Cursor c = getContentResolver().query(verbs, projection, null, null, null);
        if (c.moveToFirst()) {
            do {
                verbsListData.add(new String[] {
                        Long.toString(c.getLong(c.getColumnIndex(RussianConjugationProvider._ID))),
                        c.getString(c.getColumnIndex(RussianConjugationProvider.INF_IMPF)),
                        c.getString(c.getColumnIndex(RussianConjugationProvider.INF_PF)),
                        c.getString(c.getColumnIndex(RussianConjugationProvider.MEANING))
                });
            } while (c.moveToNext());
        }
        c.close();
        return verbsListData;
    }

    private HashSet<Long> getStudyListFromDatabase() {
        HashSet<Long> studyList = new HashSet<>();

        Uri verbs = Uri.parse("content://com.example.me.annabella.RussianConjugationProvider");
        String[] projection = new String[] { RussianConjugationProvider._ID };
        String selection = "saved=1";
        Cursor c = getContentResolver().query(verbs, projection, selection, null, null);

        if (c.moveToFirst()) {
            do {
                studyList.add(c.getLong(c.getColumnIndex(RussianConjugationProvider._ID)));
            } while (c.moveToNext());
        }
        c.close();
        return studyList;
    }

    @Override
    protected void onResume() {
        super.onResume();
        toggleEnableStudyButton();
        updateStudyList(Data.getInstance().getStudyList());
    }

    private void updateStudyList(HashSet<Long> newList) {
        HashSet<Long> oldList = getStudyListFromDatabase();
        if (oldList.equals(newList)) {
            Log.d("updateStudyList", "no change to list");
            return;
        }
        for (Long id : oldList) {
            if (!newList.contains(id)) {
                updateStudyListRow(id, 0);
            }
        }

        for (Long id: newList) {
            if (!oldList.contains(id)) {
                updateStudyListRow(id, 1);
            }
        }
        Data.getInstance().setStudyList(newList);
    }

    private void updateStudyListRow(long id, int value) {
        ContentValues cv = new ContentValues();
        cv.put(RussianConjugationProvider.SAVED, value);
        String where = "_id=" + id;
        int count = getContentResolver().update(RussianConjugationProvider.CONTENT_URI, cv, where, null);
        if (count != 1) {
            Log.d("updateStudyList", "update returned != 1");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void showAllVerbs(View view) {
        Intent intent = new Intent(this, AllVerbsActivity.class);
        startActivity(intent);
    }

    public void showFlashcard(View view) {
        Intent intent = new Intent(this, FlashcardActivity.class);
        startActivity(intent);
    }
}
