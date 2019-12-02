package com.example.as9221.kmeancluster;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.widget.TextView;


public class TermsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.tb_launcher);
        setTitle("Terms & Conditions");
        TextView titleTView =(TextView)findViewById(R.id.titleTermView);
        TextView descriptionTView =(TextView)findViewById(R.id.desTermView);

        if (Build.VERSION.SDK_INT >= 24) {
            titleTView.setText(Html.fromHtml(getString(R.string.terms_title), Html.FROM_HTML_MODE_LEGACY));
            descriptionTView.setText(Html.fromHtml(getString(R.string.terms_description), Html.FROM_HTML_MODE_LEGACY));
            descriptionTView.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            titleTView.setText(Html.fromHtml(getString(R.string.terms_title)));
            descriptionTView.setText(Html.fromHtml(getString(R.string.terms_description)));
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
