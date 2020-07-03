package com.tutorials.kiwilearn;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView logo_app = findViewById(R.id.ImageView_welcomepage_applogo);
        Glide.with(this).load(R.drawable.applogo).into(logo_app);
        ImageView logo_company = findViewById(R.id.imageView_welcomepage_companylogo);
        Glide.with(this).load(R.drawable.companylogo).into(logo_company);


        if (android.os.Build.VERSION.SDK_INT > 9) {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);

             }

        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent start = new Intent(MainActivity.this, LoginActivity.class);
                start.putExtra("Mode",R.style.LightNoActionBar);
                startActivity(start);
            }
        }, 3000);

    }
}
