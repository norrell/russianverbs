package com.example.me.annabella;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StudyListActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("StudyListActivity", "onCreate");

        setTitle("Study List");
        if (Data.getInstance().getStudyList().size() == 0) {
            displayEmpyListMessage();
        } else {
            displayStudyList();
//        Toolbar myToolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(myToolbar);
//        ActionBar ab = getSupportActionBar();
//        ab.setDisplayHomeAsUpEnabled(true);


        }
    }

    private void displayStudyList() {
        setContentView(R.layout.study_list_layout);
        mRecyclerView = findViewById(R.id.recyclerview_study_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        setAdapter();
    }

    private void displayEmpyListMessage() {
        setContentView(R.layout.empty_list_layout);
    }

    private void setAdapter() {
        List<Long> sortedIDsInStudyList = new ArrayList<Long>() {
            @Override
            public boolean add(Long x) {
                int index = Collections.binarySearch(this, x);
                if (index < 0) index = ~index;
                super.add(index, x);
                return true;
            }
        };
        for (Long id : Data.getInstance().getStudyList())
            sortedIDsInStudyList.add(id);

        mRecyclerView.setAdapter(new StudyListAdapter(sortedIDsInStudyList));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("onCreateOptionsMenu", "");
        getMenuInflater().inflate(R.menu.study_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_clear_studylist:
                return true;
            case R.id.action_show_all_verbs:
                Intent intent = new Intent(this, AllVerbsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("StudyListActivity", "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("StudyListActivity", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("StudyListActivity", "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("StudyListActivity", "onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Data.getInstance().getStudyList().size() == 0) {
            displayEmpyListMessage();
        } else {
            displayStudyList();
        }
        Log.d("StudyListActivity", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("StudyListActivity", "onResume");
    }
}

