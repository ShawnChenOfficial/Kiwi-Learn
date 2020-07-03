package com.tutorials.kiwilearn;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChangePasswordActivity extends AppCompatActivity {

    private static String useraccount = null;
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
        setContentView(R.layout.activity_change_password);

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

        if(theme == R.style.NightNoActionBar){
            imageView_small.setImageResource(R.drawable.applogo_night);
        }
        else if(theme == R.style.LightNoActionBar){
            imageView_small.setImageDrawable(getResources().getDrawable(R.drawable.applogo));
        }

        useraccount = intent.getString("userAccount");

        TextView title = findViewById(R.id.textView_title);
        title.setText("Change Password");

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

        try {
            Class.forName(DBDRIVER);
            Connection conn = DriverManager.getConnection(DBURL, DBUID, DBPWD);
            PreparedStatement preparedStatement = conn.prepareStatement("select * from Login where LoginID = ? and UserAccount = ?");
            preparedStatement.setString(1, useraccount);
            preparedStatement.setString(2, useraccount);
            ResultSet resultSet = preparedStatement.executeQuery();

            EditText old_password = findViewById(R.id.editText_change_password_password_input);
            String old_password_value = old_password.getText().toString();
            EditText new_password_1 = findViewById(R.id.editText_change_new_password_input);
            String new_password_1_value = new_password_1.getText().toString();
            EditText new_password_2 = findViewById(R.id.editText_re_new_password_input);
            String new_password_2_values = new_password_2.getText().toString();

            if (!new_password_1_value.equals(new_password_2_values)) {
                Handler handler = new Handler(getMainLooper()) {
                    @Override
                    public void handleMessage(Message msg) {
                        Toast.makeText(getApplicationContext(), "Your new passwords are not matching", Toast.LENGTH_LONG).show();
                    }
                };
                handler.sendEmptyMessage(0);
            } else if (new_password_1_value.equals(new_password_2_values)) {
                if (resultSet.next()) {
                    String useraccount_in_db = resultSet.getString(1);
                    String userpassword_in_db = resultSet.getString(3);
                    if (useraccount_in_db.equals(useraccount) && userpassword_in_db.equals(old_password_value)) {
                        preparedStatement = conn.prepareStatement("update Login set Password = ? where LoginID = ? and UserAccount = ?");
                        preparedStatement.setString(1, new_password_1_value);
                        preparedStatement.setString(2, useraccount);
                        preparedStatement.setString(3, useraccount);
                        preparedStatement.executeUpdate();

                        Handler handler = new Handler(getMainLooper()) {
                            @Override
                            public void handleMessage(Message msg) {
                                Toast.makeText(getApplicationContext(), "Please log in with your new password", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putInt("Mode",theme);
                                bundle.putString("userAccount", useraccount);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        };
                        handler.sendEmptyMessage(0);
                    }
                    else if (!userpassword_in_db.equals(old_password_value)){
                        Handler handler = new Handler(getMainLooper()) {
                            @Override
                            public void handleMessage(Message msg) {
                                Toast.makeText(getApplicationContext(), "Incorrect password", Toast.LENGTH_LONG).show();
                            }
                        };
                        handler.sendEmptyMessage(0);
                    }
                }
            }

        } catch (SQLException e) {
            Log.v("A", e.getMessage() + e.getCause());
        } catch (ClassNotFoundException e) {

        }
    }
}
