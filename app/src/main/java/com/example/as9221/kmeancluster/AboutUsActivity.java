package com.example.as9221.kmeancluster;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.widget.TextView;

// use         android:largeHeap="true" when needed


public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.tb_launcher);
        setTitle("About Us");
        TextView titleTView =(TextView)findViewById(R.id.titleTView);
        TextView descriptionTView =(TextView)findViewById(R.id.desTView);

        if (Build.VERSION.SDK_INT >= 24) {
            titleTView.setText(Html.fromHtml(getString(R.string.about_us_title),Html.FROM_HTML_MODE_LEGACY));
            descriptionTView.setText(Html.fromHtml(getString(R.string.about_us_description),Html.FROM_HTML_MODE_LEGACY));
            descriptionTView.setMovementMethod(LinkMovementMethod.getInstance());            // for 24 api and more
        } else {
            titleTView.setText(Html.fromHtml(getString(R.string.about_us_title)));
            descriptionTView.setText(Html.fromHtml(getString(R.string.about_us_description)));
            descriptionTView.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_mainMenu2).getActionView();
        return true;
    }

    @Override
    public void onBackPressed()
    {
        // code here to show dialog
        finish();
    }

}
