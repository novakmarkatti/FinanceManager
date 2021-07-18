package com.financemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "FinanceManager.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "Tranzakciok";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_VALUE = "value";
    private static final String COLUMN_DATE = "date";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =  "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TYPE + " TEXT, " +
                        COLUMN_CATEGORY + " TEXT, " +
                        COLUMN_VALUE+ " INTEGER, " +
                        COLUMN_DATE+ " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addTranzakcio(String TYPE, String CATEGORY, int VALUE, String DATE){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TYPE, TYPE);
        cv.put(COLUMN_CATEGORY, CATEGORY);
        cv.put(COLUMN_VALUE, VALUE);
        cv.put(COLUMN_DATE, DATE );
        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1){
            Toast.makeText( context,"Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText( context,"Added succsesfully", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db!= null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public void updateData(String row_id, String TYPE, String CATEGORY, int VALUE, String DATE){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TYPE, TYPE);
        cv.put(COLUMN_CATEGORY, CATEGORY);
        cv.put(COLUMN_VALUE, VALUE);
        cv.put(COLUMN_DATE, DATE );

        long result = db.update(TABLE_NAME, cv, "id=?", new String[]{row_id} );
        if(result == -1){
            Toast.makeText( context,"Failed to update", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText( context,"Updated succsesfully", Toast.LENGTH_SHORT).show();
        }
    }
}
