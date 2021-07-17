package com.financemanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tranzakciok, container, false);


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
        storeDataInArrays();

        customAdapter = new CustomAdapter(getContext(), id, TYPE, CATEGORY, VALUE, DATE );
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager( getActivity() ));

        return rootView;
    }

    void storeDataInArrays(){
        Cursor cursor = dbhelper.readAllData();
        if(cursor.getCount() == 0) {
            Toast.makeText( getContext(), "No data", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                id.add(cursor.getString(0));
                TYPE.add(cursor.getString(1));
                CATEGORY.add(cursor.getString(2));
                VALUE.add(cursor.getString(3));
                DATE.add(cursor.getString(4));
            }
        }
    }
}
