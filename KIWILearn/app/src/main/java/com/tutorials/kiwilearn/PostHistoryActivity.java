package com.tutorials.kiwilearn;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.percent.PercentLayoutHelper;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.autofill.AutofillId;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.view.View.generateViewId;
import static android.widget.RelativeLayout.BELOW;

public class PostHistoryActivity extends AppCompatActivity {

    private static String useraccount = null;
    private static final String DBDRIVER = "com.mysql.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://192.168.80.240:3306/shawn";
    private static final String DBUID = "shawn";
    private static final String DBPWD = "secret";
    private static Bundle receiveIntent;
    private ArrayList<PercentRelativeLayout> layoutIDList = new ArrayList<PercentRelativeLayout>();
    private ArrayList<Courses> coursesData_H = new ArrayList<>();
    private int numData;
    private int theme;
    private TypedArray typeArray;
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
        receiveIntent = getIntent().getExtras();
        theme = receiveIntent.getInt("Mode",R.style.LightNoActionBar);
        setTheme(theme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_history);


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

        TextView title = findViewById(R.id.textView_title);
        title.setText("My Posts");

        useraccount = receiveIntent.getString("userAccount");

        typeArray = this.getTheme().obtainStyledAttributes(R.styleable.ViewStyle);

        InitializeCourses_H();
    }

    public void onClickHome(View view) {
        Intent intent = new Intent(PostHistoryActivity.this, HomeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("userAccount",useraccount);
        bundle.putInt("Mode",theme);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onClickPost(View view) {
        Intent intent = new Intent(PostHistoryActivity.this, PostActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("userAccount",useraccount);
        bundle.putInt("Mode",theme);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onClickCourse(View view) {
        Intent intent = new Intent(PostHistoryActivity.this, CoursesActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("userAccount",useraccount);
        bundle.putInt("Mode",theme);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onClickMe(View view) {
        Intent intent = new Intent(PostHistoryActivity.this, MeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("userAccount",useraccount);
        bundle.putInt("Mode",theme);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void onClickMore_H(View view){
        PercentRelativeLayout layout = findViewById(R.id.layout_in_scrollview);
        TextView more = findViewById(R.id.textView_more);
        createView_H(3,coursesData_H.size(),layout);
        more.setText(String.format("Scroll down for %d more...",numData - 3));
        more.setClickable(false);
    }

    public void InitializeCourses_H() {
        PercentRelativeLayout layout = findViewById(R.id.layout_in_scrollview);
        TextView more = findViewById(R.id.textView_more);
        for(int i = 0; i < layoutIDList.size(); i ++){
            layout.removeView(layoutIDList.get(i));
        }

        numData = 0;

        try {
            Class.forName(DBDRIVER);
            Connection conn = DriverManager.getConnection(DBURL, DBUID, DBPWD);
            PreparedStatement preparedStatement = conn.prepareStatement("select count(*) from Courses where UserAccount = ?");
            preparedStatement.setString(1, useraccount);
            ResultSet resultSet = preparedStatement.executeQuery();


            more.setClickable(true);

            if (resultSet.next()) {

                preparedStatement = conn.prepareStatement("select * from Courses where UserAccount = ? order by StartDate desc");
                preparedStatement.setString(1, useraccount);
                resultSet = preparedStatement.executeQuery();

                LoadData(resultSet,more,layout);
            }
            else{
                more.setText("No result");
                more.setClickable(false);
            }
            conn.close();
        } catch (Exception e){

        }
    }
    private void LoadData(ResultSet resultSet,TextView more,PercentRelativeLayout layout){
        try{
            while(resultSet.next()){
                DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date startDate;
                Date endDate;
                Date now;
                try{
                    startDate = fmt.parse(resultSet.getString(9));
                    endDate = fmt.parse(resultSet.getString(10));
                    now = new Date();

                    String Status;
                    if(endDate.compareTo(now) <= 0){
                        Status ="\nStatus: Closed";
                    }
                    else{
                        Status ="\nStatus: Posting";
                    }

                    coursesData_H.add(new Courses(resultSet.getString(2).toString(),
                            resultSet.getString(3).toString(),
                            resultSet.getString(4).toString(),
                            resultSet.getString(5).toString(),
                            resultSet.getString(6).toString(),
                            resultSet.getString(7).toString(),
                            resultSet.getString(8).toString(),
                            startDate.toLocaleString(),
                            endDate.toLocaleString(),
                            Status));
                }
                catch (Exception e){

                }
            }

            numData = coursesData_H.size();

            if (numData > 3) {
                createView_H(0,3,layout);
                more.setText("Click for more...");
            }
            else if (numData == 0) {
                more.setText("No result");
                more.setClickable(false);

            } else if (numData <= 3 && numData != 0) {
                createView_H(0,numData,layout);
                more.setText(String.format("%d records found...",numData));
                more.setClickable(false);
            }
        }
        catch (Exception e){

        }
    }

    private void createView_H(int startnum, int endnum,PercentRelativeLayout layout) {

        int textColor = typeArray.getColor(R.styleable.ViewStyle_colorSecondaryContents,Color.parseColor("#707070"));

        for (int i = startnum; i < endnum; i++) {
            final int index = i;
            PercentRelativeLayout inner_layout = new PercentRelativeLayout(this);
            inner_layout.setId(generateViewId());
            PercentRelativeLayout.LayoutParams params = new PercentRelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            if (layoutIDList.size() > 0) {
                params.addRule(BELOW, layoutIDList.get(layoutIDList.size() - 1).getId());
            }
            PercentLayoutHelper.PercentLayoutInfo info = params.getPercentLayoutInfo();
            info.heightPercent = 0.27f;
            info.widthPercent = 0.90f;
            info.leftMarginPercent = 0.05f;
            info.topMarginPercent = 0.02f;
            inner_layout.requestLayout();
            inner_layout.setBackgroundResource(R.drawable.backgournd_border);
            inner_layout.setLayoutParams(params);
            layoutIDList.add(inner_layout);

            Button button = new Button(this);
            params = new PercentRelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            info = params.getPercentLayoutInfo();
            info.widthPercent = 0.20f;
            info.heightPercent = 0.25f;
            info.topMarginPercent = 0.67f;
            info.leftMarginPercent = 0.78f;
            button.requestLayout();
            button.setAutoSizeTextTypeUniformWithConfiguration(9,16,1, TypedValue.COMPLEX_UNIT_DIP);
            button.setText("Details");
            button.setAllCaps(false);
            button.setBackgroundResource(R.drawable.backgournd_border);
            button.setTextColor(textColor);
            button.setLayoutParams(params);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PostHistoryActivity.this, PostHistoryDetailsActivity.class);
                    Bundle intentString = new Bundle();
                    intentString.putString("userAccount", useraccount);
                    intentString.putString("Title",coursesData_H.get(index).GetTitle());
                    intentString.putString("Category",coursesData_H.get(index).GetCategory());
                    intentString.putString("Introduction",coursesData_H.get(index).GetIntroduction());
                    intentString.putString("Requirements",coursesData_H.get(index).GetRequirement());
                    intentString.putString("Fees",coursesData_H.get(index).GetFees());
                    intentString.putString("Contact",coursesData_H.get(index).GetContact());
                    intentString.putString("StartDate",coursesData_H.get(index).GetStartDate());
                    intentString.putString("Status",coursesData_H.get(index).GetStatus());
                    intentString.putInt("Mode",theme);
                    intent.putExtras(intentString);
                    startActivity(intent);
                }
            });

            TextView textView_title = new TextView(this);
            params = new PercentRelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            info = params.getPercentLayoutInfo();
            info.widthPercent = 0.65f;
            info.heightPercent = 0.30f;
            info.topMarginPercent = 0.05f;
            info.leftMarginPercent = 0.05f;
            textView_title.requestLayout();
            String title = coursesData_H.get(index).GetTitle();
            textView_title.setAutoSizeTextTypeUniformWithConfiguration(7,12,1, TypedValue.COMPLEX_UNIT_DIP);
            textView_title.setText(title);
            textView_title.setTextColor(textColor);
            textView_title.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            textView_title.setGravity(Gravity.START | Gravity.TOP);
            textView_title.setLayoutParams(params);

            TextView textView_intro = new TextView(this);
            params = new PercentRelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            info = params.getPercentLayoutInfo();
            info.widthPercent = 0.90f;
            info.heightPercent = 0.55f;
            info.topMarginPercent = 0.40f;
            info.leftMarginPercent = 0.05f;
            textView_intro.requestLayout();
            textView_intro.setTextColor(textColor);
            textView_intro.setAutoSizeTextTypeUniformWithConfiguration(7,12,1, TypedValue.COMPLEX_UNIT_DIP);

            if(coursesData_H.get(index).GetIntroduction().length() > 50){
                textView_intro.setText(coursesData_H.get(index).GetIntroduction().substring(0,101) + "...");
            }
            else{
                textView_intro.setText(coursesData_H.get(index).GetIntroduction());
            }
            textView_title.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            textView_title.setGravity(Gravity.START | Gravity.TOP);
            textView_intro.setLayoutParams(params);

            TextView textView_posted_date = new TextView(this);
            params = new PercentRelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            info = params.getPercentLayoutInfo();
            info.widthPercent = 0.15f;
            info.heightPercent = 0.30f;
            info.topMarginPercent = 0.05f;
            info.leftMarginPercent = 0.82f;
            textView_posted_date.requestLayout();
            textView_posted_date.setTextColor(textColor);
            textView_posted_date.setAutoSizeTextTypeUniformWithConfiguration(7,15,1, TypedValue.COMPLEX_UNIT_DIP);

            textView_posted_date.setText("Posted date\n" + coursesData_H.get(index).GetStartDate() + coursesData_H.get(index).GetStatus());

            textView_posted_date.setTextColor(textColor);
            textView_posted_date.setLayoutParams(params);

            inner_layout.addView(textView_posted_date);
            inner_layout.addView(textView_title);
            inner_layout.addView(textView_intro);
            inner_layout.addView(button);
            layout.addView(inner_layout);

        }
    }
}