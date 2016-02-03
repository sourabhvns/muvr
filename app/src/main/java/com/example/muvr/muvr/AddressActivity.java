package com.example.muvr.muvr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.muvr.R;

public class AddressActivity extends Activity {

	TextView tv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_address);
		Intent ri=getIntent();
		Bundle b=ri.getExtras();
		String rcity=b.getString("userdata");
		tv=(TextView)findViewById(R.id.muvr_cityName);
		tv.setText(rcity);

		Button muvr_go=(Button)findViewById(R.id.muvr_go);
		muvr_go.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText muvr_editstart = (EditText) findViewById(R.id.muvr_editStart);
				EditText editend = (EditText) findViewById(R.id.editEnd);
				if (muvr_editstart.getText().toString().isEmpty()) {
					Toast.makeText(getApplicationContext(), "Enter a start location", Toast.LENGTH_LONG).show();
					return;
				}
				if (editend.getText().toString().isEmpty()) {
					Toast.makeText(getApplicationContext(), "Enter end location", Toast.LENGTH_LONG).show();
					return;
				}

				Intent i=new Intent(getApplicationContext(),MapsActivity.class);
				i.putExtra("start",muvr_editstart.getText().toString());
				i.putExtra("end",editend.getText().toString());
				startActivity(i);
			}
		});
	}
}
