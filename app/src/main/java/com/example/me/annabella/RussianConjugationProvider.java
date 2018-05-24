package com.example.me.annabella;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

public class RussianConjugationProvider extends ContentProvider {
    static final String PROVIDER_NAME = "com.example.me.annabella.RussianConjugationProvider";
    static final String URL = "content://" + PROVIDER_NAME + "/verbs_alphabetic";
    static final Uri CONTENT_URI = Uri.parse(URL);

    static final String _ID = "_id";
    static final String INF_IMPF = "inf_impf";
    static final String INF_PF = "inf_pf";
    static final String MEANING = "meaning";

    static final String PRES_1S = "pres_1s";
    static final String PRES_2S = "pres_2s";
    static final String PRES_3S = "pres_3s";
    static final String PRES_1P = "pres_1p";
    static final String PRES_2P = "pres_2p";
    static final String PRES_3P = "pres_3p";

    static final String PAST_IMPF_M = "past_impf_m";
    static final String PAST_IMPF_F = "past_impf_f";
    static final String PAST_IMPF_N = "past_impf_n";
    static final String PAST_IMPF_P = "past_impf_p";
    static final String PAST_PF_M = "past_pf_m";
    static final String PAST_PF_F = "past_pf_f";
    static final String PAST_PF_N = "past_pf_n";
    static final String PAST_PF_P = "past_pf_p";

    static final String FUT_PF_1S = "fut_pf_1s";
    static final String FUT_PF_2S = "fut_pf_2s";
    static final String FUT_PF_3S = "fut_pf_3s";
    static final String FUT_PF_1P = "fut_pf_1p";
    static final String FUT_PF_2P = "fut_pf_2p";
    static final String FUT_PF_3P = "fut_pf_3p";

    static final String IMP_IMPF_2S = "imp_impf_2s";
    static final String IMP_IMPF_2P = "imp_impf_2p";
    static final String IMP_PF_2S = "imp_pf_2s";
    static final String IMP_PF_2P = "imp_pf_2p";

    static final String PART_PRES_ACT_IMPF = "part_pres_act_impf";
    static final String PART_PAST_ACT_IMPF = "part_past_act_impf";
    static final String PART_PAST_ACT_PF = "part_past_act_pf";
    static final String PART_PRES_PASS_IMPF = "part_pres_pass_impf";
    static final String PART_PAST_PASS_IMPF = "part_past_pass_impf";
    static final String PART_PAST_PASS_PF = "part_past_pass_pf";

    static final String GER_IMPF = "ger_impf";
    static final String GER_PF = "ger_pf";

    static final String SCORE = "score";
    static final String SAVED = "saved";

    private static HashMap<String, String> VERBS_PROJECTION_MAP;

    static final int VERBS = 1;
    static final int VERB_ID = 2;

    static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "verbs_alphabetic", VERBS);
        uriMatcher.addURI(PROVIDER_NAME, "verbs_alphabetic/#", VERB_ID);
    }

    private SQLiteDatabase db;
    static final String DATABASE_NAME = "russian_verbs.db";
    static final String VERBS_TABLE_NAME = "verbs_alphabetic";
    static final int DATABASE_VERSION = 1;
    // static final String CREATE_DB_TABLE = "...";

    private static class DatabaseHelper extends SQLiteOpenHelper {
        Context context;
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
            if (!databaseExists()) {
                SQLiteDatabase dbRead = this.getReadableDatabase();
                dbRead.close();
                copyDatabase();
            }
        }

        private boolean databaseExists() {
            String appDataPath = context.getApplicationInfo().dataDir;
            Log.d("database", "dataDir: " + appDataPath);
            File dbFolder = new File(appDataPath + "/databases");
            if (!dbFolder.exists())
                return false;

            File dbFilePath = new File(appDataPath + "/databases/" + DATABASE_NAME);
            if (dbFilePath.exists()) {
                Log.d("database",
                        "getDatabasePath: " + context.getDatabasePath(DATABASE_NAME) + " exists");
                return true;
            }

            return false;
        }

        public void copyDatabase() {
            String appDataPath = context.getApplicationInfo().dataDir;
            File dbFolder = new File(appDataPath + "/databases");
            if (!dbFolder.exists()) {
                dbFolder.mkdir();
            }

            File dbFilePath = new File(appDataPath + "/databases/" + DATABASE_NAME);

            try {
                InputStream inputStream = context.getAssets().open(DATABASE_NAME);
                OutputStream outputStream = new FileOutputStream(dbFilePath);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
                outputStream.flush();
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                Log.e("copyDatabase", e.getMessage());
            }
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);

        db = dbHelper.getWritableDatabase();
        return (db != null);
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri,
                        @Nullable String[] projection,
                        @Nullable String selection,
                        @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(VERBS_TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case VERBS:
                qb.setProjectionMap(VERBS_PROJECTION_MAP); // null
                break;
            case VERB_ID:
                qb.appendWhere(_ID + "=" + uri.getPathSegments().get(1));
                break;
            default:
        }

        Cursor c = qb.query(db, projection, selection, selectionArgs,
                                null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case VERBS:
                Log.d("update", "VERBS uri: " + uri.toString());
                count = db.update(VERBS_TABLE_NAME, values, selection, selectionArgs);
                break;
            case VERB_ID:
                Log.d("update", "VERB_ID uri: " + uri.toString());
                count = db.update(VERBS_TABLE_NAME, values,
                        _ID + "=" + uri.getPathSegments().get(1) +
                                (!TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : ""),
                        selectionArgs);
                break;
            default:
                Log.d("update", "unknown URI: " + uri.toString());
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }
}
