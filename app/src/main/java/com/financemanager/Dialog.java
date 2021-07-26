package com.financemanager;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import java.util.Calendar;

public class Dialog extends AppCompatDialogFragment {

    private DatePickerDialog.OnDateSetListener NapListener;
    private DatePickerDialog.OnDateSetListener InnenListener;
    private DatePickerDialog.OnDateSetListener IdeListener;
    private LayoutInflater inflater;
    private View view;
    private DialogListener listener;

    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.datum_popup, null);
        builder.setView(view).setTitle("Set date").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TextView nap_datum = view.findViewById(R.id.nap_datum);
                TextView ide_datum = view.findViewById(R.id.ide_datum);
                TextView innen_datum = view.findViewById(R.id.innen_datum);
                listener.applyTexts(nap_datum.getText().toString() , innen_datum.getText().toString() ,ide_datum.getText().toString() );


            }
        });


        // ----------------------------------- Nap ---------------------------------------
        NapListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = year + "/" + month  + "/" + dayOfMonth;
                setNap(date);
            }
        };
        ImageButton nap = view.findViewById(R.id.nap_btn);
        nap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(getContext(), NapListener, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                dpd.show();
            }
        });
        // ----------------------------------- Innen ---------------------------------------
        InnenListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = year + "/" + month  + "/" + dayOfMonth;
                setInnen(date);
            }
        };
        ImageButton innen = view.findViewById(R.id.innen_btn);
        innen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(getContext(), InnenListener, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                dpd.show();
            }
        });
        // ----------------------------------- Ide ---------------------------------------
        IdeListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = year + "/" + month  + "/" + dayOfMonth;
                setIde(date);
            }
        };
        ImageButton ide = view.findViewById(R.id.ide_btn);
        ide.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(getContext(), IdeListener, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                dpd.show();
            }
        });

        return builder.create();
    }

    public void setNap(String datum){
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                TextView nap_datum = view.findViewById(R.id.nap_datum);
                nap_datum.setText(datum);
            }
        }, 100);
    }

    public void setInnen(String datum){
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                TextView nap_datum = view.findViewById(R.id.innen_datum);
                nap_datum.setText(datum);
            }
        }, 100);
    }

    public void setIde(String datum){
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                TextView nap_datum = view.findViewById(R.id.ide_datum);
                nap_datum.setText(datum);
            }
        }, 100);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DialogListener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface DialogListener{
        void applyTexts(String nap, String innen, String ide);
    }
}
