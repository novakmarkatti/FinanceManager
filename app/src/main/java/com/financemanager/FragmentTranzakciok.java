package com.financemanager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.financemanager.Tranzakciok.AddActivity;
import com.financemanager.Tranzakciok.CustomAdapter;
import com.financemanager.Tranzakciok.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

public class FragmentTranzakciok extends Fragment {

    private DatabaseHelper dbhelper;
    private ArrayList<String> id;
    private ArrayList<String> TYPE;
    private ArrayList<String> CATEGORY;
    private ArrayList<String> VALUE;
    private ArrayList<String> DATE;
    private CustomAdapter customAdapter;
    private RecyclerView recyclerView;
    private ImageView empty_imageView;
    private TextView no_data;
    private boolean handleDateChooser1Btn = false;
    private boolean handleDateChooser2Btn = false;
    private DatePickerDialog.OnDateSetListener onDateSetListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_tranzakciok, container, false);

        final Button button1 =(Button) rootView.findViewById(R.id.kezdoDatumBtn);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(getContext(), onDateSetListener, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                dpd.show();
                handleDateChooser1Btn = true;
            }
        });

        final Button button2 =(Button) rootView.findViewById(R.id.vegzoDatumBtn);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(getContext(), onDateSetListener, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                dpd.show();
                handleDateChooser2Btn = true;
            }
        });

        empty_imageView = (ImageView) rootView.findViewById(R.id.no_data_imageview);
        no_data         = (TextView) rootView.findViewById(R.id.no_data_textview);

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = year + "/" + month  + "/" + dayOfMonth;
                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        TextView kezdoDatumText = rootView.findViewById(R.id.kezdoDatumText);
                        TextView kotojel = rootView.findViewById(R.id.kotojel);
                        TextView vegzoDatumText = rootView.findViewById(R.id.vegzoDatumText);

                        if(handleDateChooser1Btn == true){
                            kezdoDatumText.setText(date);
                            handleDateChooser1Btn = false;

                            clear();
                            TextView start =(TextView) rootView.findViewById(R.id.kezdoDatumText);
                            TextView end   =(TextView) rootView.findViewById(R.id.vegzoDatumText);
                            storeDataInArrays(start.getText().toString() , end.getText().toString() );

                        } else if(handleDateChooser2Btn == true ) {
                            vegzoDatumText.setText(date);
                            handleDateChooser2Btn = false;
                            kotojel.setText("-");

                            clear();
                            TextView start =(TextView) rootView.findViewById(R.id.kezdoDatumText);
                            TextView end   =(TextView) rootView.findViewById(R.id.vegzoDatumText);
                            storeDataInArrays(start.getText().toString() , end.getText().toString() );

                        }
                    }
                }, 100);
            }
        };



        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.floatingActionButtonTranzakciok);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getActivity(), AddActivity.class);
                startActivity(intent);
            }
        });

        dbhelper = new DatabaseHelper( getActivity() );
        id  = new ArrayList<>();
        TYPE  = new ArrayList<>();
        CATEGORY  = new ArrayList<>();
        VALUE  = new ArrayList<>();
        DATE  = new ArrayList<>();

        TextView start =(TextView) rootView.findViewById(R.id.kezdoDatumText);
        TextView end   =(TextView) rootView.findViewById(R.id.vegzoDatumText);
        storeDataInArrays(start.getText().toString() , end.getText().toString() );

        customAdapter = new CustomAdapter( getContext(), id, TYPE, CATEGORY, VALUE, DATE );
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager( getActivity() ));

        return rootView;
    }

    void clear(){
        id.clear();
        TYPE.clear();
        CATEGORY.clear();
        VALUE.clear();
        DATE.clear();
        customAdapter.notifyDataSetChanged();
    }

    void storeDataInArrays(String start, String end){
        Cursor cursor = dbhelper.readAllData(start, end);
        if(cursor.getCount() == 0) {
            empty_imageView.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        }else{
            while(cursor.moveToNext()){
                id.add(cursor.getString(0));
                TYPE.add(cursor.getString(1));
                CATEGORY.add(cursor.getString(2));
                VALUE.add(cursor.getString(3));
                DATE.add(cursor.getString(4));
            }
            empty_imageView.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        MenuInflater inflater_menu = getActivity().getMenuInflater();
        inflater_menu.inflate(R.menu.update_delete_menubar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete_all){
            DatabaseHelper db = new DatabaseHelper(getContext());
            db.deleteAllData();
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
