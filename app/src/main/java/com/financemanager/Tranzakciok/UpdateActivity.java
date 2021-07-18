package com.financemanager.Tranzakciok;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.financemanager.R;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class UpdateActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText TYPE_input, CATEGORY_input, VALUE_input, DATE_input;
    private Button update_button, delete_button;
    private String id, TYPE, CATEGORY, VALUE, DATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        TYPE_input       = findViewById(R.id.type_update);
        CATEGORY_input   = findViewById(R.id.category_update);
        VALUE_input      = findViewById(R.id.value_update);
        DATE_input       = findViewById(R.id.date_update);

        getAndSetIntentData();
        update_button    = findViewById(R.id.addToDB_update);
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper db = new DatabaseHelper(UpdateActivity.this);
                TYPE    = TYPE_input.getText().toString().trim();
                CATEGORY= CATEGORY_input.getText().toString().trim();
                VALUE   = VALUE_input.getText().toString().trim();
                DATE    = DATE_input.getText().toString().trim();
                db.updateData(id , TYPE , CATEGORY , Integer.parseInt(VALUE), DATE);
            }
        });

        delete_button    = findViewById(R.id.addToDB_delete);
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper db = new DatabaseHelper(UpdateActivity.this);
                db.deleteOneRow(id);
            }
        });

    }

    public void getAndSetIntentData(){
        if( getIntent().hasExtra("id_elem") &&
            getIntent().hasExtra("TYPE_elem") &&
            getIntent().hasExtra("CATEGORY_elem") &&
            getIntent().hasExtra("VALUE_elem") &&
            getIntent().hasExtra("DATE_elem") ) {
            // getting data from intent
            id      = getIntent().getStringExtra("id_elem");
            TYPE    = getIntent().getStringExtra("TYPE_elem");
            CATEGORY= getIntent().getStringExtra("CATEGORY_elem");
            VALUE   = getIntent().getStringExtra("VALUE_elem");
            DATE    = getIntent().getStringExtra("DATE_elem");
            // setting intent data
            TYPE_input.setText(TYPE);
            CATEGORY_input.setText(CATEGORY);
            VALUE_input.setText(VALUE);
            DATE_input.setText(DATE);
        }
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
                        EditText type = findViewById(R.id.type_update);
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

    public void handleCategoryChooser(View view){
        DatabaseHelper db = new DatabaseHelper(UpdateActivity.this);
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
                        EditText type = findViewById(R.id.category_update);
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
                EditText type = findViewById(R.id.date_update);
                type.setText(date);
            }
        }, 100);
    }
}
