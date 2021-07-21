package com.financemanager.Tranzakciok;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "FinanceManager.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "Tranzakciok";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_VALUE = "value";
    public static final String COLUMN_DATE = "date";

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
            Toast.makeText( context,"Add failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText( context,"Added successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readAllData(String start, String end){
        String query;
        if(!start.equals("") && !end.equals("")){
            query = "SELECT * FROM " + TABLE_NAME + " WHERE date between \"" + start + "\" and \"" + end + "\" order by date DESC";
        } else if(!start.equals("") && end.equals("")) {
            query = "SELECT * FROM " + TABLE_NAME + " WHERE date==\"" + start + "\" order by date DESC";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/dd", Locale.getDefault());
            String currentDateandTime = sdf.format(new Date());
            query = "SELECT * FROM " + TABLE_NAME + " WHERE date==\"" + currentDateandTime + "\" order by date DESC";
        }

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
            Toast.makeText( context,"Updated successfully", Toast.LENGTH_SHORT).show();
        }

    }

    public Cursor getCategories(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT DISTINCT category FROM " + TABLE_NAME, null);
        return c;
    }

    public void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME,  "id=?", new String[]{row_id} );
        if(result == -1){
            Toast.makeText( context,"Failed to delete", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText( context,"Deleted successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME );
    }
}
