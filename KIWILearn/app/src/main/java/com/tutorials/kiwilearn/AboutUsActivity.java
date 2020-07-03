package com.tutorials.kiwilearn;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

public class AboutUsActivity extends AppCompatActivity {

    private static String useraccount = null;
    private int theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle intent = getIntent().getExtras();
        theme = intent.getInt("Mode", R.style.LightNoActionBar);
        setTheme(theme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        ImageView icon_home = findViewById(R.id.imageView_nav_home);
        Glide.with(this).load(R.drawable.home).into(icon_home);
        ImageView icon_post = findViewById(R.id.imageView_nav_post);
        Glide.with(this).load(R.drawable.post).into(icon_post);
        ImageView icon_course = findViewById(R.id.imageView_nav_course);
        Glide.with(this).load(R.drawable.courses).into(icon_course);
        ImageView icon_me = findViewById(R.id.imageView_nav_me);
        Glide.with(this).load(R.drawable.me_selected).into(icon_me);

        int icon_color = this.getTheme().obtainStyledAttributes(R.styleable.ViewStyle).getColor(R.styleable.ViewStyle_colorPrimaryContents, Color.parseColor("#000000"));
        icon_home.setColorFilter(icon_color);
        icon_post.setColorFilter(icon_color);
        icon_course.setColorFilter(icon_color);
        icon_me.setColorFilter(icon_color);


        ImageView imageView_company = findViewById(R.id.imageView_companylogo);
        ImageView imageView_small = findViewById(R.id.imageView_applogo_small);

        if(theme == R.style.NightNoActionBar){
            imageView_company.setImageResource(R.drawable.companylogo_night);
            imageView_small.setImageResource(R.drawable.applogo_night);
        }
        else if(theme == R.style.LightNoActionBar){
            imageView_company.setImageResource(R.drawable.companylogo);
            imageView_small.setImageDrawable(getResources().getDrawable(R.drawable.applogo));
        }


        TextView title = findViewById(R.id.textView_title);
        title.setText("About Us");
        useraccount = intent.getString("userAccount");

    }

    public void onClickHome(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("Mode", theme);
        bundle.putString("userAccount", useraccount);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onClickPost(View view) {
        Intent intent = new Intent(this, PostActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("Mode", theme);
        bundle.putString("userAccount", useraccount);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onClickCourse(View view) {
        Intent intent = new Intent(this, CoursesActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("Mode", theme);
        bundle.putString("userAccount", useraccount);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onClickMe(View view) {
        Intent intent = new Intent(this, MeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("Mode", theme);
        bundle.putString("userAccount", useraccount);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
