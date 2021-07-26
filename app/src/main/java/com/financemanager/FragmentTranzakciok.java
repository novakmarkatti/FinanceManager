package com.financemanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.financemanager.Tranzakciok.AddActivity;
import com.financemanager.Tranzakciok.CustomAdapter;
import com.financemanager.Tranzakciok.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

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
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tranzakciok, container, false);

        sharedPreferences = getContext().getSharedPreferences("Datum", Context.MODE_PRIVATE);
        String datum_tranzakciok = sharedPreferences.getString("date", "");

        empty_imageView = (ImageView) rootView.findViewById(R.id.no_data_imageview);
        no_data         = (TextView) rootView.findViewById(R.id.no_data_textview);

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

        storeDataInArrays(datum_tranzakciok);
        customAdapter = new CustomAdapter( getContext(), id, TYPE, CATEGORY, VALUE, DATE );
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager( getActivity() ));

        return rootView;
    }

    public void storeDataInArrays( String date){
        Cursor cursor = dbhelper.readAllData(date);
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

}
