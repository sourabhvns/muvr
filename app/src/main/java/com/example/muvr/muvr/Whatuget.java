package com.example.muvr.muvr;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.muvr.R;

/**
 * Created by HP 15 on 2/2/2016.
 */
public class Whatuget extends Fragment {

    public Whatuget() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_whatuget, container, false);

        //getActivity().getActionBar().hide();

        return rootView;
    }
}
