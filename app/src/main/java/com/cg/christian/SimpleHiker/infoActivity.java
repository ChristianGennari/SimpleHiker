package com.cg.christian.SimpleHiker;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class infoActivity extends AppCompatActivity {

    Typeface myHeadingFontForInfo;
    Typeface mySubFont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        myHeadingFontForInfo = Typeface.createFromAsset(this.getAssets(), "fonts/Lato-Regular.ttf");
        mySubFont = Typeface.createFromAsset(this.getAssets(), "fonts/Lato-Light.ttf");

        TextView whatIsLat = findViewById(R.id.whatIsLattextView);
        TextView explainLat = findViewById(R.id.explainLatTextView);
        TextView whatIsLong = findViewById(R.id.whatIsLongTextView);
        TextView explainLong = findViewById(R.id.explainLongTextView);
        TextView whatIsAcc = findViewById(R.id.whatIsAccTextView);
        TextView explainAcc = findViewById(R.id.explainAccTextView);
        TextView whatIsAlt = findViewById(R.id.whatIsAltTextView);
        TextView explainAlt = findViewById(R.id.explainAltTextView);

        // Headings
        whatIsLat.setTypeface(myHeadingFontForInfo);
        whatIsLong.setTypeface(myHeadingFontForInfo);
        whatIsAcc.setTypeface(myHeadingFontForInfo);
        whatIsAlt.setTypeface(myHeadingFontForInfo);

        //Bread Text
        explainLat.setTypeface(mySubFont);
        explainLong.setTypeface(mySubFont);
        explainAcc.setTypeface(mySubFont);
        explainAlt.setTypeface(mySubFont);

    }

    public void onClickReturn (View viewReturn) {
        finish();
    }

}
