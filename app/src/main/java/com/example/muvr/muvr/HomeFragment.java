package com.example.muvr.muvr;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.muvr.R;

import java.util.ArrayList;

/**
 * Created by HP 15 on 1/31/2016.
 */

public class HomeFragment extends Fragment {

        public HomeFragment(){}


    String[] city = {"Ahmedabad", "Surat", "Vadodra", "Gandhinagar"};
    Button b;
    String cityName = "Ahmedabad";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            final View rootView = inflater.inflate(R.layout.activity_welcome, container, false);
            getActivity().getActionBar().show();
            Spinner spin = (Spinner)rootView.findViewById(R.id.spinner1);
            spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    cityName = city[position];
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            //Creating the ArrayAdapter instance having the city list
            ArrayAdapter aa = new ArrayAdapter(rootView.getContext(), android.R.layout.simple_spinner_item, city);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //Setting the ArrayAdapter data on the Spinner
            spin.setAdapter(aa);
            b = (Button) rootView.findViewById(R.id.muvr_address);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(rootView.getContext(), AddressActivity.class);
                    i.putExtra("userdata", cityName);
                    startActivity(i);
                }
            });


            //getActivity().getActionBar().hide();

            return rootView;
        }
    /*@Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // TODO Auto-generated method stub
        //Toast.makeText(getApplicationContext(),city[position] ,Toast.LENGTH_LONG).show();
        cityName = city[position];

    }
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        Intent i = new Intent(this, AddressActivity.class);
        i.putExtra("userdata", cityName);
        startActivity(i);

    }*/


    }


