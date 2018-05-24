package com.example.me.annabella;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class AllVerbsActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("AllVerbsActivity", "onCreate");
        setContentView(R.layout.all_verbs_layout);
//        Toolbar myToolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(myToolbar);
//        ActionBar ab = getSupportActionBar();
//        ab.setDisplayHomeAsUpEnabled(true);

        mRecyclerView = findViewById(R.id.recyclerview_all_verbs);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new AllVerbsAdapter(Data.getInstance().getAllVerbs()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.d("onCreateOptionsMenu", "");
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_select_all:
                return true;
            case R.id.action_clear_studylist:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("AllVerbsActivity", "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("AllVerbsActivity", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("AllVerbsActivity", "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("AllVerbsActivity", "onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("AllVerbsActivity", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("AllVerbsActivity", "onResume");
    }
}
