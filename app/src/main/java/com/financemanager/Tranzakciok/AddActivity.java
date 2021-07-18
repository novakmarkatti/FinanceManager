package com.financemanager.Tranzakciok;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.financemanager.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
    }

    public void handleHozzaadas(View view) {
        EditText TYPE       = findViewById(R.id.type);
        EditText CATEGORY   = findViewById(R.id.category);
        EditText VALUE      = findViewById(R.id.value);
        EditText DATE       = findViewById(R.id.date);

        DatabaseHelper db = new DatabaseHelper(AddActivity.this);

        if( DATE.getText().toString().equals("") ){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/dd", Locale.getDefault());
            String currentDateandTime = sdf.format(new Date());

            db.addTranzakcio(   TYPE.getText().toString().trim() ,
                    CATEGORY.getText().toString().trim() ,
                    Integer.parseInt(VALUE.getText().toString().trim()),
                    currentDateandTime      );
        }else{
            db.addTranzakcio(   TYPE.getText().toString().trim() ,
                    CATEGORY.getText().toString().trim() ,
                    Integer.parseInt(VALUE.getText().toString().trim()),
                    DATE.getText().toString().trim()      );
        }

    }

    public void handleCategoryChooser(View view){
        DatabaseHelper db = new DatabaseHelper(AddActivity.this);
        List<String> listItems = new ArrayList<String>();
        Cursor c = db.getCategories();
        if (c.moveToFirst()){
            do {
                String temp = c.getString(0);
                listItems.add(temp);
            } while(c.moveToNext());
        }
        c.close();
        db.close();

        CharSequence[] charSequenceItems = listItems.toArray(new CharSequence[listItems.size()]);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Kategoria tipus");
        dialog.setItems( charSequenceItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        EditText type = findViewById(R.id.category);
                        type.setText(charSequenceItems[position]);
                    }
                }, 100);
            }
        });
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = dialog.create();
        alert.show();
    }

    public void handleTypeChooser(View view) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Tranzakció tipus");
        CharSequence[] charSequence = {"Bevétel","Kiadás"};
        dialog.setItems( charSequence, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        EditText type = findViewById(R.id.type);
                        if(position==0) {
                            type.setText(charSequence[0]);
                        }else if(position==1) {
                            type.setText(charSequence[1]);
                        }
                    }
                }, 100);
            }
        });
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = dialog.create();
        alert.show();
    }

    public void handleDateChooser(View view){
        DatePickerDialog dpd = new DatePickerDialog(this, this, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        dpd.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month = month + 1;
        String date = year + "/" + month + "/" + dayOfMonth;
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                EditText type = findViewById(R.id.date);
                type.setText(date);
            }
        }, 100);
    }
}