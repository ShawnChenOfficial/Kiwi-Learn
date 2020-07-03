package com.tutorials.kiwilearn;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MeActivity extends AppCompatActivity {

    private static String useraccount = null;
    private static final String DBDRIVER = "com.mysql.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://192.168.80.240:3306/shawn";
    private static final String DBUID = "shawn";
    private static final String DBPWD = "secret";
    private int theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        theme = bundle.getInt("Mode",R.style.LightNoActionBar);
        setTheme(theme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);

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

        ImageView user_icon = findViewById(R.id.imageView_in_body_top_icon);
        Glide.with(this).load(R.drawable.user).into(user_icon);
        ImageView icon_postHistory = findViewById(R.id.imageView_in_content1_postsHistory);
        Glide.with(this).load(R.drawable.user).into(icon_postHistory);
        ImageView icon_setting = findViewById(R.id.imageView_in_content2_setting);
        Glide.with(this).load(R.drawable.user).into(icon_setting);
        ImageView icon_aboutUs = findViewById(R.id.imageView_in_content3_aboutus);
        Glide.with(this).load(R.drawable.user).into(icon_aboutUs);
        ImageView icon_policies = findViewById(R.id.imageView_in_content3_policies);
        Glide.with(this).load(R.drawable.user).into(icon_policies);

        ImageView imageView_small = findViewById(R.id.imageView_applogo_small);

        if(theme == R.style.NightNoActionBar){
            imageView_small.setImageResource(R.drawable.applogo_night);
        }
        else if(theme == R.style.LightNoActionBar){
            imageView_small.setImageDrawable(getResources().getDrawable(R.drawable.applogo));
        }

        TextView title = findViewById(R.id.textView_title);
        title.setText("Me");

        useraccount = bundle.getString("userAccount");

        final TextView usernameTextView = findViewById(R.id.textView_body_top_username);

        final TextView emailTextView = findViewById(R.id.textView_body_top_email);

        try {
            Class.forName(DBDRIVER);
            Connection conn = DriverManager.getConnection(DBURL, DBUID, DBPWD);
            PreparedStatement preparedStatement = conn.prepareStatement("select * from User where UserAccount = ?");
            preparedStatement.setString(1, useraccount);
            final ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                final String email_in_DB =resultSet.getString(5);
                final String username_in_DB = resultSet.getString(2).toString();
                final String uid_in_DB = resultSet.getString(1);//should be the nick name

                Thread thread = new Thread() {
                    @Override
                    public void run() {

                        if (username_in_DB != null && !username_in_DB.equals("")) {
                            usernameTextView.setText(username_in_DB);
                            emailTextView.setText(email_in_DB);
                        }
                        else {
                            usernameTextView.setText(uid_in_DB);
                            emailTextView.setText(email_in_DB);
                        }
                    }
                };
                thread.start();
            }
            conn.close();
        } catch (Exception e) {
            Log.v("A", e.getMessage());
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

    public void onClickAboutUs(View view) {
        Intent intent = new Intent(MeActivity.this, AboutUsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("Mode",theme);
        bundle.putString("userAccount", useraccount);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onClickPolicies(View view) {
        Intent intent = new Intent(MeActivity.this, PoliciesActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("Mode",theme);
        bundle.putString("userAccount", useraccount);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onClickSetting(View view) {
        Intent intent = new Intent(MeActivity.this, SettingActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("Mode",theme);
        bundle.putString("userAccount", useraccount);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onClickProfile(View view) {
        Intent intent = new Intent(MeActivity.this, ProfileActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("Mode",theme);
        bundle.putString("userAccount", useraccount);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onClickEditProfile(View view) {
        Intent intent = new Intent(MeActivity.this, EditProfitActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("Mode",theme);
        bundle.putString("userAccount", useraccount);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onClickLogOut(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("Mode",theme);
        bundle.putString("userAccount", useraccount);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void onClickPostHistory(View view){
        Intent intent = new Intent(this, PostHistoryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("Mode",theme);
        bundle.putString("userAccount", useraccount);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
