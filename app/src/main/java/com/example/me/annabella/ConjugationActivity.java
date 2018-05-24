package com.example.me.annabella;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ConjugationActivity extends AppCompatActivity {
    private TextView inf_pair;
    private TextView meaning;
    private TextView pres_1s;
    private TextView pres_2s;
    private TextView pres_3s;
    private TextView pres_1p;
    private TextView pres_2p;
    private TextView pres_3p;
    private TextView past_impf_m;
    private TextView past_impf_f;
    private TextView past_impf_n;
    private TextView past_impf_pl;
    private TextView past_pf_m;
    private TextView past_pf_f;
    private TextView past_pf_n;
    private TextView past_pf_pl;
    private TextView fut_impf_1s;
    private TextView fut_impf_2s;
    private TextView fut_impf_3s;
    private TextView fut_impf_1p;
    private TextView fut_impf_2p;
    private TextView fut_impf_3p;
    private TextView fut_pf_1s;
    private TextView fut_pf_2s;
    private TextView fut_pf_3s;
    private TextView fut_pf_1p;
    private TextView fut_pf_2p;
    private TextView fut_pf_3p;
    private TextView imp_impf_2s;
    private TextView imp_impf_2p;
    private TextView imp_pf_2s;
    private TextView imp_pf_2p;
    private TextView part_pres_act_impf;
    private TextView part_past_act_impf;
    private TextView part_past_act_pf;
    private TextView part_pres_pass_impf;
    private TextView part_past_pass_impf;
    private TextView part_past_pass_pf;
    private TextView ger_impf;
    private TextView ger_pf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conjugation_layout);

        inf_pair = findViewById(R.id.conju_infinitive_pair);
        meaning = findViewById(R.id.conju_meaning);
        pres_1s = findViewById(R.id.conju_pres_impf_1s);
        pres_2s = findViewById(R.id.conju_pres_impf_2s);
        pres_3s = findViewById(R.id.conju_pres_impf_3s);
        pres_1p = findViewById(R.id.conju_pres_impf_1p);
        pres_2p = findViewById(R.id.conju_pres_impf_2p);
        pres_3p = findViewById(R.id.conju_pres_impf_3p);
        past_impf_m = findViewById(R.id.conju_past_impf_m);
        past_impf_f = findViewById(R.id.conju_past_impf_f);
        past_impf_n = findViewById(R.id.conju_past_impf_n);
        past_impf_pl = findViewById(R.id.conju_past_impf_pl);
        past_pf_m = findViewById(R.id.conju_past_pf_m);
        past_pf_f = findViewById(R.id.conju_past_pf_f);
        past_pf_n = findViewById(R.id.conju_past_pf_n);
        past_pf_pl = findViewById(R.id.conju_past_pf_pl);
        fut_impf_1s = findViewById(R.id.conju_fut_impf_1s);
        fut_impf_2s = findViewById(R.id.conju_fut_impf_2s);
        fut_impf_3s = findViewById(R.id.conju_fut_impf_3s);
        fut_impf_1p = findViewById(R.id.conju_fut_impf_1p);
        fut_impf_2p = findViewById(R.id.conju_fut_impf_2p);
        fut_impf_3p = findViewById(R.id.conju_fut_impf_3p);
        fut_pf_1s = findViewById(R.id.conju_fut_pf_1s);
        fut_pf_2s = findViewById(R.id.conju_fut_pf_2s);
        fut_pf_3s = findViewById(R.id.conju_fut_pf_3s);
        fut_pf_1p = findViewById(R.id.conju_fut_pf_1p);
        fut_pf_2p = findViewById(R.id.conju_fut_pf_2p);
        fut_pf_3p = findViewById(R.id.conju_fut_pf_3p);
        imp_impf_2s = findViewById(R.id.conju_imp_impf_2s);
        imp_impf_2p = findViewById(R.id.conju_imp_impf_2p);
        imp_pf_2s = findViewById(R.id.conju_imp_pf_2s);
        imp_pf_2p = findViewById(R.id.conju_imp_pf_2p);
        ger_impf = findViewById(R.id.conju_ger_impf);
        ger_pf = findViewById(R.id.conju_ger_pf);
        part_pres_act_impf = findViewById(R.id.conju_part_pres_att_impf);
        part_past_act_impf = findViewById(R.id.conju_part_past_att_impf);
        part_past_act_pf = findViewById(R.id.conju_part_past_att_pf);
        part_pres_pass_impf = findViewById(R.id.conju_part_pres_pass_impf);
        part_past_pass_impf = findViewById(R.id.conju_part_past_pass_impf);
        part_past_pass_pf = findViewById(R.id.conju_part_past_pass_pf);

        Intent intent = getIntent();
        String verbID = intent.getStringExtra("VERB_ID");

        showConjugation(verbID);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), FlashcardActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    private void showConjugation(String id) throws AssertionError {
        Uri uri = Uri.parse("content://com.example.me.annabella.RussianConjugationProvider");
        String selection = RussianConjugationProvider._ID + "=" + id;
        Cursor c = getContentResolver().query(uri, null, selection,
                null, null);
        if (c == null) {
            throw new AssertionError("cursor is null!");
        }

        if (c.getCount() != 1) {
            throw new AssertionError("more than one column retrieved!");
        }

        if (c.moveToFirst()) {
            String inf_impf = c.getString(c.getColumnIndex(RussianConjugationProvider.INF_IMPF));
            String inf_pf = c.getString(c.getColumnIndex(RussianConjugationProvider.INF_PF));
            inf_pair.setText(inf_impf + " / " + inf_pf);
            meaning.setText(c.getString(c.getColumnIndex(RussianConjugationProvider.MEANING)));
            pres_1s.setText(c.getString(c.getColumnIndex(RussianConjugationProvider.PRES_1S)));
            pres_2s.setText(c.getString(c.getColumnIndex(RussianConjugationProvider.PRES_2S)));
            pres_3s.setText(c.getString(c.getColumnIndex(RussianConjugationProvider.PRES_3S)));
            pres_1p.setText(c.getString(c.getColumnIndex(RussianConjugationProvider.PRES_1P)));
            pres_2p.setText(c.getString(c.getColumnIndex(RussianConjugationProvider.PRES_2P)));
            pres_3p.setText(c.getString(c.getColumnIndex(RussianConjugationProvider.PRES_3P)));
            past_impf_m.setText(c.getString(c.getColumnIndex(RussianConjugationProvider.PAST_IMPF_M)));
            past_impf_f.setText(c.getString(c.getColumnIndex(RussianConjugationProvider.PAST_IMPF_F)));
            past_impf_n.setText(c.getString(c.getColumnIndex(RussianConjugationProvider.PAST_IMPF_N)));
            past_impf_pl.setText(c.getString(c.getColumnIndex(RussianConjugationProvider.PAST_IMPF_P)));
            past_pf_m.setText(c.getString(c.getColumnIndex(RussianConjugationProvider.PAST_PF_M)));
            past_pf_f.setText(c.getString(c.getColumnIndex(RussianConjugationProvider.PAST_PF_F)));
            past_pf_n.setText(c.getString(c.getColumnIndex(RussianConjugationProvider.PAST_PF_N)));
            past_pf_pl.setText(c.getString(c.getColumnIndex(RussianConjugationProvider.PAST_PF_P)));
            fut_impf_1s.setText("буду " + inf_impf);
            fut_impf_2s.setText("будешь " + inf_impf);
            fut_impf_3s.setText("будет " + inf_impf);
            fut_impf_1p.setText("будем " + inf_impf);
            fut_impf_2p.setText("будете " + inf_impf);
            fut_impf_3p.setText("будут " + inf_impf);
            fut_pf_1s.setText(c.getString(c.getColumnIndex(RussianConjugationProvider.FUT_PF_1S)));
            fut_pf_2s.setText(c.getString(c.getColumnIndex(RussianConjugationProvider.FUT_PF_2S)));
            fut_pf_3s.setText(c.getString(c.getColumnIndex(RussianConjugationProvider.FUT_PF_3S)));
            fut_pf_1p.setText(c.getString(c.getColumnIndex(RussianConjugationProvider.FUT_PF_1P)));
            fut_pf_2p.setText(c.getString(c.getColumnIndex(RussianConjugationProvider.FUT_PF_2P)));
            fut_pf_3p.setText(c.getString(c.getColumnIndex(RussianConjugationProvider.FUT_PF_3P)));
            imp_impf_2s.setText(c.getString(c.getColumnIndex(RussianConjugationProvider.IMP_IMPF_2S)));
            imp_impf_2p.setText(c.getString(c.getColumnIndex(RussianConjugationProvider.IMP_IMPF_2P)));
            imp_pf_2s.setText(c.getString(c.getColumnIndex(RussianConjugationProvider.IMP_PF_2S)));
            imp_pf_2p.setText(c.getString(c.getColumnIndex(RussianConjugationProvider.IMP_PF_2P)));
            ger_impf.setText(c.getString(c.getColumnIndex(RussianConjugationProvider.GER_IMPF)));
            ger_pf.setText(c.getString(c.getColumnIndex(RussianConjugationProvider.GER_PF)));
            part_pres_act_impf.setText(c.getString(c.getColumnIndex(RussianConjugationProvider.PART_PRES_ACT_IMPF)));
            part_past_act_impf.setText(c.getString(c.getColumnIndex(RussianConjugationProvider.PART_PAST_ACT_IMPF)));
            part_past_act_pf.setText(c.getString(c.getColumnIndex(RussianConjugationProvider.PART_PAST_ACT_PF)));
            part_pres_pass_impf.setText(c.getString(c.getColumnIndex(RussianConjugationProvider.PART_PRES_PASS_IMPF)));
            part_past_pass_impf.setText(c.getString(c.getColumnIndex(RussianConjugationProvider.PART_PAST_PASS_IMPF)));
            part_past_pass_pf.setText(c.getString(c.getColumnIndex(RussianConjugationProvider.PART_PAST_PASS_PF)));
        }
        c.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.d("ConjugationActivity", "home");
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
