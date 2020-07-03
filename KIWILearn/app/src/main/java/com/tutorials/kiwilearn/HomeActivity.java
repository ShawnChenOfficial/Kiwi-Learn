package com.tutorials.kiwilearn;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.percent.PercentLayoutHelper;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class HomeActivity extends AppCompatActivity {

    private static String useraccount = null;
    private int theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle intent = getIntent().getExtras();
        theme = intent.getInt("Mode",R.style.LightNoActionBar);
        setTheme(theme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ImageView icon_home = findViewById(R.id.imageView_nav_home);
        Glide.with(this).load(R.drawable.home_selected).into(icon_home);
        ImageView icon_post = findViewById(R.id.imageView_nav_post);
        Glide.with(this).load(R.drawable.post).into(icon_post);
        ImageView icon_course = findViewById(R.id.imageView_nav_course);
        Glide.with(this).load(R.drawable.courses).into(icon_course);
        ImageView icon_me = findViewById(R.id.imageView_nav_me);
        Glide.with(this).load(R.drawable.me).into(icon_me);

        int icon_color = this.getTheme().obtainStyledAttributes(R.styleable.ViewStyle).getColor(R.styleable.ViewStyle_colorPrimaryContents, Color.parseColor("#000000"));
        icon_home.setColorFilter(icon_color);
        icon_post.setColorFilter(icon_color);
        icon_course.setColorFilter(icon_color);
        icon_me.setColorFilter(icon_color);

        ImageView imageView_small = findViewById(R.id.imageView_applogo_small);

        if(theme == R.style.NightNoActionBar){
            imageView_small.setImageResource(R.drawable.applogo_night);
        }
        else if(theme == R.style.LightNoActionBar){
            imageView_small.setImageDrawable(getResources().getDrawable(R.drawable.applogo));
        }

        TextView title = findViewById(R.id.textView_title);
        title.setText("Kiwi Learn");


        PercentRelativeLayout layout = findViewById(R.id.layout_body);
        ImageView imageViewADS = new ImageView(this);
        imageViewADS.setId(View.generateViewId());
        PercentRelativeLayout.LayoutParams params = new PercentRelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(PercentRelativeLayout.ALIGN_PARENT_TOP);
        PercentLayoutHelper.PercentLayoutInfo info = params.getPercentLayoutInfo();
        info.widthPercent = 1.00f;
        info.heightPercent = 0.35f;
        imageViewADS.setBackgroundResource(R.drawable.ads);
        imageViewADS.setLayoutParams(params);
        layout.addView(imageViewADS);

        useraccount = intent.getString("userAccount");

    }

    public void onClickHome(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("userAccount",useraccount);
        bundle.putInt("Mode",theme);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void onClickPost(View view){
        Intent intent = new Intent(this, PostActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("userAccount",useraccount);
        bundle.putInt("Mode",theme);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void onClickCourse(View view){
        Intent intent = new Intent(this,CoursesActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("userAccount",useraccount);
        bundle.putInt("Mode",theme);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void onClickMe(View view){
        Intent intent = new Intent(this, MeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("userAccount",useraccount);
        bundle.putInt("Mode",theme);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void searchCourse(View view) {
        String message = "111";
        Button button;
        switch ((int)view.getId()) {
            case (int)R.id.button1:
                button = findViewById(R.id.button1);
                message = button.getText().toString();
                break;
            case (int)R.id.button2:
                button = findViewById(R.id.button2);
                message = button.getText().toString();
                break;
            case (int)R.id.button3:
                button = findViewById(R.id.button3);
                message = button.getText().toString();
                break;
            case (int)R.id.button4:
                button = findViewById(R.id.button4);
                message = button.getText().toString();
                break;
            case (int)R.id.button5:
                button = findViewById(R.id.button5);
                message = button.getText().toString();
                break;
            case (int)R.id.button6:
                button = findViewById(R.id.button6);
                message = button.getText().toString();
                break;
        }
        Intent intent = new Intent(HomeActivity.this, CoursesActivity.class);
        Bundle intentString = new Bundle();
        intentString.putString("Category",message);
        intentString.putString("userAccount",useraccount);
        intentString.putInt("Mode",theme);
        intent.putExtras(intentString);
        startActivity(intent);
    }
}
