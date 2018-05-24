package com.example.me.annabella;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Stack;

public class FlashcardActivity extends AppCompatActivity {
    private ArrayList<Long> studyListArray;
    private String mId;
    private TextView mInfImpfTextView;
    private TextView mInfPfTextView;
    private TextView mMeaningTextView;
    private ArrayList<Integer> mIndexes;
    private int currentPos;
    private Button buttonPrevious;
    private Button buttonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("flashcard", "onCreate");
        super.onCreate(savedInstanceState);

        if (Data.getInstance().getStudyList() == null
                || Data.getInstance().getStudyList().size() == 0) {
            setContentView(R.layout.empty_list_layout);
            return;
        }

        /* Serialize the HashSet into a an ArrayList.
         * The order of the elements is deterministic. */
        studyListArray = new ArrayList<>(Data.getInstance().getStudyList());

        if (savedInstanceState != null) {
            mIndexes = savedInstanceState.getIntegerArrayList("mIndexes");
            currentPos = savedInstanceState.getInt("currentPos");
        } else {
            mIndexes = new ArrayList<>();
            mIndexes.add(new Random().nextInt(studyListArray.size()));
            currentPos = 0;
        }

        setContentView(R.layout.flashcard_layout);
        buttonPrevious = findViewById(R.id.button_previous);
        buttonPrevious.setEnabled(currentPos != 0);
        buttonNext = findViewById(R.id.button_next);
        buttonNext.setEnabled(studyListArray.size() > 1);

        /* Retrieve the corresponding verb field from the
         * ArrayList of all the verbs in memory. */
        long verbID = studyListArray.get(mIndexes.get(currentPos));
        String[] fields = Data.getInstance().getAllVerbs().get((int) verbID - 1);
        mInfImpfTextView = findViewById(R.id.flashcard_inf_impf);
        mInfPfTextView = findViewById(R.id.flashcard_inf_pf);
        mMeaningTextView = findViewById(R.id.flashcard_meaning);
        mId = fields[0];
        mInfImpfTextView.setText(fields[1]);
        mInfPfTextView.setText(fields[2]);
        mMeaningTextView.setText(fields[3]);
    }

    public void showConjugation(View view) {
        Intent intent = new Intent(this, ConjugationActivity.class);
        intent.putExtra("VERB_ID", mId);
        startActivity(intent);
    }

    public void showNextPair(View view) {
        if (currentPos == mIndexes.size() - 1) {
            int index;
            /* If currentPos points to the end of our index list,
             * we need to generate a new random index, append it
             * to the list  and make it our current index */
            do {
                /* Ensure that two consecutive indexes aren't equal */
                index = new Random().nextInt(studyListArray.size());
            } while (index == mIndexes.get(currentPos));
            mIndexes.add(index);
        }
        currentPos++;
        /* Retrieve the respective verbs  */
        long verbID = studyListArray.get(mIndexes.get(currentPos));
        String[] fields = Data.getInstance().getAllVerbs().get((int) verbID - 1);
        mId = fields[0];
        mInfImpfTextView.setText(fields[1]);
        mInfPfTextView.setText(fields[2]);
        mMeaningTextView.setText(fields[3]);
        buttonPrevious.setEnabled(true);
    }

    public void showPreviousPair(View view) {
        if (currentPos == 0) {
            return;
        }

        currentPos--;
        long verbID = studyListArray.get(mIndexes.get(currentPos));
        String[] fields = Data.getInstance().getAllVerbs().get((int) verbID - 1);
        mId = fields[0];
        mInfImpfTextView.setText(fields[1]);
        mInfPfTextView.setText(fields[2]);
        mMeaningTextView.setText(fields[3]);
        if (currentPos == 0)
            buttonPrevious.setEnabled(false);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntegerArrayList("mIndexes", mIndexes);
        outState.putInt("currentPos", currentPos);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Flashcard", "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Flashcard", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Flashcard", "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Flashcard", "onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Flashcard", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Flashcard", "onResume");
    }
}
