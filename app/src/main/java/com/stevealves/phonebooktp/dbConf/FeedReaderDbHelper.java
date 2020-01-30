package com.stevealves.phonebooktp.dbConf;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FeedReaderDbHelper extends SQLiteOpenHelper {

    public final static int DATABASE_VERSION = 1;
    public final static String DATABASE_NAME = "CONTACTOsss.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedReaderContract.FeedEntry.TABLE_NAME + " (" +
                    FeedReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FeedReaderContract.FeedEntry.COLUMN_NOME + " TEXT, " +
                    FeedReaderContract.FeedEntry.COLUMN_PHONE + " INTEGER, " +
                    FeedReaderContract.FeedEntry.COLUMN_EMAIL + " TEXT, " +
                    FeedReaderContract.FeedEntry.COLUMN_BIRTHDAY + " TEXT, " +
                    FeedReaderContract.FeedEntry.COLUMN_PHOTO + " BLOB," +
                    FeedReaderContract.FeedEntry.COLUMN_LATITUDE + " DOUBLE, " +
                    FeedReaderContract.FeedEntry.COLUMN_FAV + " BOOLEAN, " +
                    FeedReaderContract.FeedEntry.COLUMN_LONGITUDE + " DOUBLE )";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntry.TABLE_NAME;

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(SQL_CREATE_ENTRIES);
        } catch (Exception e) {
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        try {
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        } catch (Exception e) {
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}
