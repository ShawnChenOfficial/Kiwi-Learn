package com.tutorials.kiwilearn;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class CreateAccountActivity extends AppCompatActivity {


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
        setContentView(R.layout.activity_create_account);

        ImageView imageView_small = findViewById(R.id.imageView_applogo_small);

        if(theme == R.style.NightNoActionBar){
            imageView_small.setImageResource(R.drawable.applogo_night);
        }
        else if(theme == R.style.LightNoActionBar){
            imageView_small.setImageDrawable(getResources().getDrawable(R.drawable.applogo));
        }

        TextView title = findViewById(R.id.textView_title);
        title.setText("Join Us");

    }

    public void onClickSubmit(View view){

        final EditText account = findViewById(R.id.editText_create_account_username_input);
        final EditText password_first = findViewById(R.id.editText_create_account_password_input);
        final EditText password_retype = findViewById(R.id.editText_create_account_re_password_input);
        final EditText email = findViewById(R.id.editText_create_account_email_input);
        final CheckBox agreement = findViewById(R.id.checkBox_agree);

        String new_account = account.getText().toString();
        String new_password = password_first.getText().toString();
        String new_password_retype = password_retype.getText().toString();
        String new_email = email.getText().toString();

        if(new_account.equals("")){
            Handler handler = new Handler(getMainLooper()){
                @Override
                public void handleMessage(Message msg){
                    Toast.makeText(getApplicationContext(),"User account is empty",Toast.LENGTH_LONG).show();
                }
            };
            handler.sendEmptyMessage(0);
        }

        else if(new_password.equals("")){
            Handler handler = new Handler(getMainLooper()){
                @Override
                public void handleMessage(Message msg){
                    Toast.makeText(getApplicationContext(),"User password is empty",Toast.LENGTH_LONG).show();
                }
            };
            handler.sendEmptyMessage(0);
        }

        else if(new_password_retype.equals("")){
            Handler handler = new Handler(getMainLooper()){
                @Override
                public void handleMessage(Message msg){
                    Toast.makeText(getApplicationContext(),"Please repeat your password",Toast.LENGTH_LONG).show();
                }
            };
            handler.sendEmptyMessage(0);
        }

        else if(new_email.equals("")){
            Handler handler = new Handler(getMainLooper()){
                @Override
                public void handleMessage(Message msg){
                    Toast.makeText(getApplicationContext(),"User email is empty",Toast.LENGTH_LONG).show();
                }
            };
            handler.sendEmptyMessage(0);
        }

        else if(!new_password.equals(new_password_retype)){
            Handler handler = new Handler(getMainLooper()){
                @Override
                public void handleMessage(Message msg){
                    Toast.makeText(getApplicationContext(),"The passwords are not matching",Toast.LENGTH_LONG).show();
                }
            };
            handler.sendEmptyMessage(0);
        }

        else if(!agreement.isChecked()){
            Handler handler = new Handler(getMainLooper()){
                @Override
                public void handleMessage(Message msg){
                    Toast.makeText(getApplicationContext(),"You must agree with our policies",Toast.LENGTH_LONG).show();
                }
            };
            handler.sendEmptyMessage(0);
        }

        else {
            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection(DBURL,DBUID,DBPWD);
                PreparedStatement preparedStatement = conn.prepareStatement("select UserAccount from User where UserAccount = ?");
                preparedStatement.setString(1,new_account);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    String userAccount_in_DB = resultSet.getString(1);
                    if(userAccount_in_DB.equals(new_account)){
                        Handler handler = new Handler(getMainLooper()){
                            @Override
                            public void handleMessage (Message msg){
                                Toast.makeText(getApplicationContext(),"Account is existing, please try another account",Toast.LENGTH_LONG).show();
                            }
                        };
                        handler.sendEmptyMessage(0);
                    }
                }
                else
                    {

                        preparedStatement = conn.prepareStatement("insert into Login (LoginID, UserAccount, Password) values (?,?,?)");
                        preparedStatement.setString(1,new_account);
                        preparedStatement.setString(2,new_account);
                        preparedStatement.setString(3,new_password);
                        preparedStatement.executeUpdate();

                        preparedStatement.close();

                        preparedStatement = conn.prepareStatement("insert into User (UserAccount,Nickname, Gender, Region, Email, Iconpath) values (?,?,?,?,?,?)");
                        preparedStatement.setString(1,new_account);
                        preparedStatement.setString(2,"");
                        preparedStatement.setString(3,"");
                        preparedStatement.setString(4,"");
                        preparedStatement.setString(5,new_email);
                        preparedStatement.setString(6,"");
                        preparedStatement.executeUpdate();

                        preparedStatement.close();

                        Handler handler = new Handler(getMainLooper()){
                            @Override
                            public void handleMessage(Message msg){
                                Toast.makeText(getApplicationContext(),"You have successfully created an account name",Toast.LENGTH_LONG).show();

                                try{
                                    Intent start = new Intent(CreateAccountActivity.this, LoginActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("Mode",theme);
                                    start.putExtras(bundle);
                                    startActivity(start);
                                }
                                catch (Exception e){
                                    Log.v("A",e.getCause().toString());
                                }
                            }
                        };
                        handler.sendEmptyMessage(0);
                }

                conn.close();

            }

            catch (Exception e){
                Log.v("B",e.getCause().toString() + "\n" +e.getMessage() + "\n");
            }
        }
    }
}
