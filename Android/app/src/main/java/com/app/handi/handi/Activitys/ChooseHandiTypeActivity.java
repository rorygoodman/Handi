package com.app.handi.handi.Activitys;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.handi.handi.DataTypes.Job;
import com.app.handi.handi.R;

public class ChooseHandiTypeActivity extends AppCompatActivity {
    String profession;
    Intent intent;
    boolean moreQuotes = false;
    Job job;
    ImageView imageBackground;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_handi);
        job = (Job)getIntent().getSerializableExtra("Job");
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
            moreQuotes = bundle.getBoolean("moreQuotes");
        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/LT.ttf");
        // Loading TextViews into an array
        TextView[] textViews = {(TextView) findViewById(R.id.activity_choose_handi_text_view_header), (TextView) findViewById(R.id.activity_choose_handi_text_view_cleaner),
                (TextView) findViewById(R.id.activity_choose_handi_text_view_plumber), (TextView) findViewById(R.id.activity_choose_handi_text_view_electrician),
                (TextView) findViewById(R.id.activity_choose_handi_text_view_handiman), (TextView) findViewById(R.id.activity_choose_handi_text_view_painter)};
        //setting typeface
        setTypeFace(textViews, tf);
    }

    public void onClick(View view) {
        //Cleaner Button actions
        if(view.getId()==R.id.activity_choose_handi_relative_layout_cleaner){
            profession = "Cleaner";
            intent = new Intent(this,JobSelectionActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Job",job);
            intent.putExtras(bundle);
            intent.putExtra("moreQuotes",moreQuotes);
            intent.putExtra("profession",profession);
            imageBackground = (ImageView) findViewById(R.id.activity_choose_handi_image_box_cleaner);
            imageBackground.setColorFilter(ContextCompat.getColor(this,R.color.dark_pink));
            startActivity(intent);
        }
        //Electrician Button actions
        else if(view.getId()==R.id.activity_choose_handi_relative_layout_electrician){
            profession = "Electrician";
            intent = new Intent(this,JobSelectionActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Job",job);
            intent.putExtras(bundle);
            intent.putExtra("moreQuotes",moreQuotes);
            intent.putExtra("profession",profession);
            imageBackground = (ImageView) findViewById(R.id.activity_choose_handi_image_box_electrician);
            imageBackground.setColorFilter(ContextCompat.getColor(this,R.color.dark_pink));
            startActivity(intent);
        }
        //Plumber button actions
        else if(view.getId()==R.id.activity_choose_handi_LinearLayout_plumber){
            profession = "Plumber";
            intent = new Intent(this,JobSelectionActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Job",job);
            intent.putExtras(bundle);
            intent.putExtra("moreQuotes",moreQuotes);
            intent.putExtra("profession",profession);
            imageBackground = (ImageView) findViewById(R.id.activity_choose_handi_image_box_plumber);
            imageBackground.setColorFilter(ContextCompat.getColor(this,R.color.dark_pink));
            startActivity(intent);
        }
        //Painter button actions
        else if(view.getId()==R.id.activity_choose_handi_relative_layout_painter){
            profession = "Painter";
            intent = new Intent(this,JobSelectionActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Job",job);
            intent.putExtras(bundle);
            intent.putExtra("moreQuotes",moreQuotes);
            intent.putExtra("profession",profession);
            imageBackground = (ImageView) findViewById(R.id.activity_choose_handi_image_box_painter);
            imageBackground.setColorFilter(ContextCompat.getColor(this,R.color.dark_pink));
            startActivity(intent);
        }
        //HandiMan button actions
        else {
            profession = "HandiMan";
            intent = new Intent(this,JobSelectionActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Job",job);
            intent.putExtras(bundle);
            intent.putExtra("moreQuotes",moreQuotes);
            intent.putExtra("profession",profession);
            imageBackground = (ImageView) findViewById(R.id.activity_choose_handi_image_box_handiman);
            imageBackground.setColorFilter(ContextCompat.getColor(this,R.color.dark_pink));
            startActivity(intent);
        }
    }

    //function that changes the an array of TextViews to a certian typeface
    public void setTypeFace(TextView[] texts, Typeface tf) {
        for (TextView text : texts) text.setTypeface(tf);
    }
}
