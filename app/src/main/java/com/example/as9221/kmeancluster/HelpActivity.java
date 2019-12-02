package com.example.as9221.kmeancluster;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.view.Menu;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.tb_launcher);
        setTitle("Help");
        TextView inst_help_TView_1 =(TextView)findViewById(R.id.inst_help_TView1);
        TextView inst_help_TView_2 =(TextView)findViewById(R.id.inst_help_TView2);
        TextView inst_help_TView_3 =(TextView)findViewById(R.id.inst_help_TView3);
        TextView inst_help_TView_4 =(TextView)findViewById(R.id.inst_help_TView4);
        TextView camera_setting_TView =(TextView)findViewById(R.id.camera_setting_TView);

        if (Build.VERSION.SDK_INT >= 24) {
            inst_help_TView_1.setText(Html.fromHtml(getString(R.string.inst_help1), Html.FROM_HTML_MODE_LEGACY));
            inst_help_TView_2.setText(Html.fromHtml(getString(R.string.inst_help2), Html.FROM_HTML_MODE_LEGACY));
            inst_help_TView_3.setText(Html.fromHtml(getString(R.string.inst_help3), Html.FROM_HTML_MODE_LEGACY));
            inst_help_TView_4.setText(Html.fromHtml(getString(R.string.inst_help4), Html.FROM_HTML_MODE_LEGACY));
            camera_setting_TView.setText(Html.fromHtml(getString(R.string.camera_setting), Html.FROM_HTML_MODE_LEGACY));

        } else {
            inst_help_TView_1.setText(Html.fromHtml(getString(R.string.inst_help1)));
            inst_help_TView_2.setText(Html.fromHtml(getString(R.string.inst_help2)));
            inst_help_TView_3.setText(Html.fromHtml(getString(R.string.inst_help3)));
            inst_help_TView_4.setText(Html.fromHtml(getString(R.string.inst_help4)));
            camera_setting_TView.setText(Html.fromHtml(getString(R.string.camera_setting)));
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
