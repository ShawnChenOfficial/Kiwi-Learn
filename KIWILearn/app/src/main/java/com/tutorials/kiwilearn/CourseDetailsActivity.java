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

public class CourseDetailsActivity extends AppCompatActivity {

    private Button search;
    private Bundle receiveIntent;
    private String useraccount;
    private String courseTitle;
    private String category;
    private String intro;
    private String requirements;
    private String fees;
    private String contact;
    private String startDate;
    private String status;
    private String[] searchedMessage;
    private int theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        receiveIntent = getIntent().getExtras();
        theme = receiveIntent.getInt("Mode",R.style.LightNoActionBar);
        setTheme(theme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detials);

        ImageView icon_home = findViewById(R.id.imageView_nav_home);
        Glide.with(this).load(R.drawable.home).into(icon_home);
        ImageView icon_post = findViewById(R.id.imageView_nav_post);
        Glide.with(this).load(R.drawable.post).into(icon_post);
        ImageView icon_course = findViewById(R.id.imageView_nav_course);
        Glide.with(this).load(R.drawable.courses_selected).into(icon_course);
        ImageView icon_me = findViewById(R.id.imageView_nav_me);
        Glide.with(this).load(R.drawable.me).into(icon_me);
        ImageView icon_back = findViewById(R.id.imageView_back);
        Glide.with(this).load(R.drawable.back).into(icon_back);
        ImageView icon_search = findViewById(R.id.imageView_searchIcon);
        Glide.with(this).load(R.drawable.searchicon).into(icon_search);


        int icon_color = this.getTheme().obtainStyledAttributes(R.styleable.ViewStyle).getColor(R.styleable.ViewStyle_colorPrimaryContents, Color.parseColor("#000000"));
        icon_home.setColorFilter(icon_color);
        icon_post.setColorFilter(icon_color);
        icon_course.setColorFilter(icon_color);
        icon_me.setColorFilter(icon_color);
        icon_back.setColorFilter(icon_color);
        icon_search.setColorFilter(icon_color);

        ImageView imageView_small = findViewById(R.id.imageView_applogo_small);
        final SearchView searchView = findViewById(R.id.searchview_courses_search_bar);
        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);

        TextView textView = (TextView) searchView.findViewById(id);
        if(theme == R.style.NightNoActionBar){
            textView.setTextColor(Color.WHITE);
            imageView_small.setImageResource(R.drawable.applogo_night);
        }
        else if(theme == R.style.LightNoActionBar){
            textView.setTextColor(Color.BLACK);
            imageView_small.setImageDrawable(getResources().getDrawable(R.drawable.applogo));
        }

        TextView title = findViewById(R.id.textView_title);
        title.setText("Course Details");

        search = findViewById(R.id.button_search);

        useraccount = receiveIntent.getString("userAccount");
        category = receiveIntent.getString("Category");
        courseTitle = receiveIntent.getString("Title");
        intro = receiveIntent.getString("Introduction");
        requirements = receiveIntent.getString("Requirements");
        fees = receiveIntent.getString("Fees");
        contact = receiveIntent.getString("Contact");
        startDate = receiveIntent.getString("StartDate");
        status = receiveIntent.getString("Status");


        searchedMessage = new String[1];
        searchedMessage[0] = receiveIntent.getString("SearchedKey");

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


        searchView.setQuery(searchedMessage[0],false);
        if(searchedMessage[0] == null || searchedMessage[0].equals("")){
            searchView.setQueryHint("Keys for search");
        }
        else{
            searchView.setQueryHint("");
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        findViewById(R.id.button_search).callOnClick();
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchedMessage[0] = newText;

                if(searchedMessage[0] == null || searchedMessage[0].equals("")){
                    searchView.setQueryHint("Keys for search");
                }
                else{
                    searchView.setQueryHint("");
                }
                return false;
            }
        });


    }

    public void onClickHome(View view){
        Intent intent = new Intent(CourseDetailsActivity.this, HomeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("Mode",theme);
        bundle.putString("userAccount", useraccount);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void onClickPost(View view){
        Intent intent = new Intent(CourseDetailsActivity.this, PostActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("Mode",theme);
        bundle.putString("userAccount", useraccount);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void onClickCourse(View view){
        Intent intent = new Intent(CourseDetailsActivity.this,CoursesActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("Mode",theme);
        bundle.putString("userAccount", useraccount);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void onClickMe(View view){
        Intent intent = new Intent(CourseDetailsActivity.this, MeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("Mode",theme);
        bundle.putString("userAccount", useraccount);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void onClickBack(View view){
        Intent intent = new Intent(CourseDetailsActivity.this,CoursesActivity.class);
        Bundle intentString = new Bundle();
        intentString.putString("Category",searchedMessage[0]);
        intentString.putInt("Mode",theme);
        intentString.putString("userAccount",useraccount);
        intent.putExtras(intentString);
        startActivity(intent);
    }
    public void onClickSearch(View view){
        Intent intent = new Intent(CourseDetailsActivity.this,CoursesActivity.class);
        Bundle intentString = new Bundle();
        intentString.putString("Category",searchedMessage[0]);
        intentString.putInt("Mode",theme);
        intentString.putString("userAccount",useraccount);
        intent.putExtras(intentString);
        startActivity(intent);
    }
}
