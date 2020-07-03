package com.tutorials.kiwilearn;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends AppCompatActivity {

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
        Intent intent = getIntent();
        theme = intent.getIntExtra("Mode",R.style.LightNoActionBar);
        setTheme(theme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageView imageView_small = findViewById(R.id.imageView_applogo_small);
        ImageView imageView_large = findViewById(R.id.imageView_applogo_large);

        if(theme == R.style.NightNoActionBar){
            imageView_small.setImageResource(R.drawable.applogo_night);
            imageView_large.setImageResource(R.drawable.applogo_night);
        }
        else if(theme == R.style.LightNoActionBar){
            imageView_small.setImageDrawable(getResources().getDrawable(R.drawable.applogo));
            imageView_large.setImageDrawable(getResources().getDrawable(R.drawable.applogo));
        }


        TextView title = findViewById(R.id.textView_title);
        title.setText("Log In");

        Button login = findViewById(R.id.button_submit);

        final EditText account_input = findViewById(R.id.editText_login_account_input);
        final EditText password_input = findViewById(R.id.editText_login_password_input);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String account_line = account_input.getText().toString();
                final String password_line = password_input.getText().toString();
                if (!account_line.equals("") && !password_line.equals("")){
                    Thread thread = new Thread(){
                        public void run(){
                            getCheckAccount(account_line,password_line);
                        }
                    };
                    thread.start();
                }
                else if ( account_line.equals("") ){
                    Handler handler = new Handler(getMainLooper()){
                        @Override
                        public void handleMessage(Message msg){
                            Toast.makeText(getApplicationContext(),"Account cannot be empty!",Toast.LENGTH_LONG).show();
                        }
                    };
                    handler.sendEmptyMessage(0);
                }
                else if (password_line.equals("")){
                    Handler handler = new Handler(getMainLooper()){
                        @Override
                        public void handleMessage(Message msg){
                            Toast.makeText(getApplicationContext(),"Password cannot be empty!",Toast.LENGTH_LONG).show();
                        }
                    };
                    handler.sendEmptyMessage(0);
                }
            }
        });
    }
    public void onClickSignUp(View view){
        Intent start = new Intent(LoginActivity.this, CreateAccountActivity.class);
        start.putExtra("Mode",theme);
        startActivity(start);
    }

    public void getCheckAccount(final String account, final String password) {
        Connection conn;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DBURL, DBUID, DBPWD);
            Log.i("A", "Connection Successful");
            PreparedStatement preparedStatement  = conn.prepareStatement("select * from Login where UserAccount = ?");
            preparedStatement.setString(1,account);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String accountInDB = resultSet.getString(2);
                String passwordInDB = resultSet.getString(3);
                if (accountInDB.equals(account) && passwordInDB.equals(password))
                {
                    Handler handler = new Handler(getMainLooper()){
                        @Override
                        public void handleMessage(Message msg){
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("Mode",theme);
                            bundle.putString("userAccount", account);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    };
                    handler.sendEmptyMessage(0);
                }
                else if(!accountInDB.equals(account) || !passwordInDB.equals(password)) {
                    Handler handler = new Handler(getMainLooper()) {
                        @Override
                        public void handleMessage(Message msg) {
                            Toast.makeText(getApplicationContext(), "Incorrect account or password", Toast.LENGTH_LONG).show();
                        }
                    };
                    handler.sendEmptyMessage(0);
                }
            }

            else if(!resultSet.next()) {

                Handler handler = new Handler(getMainLooper()) {
                    @Override
                    public void handleMessage(Message msg) {
                        Toast.makeText(getApplicationContext(), "Incorrect account or password", Toast.LENGTH_LONG).show();
                    }
                };
                handler.sendEmptyMessage(0);
            }

            conn.close();
        }
        catch (final Exception e)
        {
            Handler handler = new Handler(getMainLooper())
            {
                @Override
                public void handleMessage(Message msg)
                {
                    Log.v("A",e.getMessage());
                    Log.v("A", "Failed");
                }
            };
            handler.sendEmptyMessage(0);

        }

    }
}
