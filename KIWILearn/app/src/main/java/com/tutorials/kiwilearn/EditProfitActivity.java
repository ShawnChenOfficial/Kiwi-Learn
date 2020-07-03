package com.tutorials.kiwilearn;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EditProfitActivity extends AppCompatActivity {

    private static String useraccount;
    private static final String DBDRIVER = "com.mysql.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://192.168.80.240:3306/shawn";
    private static final String DBUID = "shawn";
    private static final String DBPWD = "secret";
    private int theme;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Bundle intent = getIntent().getExtras();
        theme = intent.getInt("Mode",R.style.LightNoActionBar);
        setTheme(theme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profit);

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


        ImageView icon_user = findViewById(R.id.imageView_user_icon);
        Glide.with(this).load(R.drawable.user).into(icon_user);

        ImageView imageView_small = findViewById(R.id.imageView_applogo_small);

        if(theme == R.style.NightNoActionBar){
            imageView_small.setImageResource(R.drawable.applogo_night);
        }
        else if(theme == R.style.LightNoActionBar){
            imageView_small.setImageDrawable(getResources().getDrawable(R.drawable.applogo));
        }

        TextView title = findViewById(R.id.textView_title);
        title.setText("Edit Profile");

        useraccount = intent.getString("userAccount");

        final EditText new_nickname_EditText = findViewById(R.id.editText_user_name);
        final RadioButton new_gender_female_RadioButton = findViewById(R.id.radioButton_female);
        final RadioButton new_gender_male_RadioButton = findViewById(R.id.radioButton_male);
        final EditText new_region_EditText = findViewById(R.id.editText_user_region);
        final EditText new_email_EditText = findViewById(R.id.editText_user_email);

        try {
            Class.forName(DBDRIVER);
            Connection conn = DriverManager.getConnection(DBURL, DBUID, DBPWD);
            PreparedStatement preparedStatement = conn.prepareStatement("select * from User Where UserAccount = ?");
            preparedStatement.setString(1, useraccount);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                final String nickname = resultSet.getString(2);
                final String gender = resultSet.getString(3);
                final String region = resultSet.getString(4);
                final String email = resultSet.getString(5);

                Handler handler = new Handler(getMainLooper()){
                    @Override
                    public void handleMessage(Message msg){

                        new_nickname_EditText.setText(nickname);

                        if(gender.equals("Female")){
                            new_gender_female_RadioButton.setChecked(true);
                        }
                        else if (gender.equals("Male")){
                            new_gender_male_RadioButton.setChecked(true);
                        }
                        new_region_EditText.setText(region);
                        new_email_EditText.setText(email);
                    }
                };
                handler.sendEmptyMessage(0);
            }
        }
        catch (Exception e) {
            Log.v("B", e.getMessage());
        }

    }

    public void onClickHome(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("Mode",theme);
        bundle.putString("userAccount", useraccount);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onClickPost(View view) {
        Intent intent = new Intent(this, PostActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("Mode",theme);
        bundle.putString("userAccount", useraccount);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onClickCourse(View view) {
        Intent intent = new Intent(this, CoursesActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("Mode",theme);
        bundle.putString("userAccount", useraccount);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onClickMe(View view) {
        Intent intent = new Intent(this, MeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("Mode",theme);
        bundle.putString("userAccount", useraccount);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onClickSubmit(View view) {

        final EditText new_nickname_EditText = findViewById(R.id.editText_user_name);
        final RadioButton new_gender_female_RadioButton = findViewById(R.id.radioButton_female);
        final RadioButton new_gender_male_RadioButton = findViewById(R.id.radioButton_male);
        final EditText new_region_EditText = findViewById(R.id.editText_user_region);
        final EditText new_email_EditText = findViewById(R.id.editText_user_email);

        String new_nickname = new_nickname_EditText.getText().toString();
        String new_region = new_region_EditText.getText().toString();
        String new_email = new_email_EditText.getText().toString();
        String new_gender = "";
        if (new_gender_female_RadioButton.isChecked()) {
            new_gender = "Female";
        } else if (new_gender_male_RadioButton.isChecked()) {
            new_gender = "Male";
        }

        try {
            Class.forName(DBDRIVER);
            Connection conn = DriverManager.getConnection(DBURL, DBUID, DBPWD);
            PreparedStatement preparedStatement = conn.prepareStatement("update User set Nickname=?,Gender = ?, Region = ? ,Email = ? Where UserAccount = ?");
            preparedStatement.setString(1, new_nickname);
            preparedStatement.setString(2, new_gender);
            preparedStatement.setString(3, new_region);
            preparedStatement.setString(4, new_email);
            preparedStatement.setString(5, useraccount);
            preparedStatement.executeUpdate();

            Handler handler = new Handler(getMainLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    try {
                        Intent intent = new Intent(EditProfitActivity.this, ProfileActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("Mode",theme);
                        bundle.putString("userAccount", useraccount);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } catch (Exception e) {
                        Log.v("A", e.getMessage());
                    }
                }
            };
            handler.sendEmptyMessage(0);

        } catch (Exception e) {
            Log.v("B", e.getMessage());
        }
    }
}
