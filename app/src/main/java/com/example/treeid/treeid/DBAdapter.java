package com.example.treeid.treeid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikola on 26.2.2018..
 */

public class DBAdapter {
    static final String KEY_ROWID   = "_id";
    static final String KEY_IME     = "ime";
    static final String KEY_LAT_IME = "lat_ime";
    static final String KEY_PORODICA= "porodica";
    static final String KEY_LIST    = "list";
    static final String KEY_VISINA  = "visina";
    static final String KEY_PLOD    = "plod";
    static final String KEY_KORA_BOJA    = "kora_boja";
    static final String KEY_KORA_TEKSTURA    = "kora_tekstura";
    static final String KEY_KROSNJA = "krosnja";
    static final String KEY_LINK    = "link";
    static final String TAG = "DBAdapter";

    static final String DATABASE_NAME = "MyDB";
    static final String DATABASE_TABLE = "stabla";
    static final int DATABASE_VERSION = 2;

    static final String DATABASE_CREATE =
            "create table stabla (_id integer primary key autoincrement, "
                    + "ime text not null, lat_ime text not null,"
                    + "porodica text not null, list text not null,"
                    + "visina text not null, plod text not null,"
                    + "kora_boja text not null, kora_tekstura text not null,"
                    + " krosnja text not null, link text not null);";

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

    //---otvara bazu---
    public DBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---zatvara bazu---
    public void close()
    {
        DBHelper.close();
    }

    //---ubacuje stablo u bazu---
    public long insertStablo(String ime, String lat_ime, String porodica, String list, String visina, String plod,
                             String kora_boja, String kora_tekstura, String krosnja, String link)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_IME, ime);
        initialValues.put(KEY_LAT_IME, lat_ime);
        initialValues.put(KEY_PORODICA, porodica);
        initialValues.put(KEY_LIST, list);
        initialValues.put(KEY_VISINA, visina);
        initialValues.put(KEY_PLOD, plod);
        initialValues.put(KEY_KORA_BOJA, kora_boja);
        initialValues.put(KEY_KORA_TEKSTURA, kora_tekstura);
        initialValues.put(KEY_KROSNJA, krosnja);
        initialValues.put(KEY_LINK, link);

        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //---briše specificno stablo---
    public boolean deleteStablo(long rowId)
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    //---vraca ArrayList koji sadrzi sva stabla---
    public ArrayList<Stablo> getAllStabla()
    {
        ArrayList<Stablo> svaStabla = new ArrayList<Stablo>();
        Cursor mCursor = db.query(DATABASE_TABLE, new String[] {KEY_ROWID,KEY_IME,KEY_LAT_IME,KEY_PORODICA,KEY_LIST,KEY_VISINA,KEY_PLOD,KEY_KORA_BOJA,KEY_KORA_TEKSTURA,KEY_KROSNJA,KEY_LINK }, null, null, null, null, null);

        if(mCursor.moveToFirst())
        {
            do
            {
                svaStabla.add(new Stablo(mCursor.getString(1), mCursor.getString(2), mCursor.getString(3),
                                         mCursor.getString(4), mCursor.getString(5), mCursor.getString(6),
                                         mCursor.getString(7), mCursor.getString(8), mCursor.getString(9),
                                         mCursor.getString((10))));
            } while(mCursor.moveToNext());
        }

        return svaStabla;
    }

    //---vraca specificno stablo---
    public Stablo getStablo(long rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,KEY_IME,KEY_LAT_IME,KEY_PORODICA,KEY_LIST,KEY_VISINA,KEY_PLOD,KEY_KORA_BOJA,KEY_KORA_TEKSTURA,KEY_KROSNJA,KEY_LINK  }, KEY_ROWID + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return new Stablo(mCursor.getString(1), mCursor.getString(2), mCursor.getString(3),
                          mCursor.getString(4), mCursor.getString(5), mCursor.getString(6),
                          mCursor.getString(7), mCursor.getString(8), mCursor.getString(9),
                          mCursor.getString((10)));
    }

    //---update-a odredjeno stablo---
    public boolean updateStablo(long rowId, String ime, String lat_ime, String porodica, String list, String visina, String plod,
                                    String kora_boja, String kora_tekstura, String krosnja, String link)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_IME, ime);
        args.put(KEY_LAT_IME, lat_ime);
        args.put(KEY_PORODICA, porodica);
        args.put(KEY_LIST, list);
        args.put(KEY_VISINA, visina);
        args.put(KEY_PLOD, plod);
        args.put(KEY_KORA_BOJA, kora_boja);
        args.put(KEY_KORA_TEKSTURA, kora_tekstura);
        args.put(KEY_KROSNJA, krosnja);
        args.put(KEY_LINK, link);
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

}


//        long insert(String table, String nullColumnHack, ContentValues values)
//        Metoda za ubacivanje retka u bazu.
//        Vraca ID retka koji je ubacen, ili -1 u slucaju greske.
//
//        int delete(String table, String whereClause, String[] whereArgs)
//        Metoda za brisanje redaka iz baze.
//        Vraca broj pobrisanih redaka ako je bilo whereClause-a,  inace vraca 0. Da se izbrisu svi retci stavimo "1" na mjesto whereClause.
//
//        int update(String table, ContentValues values, String whereClause, String[] whereArgs)
//        Metoda za izmjenu redaka u bazi.
//        Vraca broj redaka koji su izmjenjeni.
//
//        Cursor 	query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit)
//        Za upit na danoj tablici, vraca Cursor na rezultat.
//
//        Cursor 	query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy)
//        Za upit na danoj tablici, vraca Cursor na rezultat.
//
//        Cursor 	query(boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit)
//        Za upit na danoj tablici, vraca Cursor na rezultat.
//


