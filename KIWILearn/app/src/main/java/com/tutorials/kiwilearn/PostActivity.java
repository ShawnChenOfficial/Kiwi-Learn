package com.tutorials.kiwilearn;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.sql.Connection;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

public class PostActivity extends AppCompatActivity {

    private String useraccount;
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
        Bundle bundle = getIntent().getExtras();
        theme = bundle.getInt("Mode",R.style.LightNoActionBar);
        setTheme(theme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        ImageView icon_home = findViewById(R.id.imageView_nav_home);
        Glide.with(this).load(R.drawable.home).into(icon_home);
        ImageView icon_post = findViewById(R.id.imageView_nav_post);
        Glide.with(this).load(R.drawable.post_selected).into(icon_post);
        ImageView icon_course = findViewById(R.id.imageView_nav_course);
        Glide.with(this).load(R.drawable.courses).into(icon_course);
        ImageView icon_me = findViewById(R.id.imageView_nav_me);
        Glide.with(this).load(R.drawable.me).into(icon_me);
        ImageView icon_drop_down_category = findViewById(R.id.imageView_drop_down_category);
        Glide.with(this).load(R.drawable.arrow_down).into(icon_drop_down_category);
        ImageView icon_drop_down_duration = findViewById(R.id.imageView_drop_down_duration);
        Glide.with(this).load(R.drawable.arrow_down).into(icon_drop_down_duration);

        int icon_color = this.getTheme().obtainStyledAttributes(R.styleable.ViewStyle).getColor(R.styleable.ViewStyle_colorPrimaryContents, Color.parseColor("#000000"));
        icon_home.setColorFilter(icon_color);
        icon_post.setColorFilter(icon_color);
        icon_course.setColorFilter(icon_color);
        icon_me.setColorFilter(icon_color);
        icon_drop_down_category.setColorFilter(icon_color);
        icon_drop_down_duration.setColorFilter(icon_color);

        /* Initial the dropdown menu of Category */
        Spinner spinner_Category = (Spinner) findViewById(R.id.spinner_post_category);

        String[] items_Category = this.getResources().getStringArray(R.array.post_category_items);

        ArrayAdapter<String> spinnerArrayAdapter_Category = new ArrayAdapter<String>(
                this, R.layout.drop_down_textview, items_Category);
        spinnerArrayAdapter_Category.setDropDownViewResource(R.layout.drop_down_textview);
        spinner_Category.setAdapter(spinnerArrayAdapter_Category);

        /* Initial the dropdown menu of Duration */
        Spinner spinner_Duration = (Spinner) findViewById(R.id.spinner_post_duration);

        String[] items_Duration = this.getResources().getStringArray(R.array.post_duration);

        ArrayAdapter<String> spinnerArrayAdapter_Duration = new ArrayAdapter<String>(
                this, R.layout.drop_down_textview, items_Duration);
        spinnerArrayAdapter_Duration.setDropDownViewResource(R.layout.drop_down_textview);
        spinner_Duration.setAdapter(spinnerArrayAdapter_Duration);

        /* set theme */
        ImageView imageView_small = findViewById(R.id.imageView_applogo_small);

        if(theme == R.style.NightNoActionBar){
            imageView_small.setImageResource(R.drawable.applogo_night);
        }
        else if(theme == R.style.LightNoActionBar){
            imageView_small.setImageDrawable(getResources().getDrawable(R.drawable.applogo));
        }

        TextView title = findViewById(R.id.textView_title);
        title.setText("Post Your Course");

        useraccount = bundle.getString("userAccount");
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

    public void onClickSubmit_Post(View view){

        Spinner categroy = findViewById(R.id.spinner_post_category);
        EditText title = findViewById(R.id.editText_post_title);
        EditText intro = findViewById(R.id.editText_post_introduction);
        EditText requirements = findViewById(R.id.editText_post_requirements);
        EditText fees = findViewById(R.id.editText_post_fees);
        EditText contact = findViewById(R.id.editText_post_contact);
        Spinner duration = findViewById(R.id.spinner_post_duration);
        try{
            Class.forName(DBDRIVER);
            Connection conn = DriverManager.getConnection(DBURL,DBUID,DBPWD);

            int id = 0;
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("select MAX(CourseID) from Courses");
            if(resultSet.next()){

                id = resultSet.getInt(1) + 1;

                String aaa = title.getText().toString();

                if(title.getText().toString() == null || title.getText().toString().equals("")){
                    Toast.makeText(this,"Title is empty",Toast.LENGTH_LONG).show();
                }
                else if(intro.getText().toString() == null || intro.getText().toString().equals("")){
                    Toast.makeText(this,"Introduction is empty",Toast.LENGTH_LONG).show();
                }
                else if(requirements.getText().toString() == null || requirements.getText().toString().equals("")){
                    Toast.makeText(this,"Requirement is empty",Toast.LENGTH_LONG).show();
                }
                else if(fees.getText().toString() == null || fees.toString().equals("")){
                    Toast.makeText(this,"Fees is empty",Toast.LENGTH_LONG).show();
                }
                else if(contact.getText().toString() == null || contact.getText().toString().equals("")){
                    Toast.makeText(this,"Contact is empty",Toast.LENGTH_LONG).show();
                }
                else if (title.getText().toString() != null && !title.getText().toString().equals("")
                && intro.getText().toString() != null && !intro.getText().toString().equals("")
                && requirements.getText().toString() != null && !requirements.getText().toString().equals("")
                && fees.getText().toString() != null && !fees.toString().equals("")
                && contact.getText().toString() != null && !contact.getText().toString().equals("")){
                    PreparedStatement preparedStatement = conn.prepareStatement("insert into Courses(CourseID, UserAccount,Title,Category,Introduction,Requirements,Fees,Contact,StartDate,EndDate) " +
                            "values (?,?,?,?,?,?,?,?,?,?)");
                    preparedStatement.setInt(1,id);
                    preparedStatement.setString(2,useraccount);
                    preparedStatement.setString(3,title.getText().toString());
                    preparedStatement.setString(4,categroy.getSelectedItem().toString());
                    preparedStatement.setString(5,intro.getText().toString());
                    preparedStatement.setString(6,requirements.getText().toString());
                    preparedStatement.setString(7,fees.getText().toString());
                    preparedStatement.setString(8,contact.getText().toString());

                    Date now = new Date();
                    DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String startDate = f.format(now);

                    preparedStatement.setString(9,startDate);

                    Calendar dateEnd = Calendar.getInstance();
                    dateEnd.setTime(now);
                    dateEnd.add(Calendar.DAY_OF_YEAR,Integer.parseInt(duration.getSelectedItem().toString().split(" ")[0]));
                    String endDate = f.format(dateEnd.getTime());

                    preparedStatement.setString(10,endDate);

                    preparedStatement.executeUpdate();

                    Handler handler = new Handler(getMainLooper()){
                        @Override
                        public void handleMessage(Message msg){
                            Toast.makeText(getApplicationContext(),"Submit success",Toast.LENGTH_LONG).show();
                        }

                    };
                    handler.sendEmptyMessage(0);

                    Intent intent = new Intent(this,HomeActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("userAccount",useraccount);
                    bundle.putInt("Mode",theme);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }


                conn.close();
            }
        }
        catch (SQLException e){
            Log.v("A",e.getMessage() + e.getCause());
        }
        catch (ClassNotFoundException e){
            Log.v("A",e.getMessage() + e.getCause());
        }
    }
}
