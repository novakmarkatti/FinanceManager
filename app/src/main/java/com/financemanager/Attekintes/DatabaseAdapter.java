package com.financemanager.Attekintes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.financemanager.R;
import java.util.ArrayList;

public class DatabaseAdapter extends RecyclerView.Adapter<com.financemanager.Attekintes.DatabaseAdapter.AttekintesViewHolder>{

    private Context context;
    private ArrayList  CATEGORY, VALUE;

    public DatabaseAdapter(Context context, ArrayList CATEGORY, ArrayList VALUE) {
        this.CATEGORY = CATEGORY;
        this.VALUE = VALUE;
        this.context = context;
    }

    @NonNull
    @Override
    public com.financemanager.Attekintes.DatabaseAdapter.AttekintesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.attekintes_item, parent, false);
        return new com.financemanager.Attekintes.DatabaseAdapter.AttekintesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull com.financemanager.Attekintes.DatabaseAdapter.AttekintesViewHolder holder, int position) {
        holder.CATEGORY_elem.setText(String.valueOf(CATEGORY.get(position)));
        holder.VALUE_elem.setText(String.valueOf(VALUE.get(position)));
    }

    @Override
    public int getItemCount() {
        return CATEGORY.size();
    }

    public class AttekintesViewHolder extends RecyclerView.ViewHolder{

        private TextView CATEGORY_elem, VALUE_elem;

        public AttekintesViewHolder(@NonNull View itemView) {
            super(itemView);
            CATEGORY_elem   =itemView.findViewById(R.id.kategoria);
            VALUE_elem      =itemView.findViewById(R.id.ertek);
        }
    }


}








