package com.example.muvr.muvr;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.muvr.R;

/**
 * Created by HP 15 on 2/2/2016.
 */
public class Contact extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_contact, container, false);

        rootView.findViewById(R.id.rl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:23.1068628,72.6039641?q=23.1068628,72.6039641(MUVR Technology PVT. Limited)"));
                startActivity(intent);

            }
        });

        return rootView;
    }
}
