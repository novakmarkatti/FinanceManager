package com.financemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList id, TYPE, CATEGORY, VALUE, DATE;

    CustomAdapter(Context context, ArrayList id, ArrayList TYPE, ArrayList CATEGORY, ArrayList VALUE, ArrayList DATE  ){
        this.context = context;
        this.id = id;
        this.TYPE = TYPE;
        this.CATEGORY = CATEGORY;
        this.VALUE = VALUE;
        this.DATE = DATE;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.CATEGORY_elem.setText(String.valueOf(CATEGORY.get(position)));
        holder.VALUE_elem.setText(String.valueOf(VALUE.get(position)));

        holder.id_elem.setText(String.valueOf(id.get(position)));
        holder.TYPE_elem.setText(String.valueOf(TYPE.get(position)));
        holder.DATE_elem.setText(String.valueOf(DATE.get(position)));
    }

    @Override
    public int getItemCount() {
        return CATEGORY.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView id_elem, TYPE_elem, CATEGORY_elem, VALUE_elem, DATE_elem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id_elem=itemView.findViewById(R.id.id_elem);
            CATEGORY_elem=itemView.findViewById(R.id.category_elem);
            VALUE_elem=itemView.findViewById(R.id.value_elem);
            TYPE_elem=itemView.findViewById(R.id.type_elem);
            DATE_elem=itemView.findViewById(R.id.date_elem);
        }
    }
}
