package com.tutorials.kiwilearn;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import junit.framework.Test;

import java.util.Set;


public class SettingActivity extends AppCompatActivity {

    private String useraccount;
    private int theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final Bundle bundle = getIntent().getExtras();
        theme = bundle.getInt("Mode", R.style.LightNoActionBar);
        setTheme(theme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

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


        ImageView imageView_small = findViewById(R.id.imageView_applogo_small);

        if (theme == R.style.NightNoActionBar) {
            imageView_small.setImageResource(R.drawable.applogo_night);
        } else if (theme == R.style.LightNoActionBar) {
            imageView_small.setImageDrawable(getResources().getDrawable(R.drawable.applogo));
        }

        TextView title = findViewById(R.id.textView_title);
        title.setText("Setting");
        useraccount = bundle.getString("userAccount");

        /*Initial the dropdown menu of language*/
        final Spinner spinner = (Spinner) findViewById(R.id.spinner_in_content3_language_dropdown);

        String[] items = this.getResources().getStringArray(R.array.setting_language);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.drop_down_textview, items);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.drop_down_textview);
        spinner.setAdapter(spinnerArrayAdapter);

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        final RadioButton light = findViewById(R.id.radioButton_light);
        final RadioButton dark = findViewById(R.id.radioButton_dark);

        if (theme == R.style.LightNoActionBar) {
            light.setChecked(true);
            dark.setChecked(false);
        } else if (theme == R.style.NightNoActionBar) {
            light.setChecked(false);
            dark.setChecked(true);
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View checkView = radioGroup.findViewById(i);
                if (!checkView.isPressed()) {
                    return;
                } else {
                    if (theme == checkView.getId()) {
                        return;
                    } else {
                        if (checkView.getId() == light.getId()) {
                            light.setChecked(true);
                            dark.setChecked(false);
                            theme = R.style.LightNoActionBar;
                        } else if (checkView.getId() == dark.getId()) {
                            light.setChecked(false);
                            dark.setChecked(true);
                            theme = R.style.NightNoActionBar;
                        }
                        setTheme(theme);
                        Intent intent = new Intent(SettingActivity.this, SettingActivity.class);
                        Bundle bundle1 = new Bundle();
                        bundle1.putInt("Mode", theme);
                        bundle1.putString("userAccount", useraccount);
                        intent.putExtras(bundle1);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        SettingActivity.this.startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }

    public void onClickHome(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("userAccount", useraccount);
        bundle.putInt("Mode", theme);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onClickPost(View view) {
        Intent intent = new Intent(this, PostActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("userAccount", useraccount);
        bundle.putInt("Mode", theme);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onClickCourse(View view) {
        Intent intent = new Intent(this, CoursesActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("userAccount", useraccount);
        bundle.putInt("Mode", theme);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onClickMe(View view) {
        Intent intent = new Intent(this, MeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("userAccount", useraccount);
        bundle.putInt("Mode", theme);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onClickChangePassword(View view) {
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("userAccount", useraccount);
        bundle.putInt("Mode", theme);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
