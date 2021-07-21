package com.financemanager;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.financemanager.Attekintes.DatabaseAdapter;
import com.financemanager.Tranzakciok.CustomAdapter;
import com.financemanager.Tranzakciok.DatabaseHelper;

import java.util.ArrayList;

public class FragmentAttekintes extends Fragment {

    private DatabaseHelper dbhelper;
    private SQLiteDatabase db;
    private ArrayList<String> CATEGORY;
    private ArrayList<String> VALUE;
    private DatabaseAdapter customAdapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_attekintes, container, false);
/*
        DatabaseAdapter databaseAdapter = new DatabaseAdapter(getContext());
        ListView listView = rootView.findViewById(R.id.listViewAttekintes);
        SimpleCursorAdapter simpleCursorAdapter = databaseAdapter.populateListView();
        listView.setAdapter(simpleCursorAdapter);
*/

        dbhelper = new DatabaseHelper( getActivity() );
        db = dbhelper.getReadableDatabase();
        CATEGORY  = new ArrayList<>();
        VALUE  = new ArrayList<>();

        storeData();

        customAdapter = new DatabaseAdapter( getContext(), CATEGORY, VALUE );
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewAttekintes);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager( getActivity() ));

        return rootView;
    }

    void clear(){
        CATEGORY.clear();
        VALUE.clear();
        customAdapter.notifyDataSetChanged();
    }

    void storeData(){
        Cursor cursor = db.rawQuery("SELECT category,SUM(value) FROM " + DatabaseHelper.TABLE_NAME + " group by category", null);
        if(cursor.getCount() != 0) {
            while(cursor.moveToNext()){
                CATEGORY.add(cursor.getString(0));
                VALUE.add(cursor.getString(1));
            }
        }
    }

}





