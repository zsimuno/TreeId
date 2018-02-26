package com.example.treeid.treeid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Nikola on 26.2.2018..
 */

public class DBAdapter {
    static final String KEY_ROWID   = "_id";
    static final String KEY_IME     = "ime";
    static final String KEY_LAT_IME = "lat_ime";
    static final String KEY_LIST    = "list";
    static final String KEY_VISINA  = "visina";
    static final String KEY_PLOD    = "plod";
    static final String KEY_KORA    = "kora";
    static final String KEY_KROSNJA = "krosnja";
    static final String KEY_LINK    = "link";
    static final String TAG = "DBAdapter";

    static final String DATABASE_NAME = "MyDB";
    static final String DATABASE_TABLE = "stabla";
    static final int DATABASE_VERSION = 2;

    static final String DATABASE_CREATE =
            "create table stabla (_id integer primary key autoincrement, "
                    + "ime text not null, lat_ime text not null,"
                    + " list text not null,"
                    + "visina text not null, plod text not null,"
                    + "kora text not null, krosnja text not null"
                    + "link text not null);";

    final Context context;

    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading db from" + oldVersion + "to"
                    + newVersion );
            db.execSQL("DROP TABLE IF EXISTS stabla");
            onCreate(db);
        }
    }

    //---opens the database---
    public DBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }

    //---insert a tree into the database---
    public long insertTree(String ime, String list, String visina, String plod, String kora, String krosnja, String link, String lat_ime)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_IME, ime);
        initialValues.put(KEY_LIST, list);
        initialValues.put(KEY_VISINA, visina);
        initialValues.put(KEY_PLOD, plod);
        initialValues.put(KEY_KORA, kora);
        initialValues.put(KEY_KROSNJA, krosnja);
        initialValues.put(KEY_LINK, link);
        initialValues.put(KEY_LAT_IME, lat_ime);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //---deletes a particular tree---
    public boolean deleteTree(long rowId)
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    //---retrieves all the trees---
    public Cursor getAllTrees() // Vratit će listu objekata klase "Stablo"
    {
        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID,KEY_IME,KEY_LIST,KEY_VISINA,KEY_PLOD,KEY_KORA,KEY_KROSNJA,KEY_LINK,KEY_LAT_IME }, null, null, null, null, null);
    }

    //---retrieves a particular tree---
    public Cursor getTree(long rowId) throws SQLException // Vratit će objekt klase "Stablo"
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,KEY_IME,KEY_LIST,KEY_VISINA,KEY_PLOD,KEY_KORA,KEY_KROSNJA,KEY_LINK,KEY_LAT_IME  }, KEY_ROWID + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---updates a tree---
    public boolean updateTree(long rowId, String ime, String list, String visina, String plod, String kora, String krosnja, String link, String lat_ime)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_IME, ime);
        args.put(KEY_LIST, list);
        args.put(KEY_VISINA, visina);
        args.put(KEY_PLOD, plod);
        args.put(KEY_KORA, kora);
        args.put(KEY_KROSNJA, krosnja);
        args.put(KEY_LINK, link);
        args.put(KEY_LAT_IME, lat_ime);
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

}
