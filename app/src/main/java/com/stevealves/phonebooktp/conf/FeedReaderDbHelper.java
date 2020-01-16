package com.stevealves.phonebooktp.conf;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class FeedReaderDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private final static int DATABASE_VERSION = 1;
    private final static String DATABASE_NAME = "contactos.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedReaderContract.FeedEntry.TABLE_NAME + " (" +
                    FeedReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    FeedReaderContract.FeedEntry.COLUMN_NOME + " TEXT NOT NULL," +
                    FeedReaderContract.FeedEntry.COLUMN_PHONE + " INT NOT NUL," +
                    FeedReaderContract.FeedEntry.COLUMN_EMAIL + " TEXT NOT NUL," +
                    FeedReaderContract.FeedEntry.COLUMN_BIRTHDAY + " TEXT NOT NUL," +
                    FeedReaderContract.FeedEntry.COLUMN_PHOTO + " BLOB NOT NUL," +
                    FeedReaderContract.FeedEntry.COLUMN_LATITUDE + " DOUBLE NOT NUL," +
                    FeedReaderContract.FeedEntry.COLUMN_LONGITUDE + " DOUBLE NOT NUL)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntry.TABLE_NAME;

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}
