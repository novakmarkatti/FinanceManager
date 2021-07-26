package com.financemanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.financemanager.Attekintes.DatabaseAdapter;
import com.financemanager.Tranzakciok.DatabaseHelper;
import java.util.ArrayList;

public class FragmentAttekintes extends Fragment{

    private SQLiteDatabase db;
    private ArrayList<String> CATEGORY;
    private ArrayList<String> VALUE;
    private DatabaseAdapter customAdapter;
    private RecyclerView recyclerView;
    private int egyenleg  = 0;
    private int kiadasok  = 0;
    private int bevetelek = 0;
    private View rootView;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_attekintes, container, false);

        sharedPreferences = getContext().getSharedPreferences("Datum", Context.MODE_PRIVATE);
        String datum_tranzakciok = sharedPreferences.getString("date", "");

        DatabaseHelper dbhelper = new DatabaseHelper( getActivity() );
        db = dbhelper.getReadableDatabase();
        CATEGORY  = new ArrayList<>();
        VALUE  = new ArrayList<>();

        storeData(datum_tranzakciok);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                TextView egyenleg_text = (TextView) rootView.findViewById(R.id.egyenleg_attekintes_value);
                TextView kiadasok_text = (TextView) rootView.findViewById(R.id.kiadasok_attekintes_value);
                TextView bevetelek_text = (TextView) rootView.findViewById(R.id.bevetelek_attekintes_value);
                egyenleg_text.setText(String.valueOf(egyenleg)  );
                kiadasok_text.setText(String.valueOf(kiadasok)  );
                bevetelek_text.setText(String.valueOf(bevetelek) );
            }
        }, 100);


        customAdapter = new DatabaseAdapter( getContext(), CATEGORY, VALUE );
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewAttekintes);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager( getActivity() ));

        return rootView;
    }

    void storeData(String date){
        String query;
        if( !date.equals("") ) {
            if( date.contains("-") ){
                String[] param = date.split("-");
                query = "SELECT category,SUM(value) FROM ( " +
                        "SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE date between \"" + param[0] + "\" and \"" + param[1] + "\" order by date DESC"
                        + " ) group by category order by SUM(value)";
            } else {
                query = "SELECT category,SUM(value) FROM ( " +
                        "SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE date==\"" + date + "\" order by date DESC"
                        + " ) group by category order by SUM(value)";
            }
        } else {
            query = "SELECT category,SUM(value) FROM " + DatabaseHelper.TABLE_NAME + " group by category order by SUM(value)";
        }

        Cursor cursor = db.rawQuery(query, null);
        if(cursor.getCount() != 0) {
            while(cursor.moveToNext()){
                String category_temp = cursor.getString(0);
                CATEGORY.add(category_temp);
                String value_temp = cursor.getString(1);
                VALUE.add(value_temp);

                int temp = Integer.parseInt(value_temp);
                this.egyenleg += temp;
                if(temp < 0) {
                    this.kiadasok += temp;
                } else if(temp > 0) {
                    this.bevetelek += temp;
                }
            }
        }
    }


}





