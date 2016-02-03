package com.example.muvr.muvr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.example.muvr.R;

public class MuvrActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muvr);
        ImageView img_animation = (ImageView) findViewById(R.id.img_animation);

        TranslateAnimation animation = new TranslateAnimation(0.0f, 150.0f, 0.0f, 0.0f);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animation.setDuration(1000);  // animation duration
        animation.setRepeatCount(0);  // animation repeat count
        // repeat animation (left to right, right to left )
        animation.setFillAfter(true);

        img_animation.startAnimation(animation);  // start animation


        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(3050);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Intent i = new Intent("com.example.muvr.MAINACTIVITY");
                    startActivity(i);
                    finish();
                }
            }
        };
        timer.start();
    }
}
