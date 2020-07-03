package com.tutorials.kiwilearn;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

public class PostHistoryDetailsActivity extends AppCompatActivity {

    public Bundle receiveIntent;
    private String useraccount;
    private String courseTitle;
    private String category;
    private String intro;
    private String requirements;
    private String fees;
    private String contact;
    private String startDate;
    private String status;
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
        receiveIntent = getIntent().getExtras();
        theme = receiveIntent.getInt("Mode",R.style.LightNoActionBar);
        setTheme(theme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_history_details);

        ImageView icon_back = findViewById(R.id.imageView_back);
        Glide.with(this).load(R.drawable.back).into(icon_back);
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
        icon_back.setColorFilter(icon_color);


        ImageView imageView_small = findViewById(R.id.imageView_applogo_small);

        if(theme == R.style.NightNoActionBar){
            imageView_small.setImageResource(R.drawable.applogo_night);
        }
        else if(theme == R.style.LightNoActionBar){
            imageView_small.setImageDrawable(getResources().getDrawable(R.drawable.applogo));
        }

        TextView title = findViewById(R.id.textView_title);
        title.setText("My Posts Details");

        useraccount = receiveIntent.getString("userAccount");
        category = receiveIntent.getString("Category");
        courseTitle = receiveIntent.getString("Title");
        intro = receiveIntent.getString("Introduction");
        requirements = receiveIntent.getString("Requirements");
        fees = receiveIntent.getString("Fees");
        contact = receiveIntent.getString("Contact");
        startDate = receiveIntent.getString("StartDate");
        status = receiveIntent.getString("Status");

        TextView detailsTitle = findViewById(R.id.textView_course_details_title);
        detailsTitle.setText(courseTitle);
        TextView detailsIntro = findViewById(R.id.textView_course_details_introduction);
        detailsIntro.setAutoSizeTextTypeUniformWithConfiguration(7,10,1, TypedValue.COMPLEX_UNIT_DIP);
        detailsIntro.setText("Introduction:\n" + intro);
        TextView detailsRequiements = findViewById(R.id.textView_course_details_requirements);
        detailsRequiements.setAutoSizeTextTypeUniformWithConfiguration(7,10,1, TypedValue.COMPLEX_UNIT_DIP);
        detailsRequiements.setText("Requirements:\n"+requirements);
        TextView detailsFees = findViewById(R.id.textView_course_details_fees);
        detailsFees.setAutoSizeTextTypeUniformWithConfiguration(7,10,1, TypedValue.COMPLEX_UNIT_DIP);
        detailsFees.setText("Fees:\n"+fees);
        TextView detailsContact = findViewById(R.id.textView_course_details_contacts);
        detailsContact.setAutoSizeTextTypeUniformWithConfiguration(7,10,1, TypedValue.COMPLEX_UNIT_DIP);
        detailsContact.setText("Contact:\n"+contact);
        TextView detailsPostDate = findViewById(R.id.textView_course_details_postdate);
        detailsPostDate.setAutoSizeTextTypeUniformWithConfiguration(7,10,1, TypedValue.COMPLEX_UNIT_DIP);
        detailsPostDate.setText("Post date:\n" + startDate + status);

    }

    public void onClickHome(View view){
        Intent intent = new Intent(PostHistoryDetailsActivity.this, HomeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("userAccount",useraccount);
        bundle.putInt("Mode",theme);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void onClickPost(View view){
        Intent intent = new Intent(PostHistoryDetailsActivity.this, PostActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("userAccount",useraccount);
        bundle.putInt("Mode",theme);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void onClickCourse(View view){
        Intent intent = new Intent(PostHistoryDetailsActivity.this,CoursesActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("userAccount",useraccount);
        bundle.putInt("Mode",theme);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void onClickMe(View view){
        Intent intent = new Intent(PostHistoryDetailsActivity.this, MeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("userAccount",useraccount);
        bundle.putInt("Mode",theme);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void onClickBack_H(View view){
        Intent intent = new Intent(PostHistoryDetailsActivity.this,PostHistoryActivity.class);
        Bundle intentString = new Bundle();
        intentString.putString("userAccount",useraccount);
        intentString.putInt("Mode",theme);
        intent.putExtras(intentString);
        startActivity(intent);
    }
}
