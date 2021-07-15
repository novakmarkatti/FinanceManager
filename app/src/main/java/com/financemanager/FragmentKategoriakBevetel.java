package com.financemanager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class FragmentKategoriakBevetel extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_kategoriak_bevetel, container, false);

        final Button button =(Button) rootView.findViewById(R.id.kettes);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Navigation.findNavController(rootView).navigate(R.id.action_fragmentKategoriakBevetel_to_fragmentKategoriak);
            }
        });

        return rootView;
    }


}