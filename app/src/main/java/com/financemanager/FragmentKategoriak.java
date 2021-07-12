package com.financemanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentKategoriak extends Fragment {

    Button fragment1btn, fragment2btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


/*
        fragment2btn = findViewById(R.id.fragment2btn);
        fragment2btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment( new FragmentKategoriakMasodik() );
            }
        });

        fragment1btn = findViewById(R.id.fragment1btn);
        fragment1btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment( new FragmentKategoriakElso() );
            }
        });
*/

        return inflater.inflate(R.layout.fragment_kategoriak, container, false);
        //return super.onCreateView(inflater, container, savedInstanceState);
    }
/*
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
*/
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}
