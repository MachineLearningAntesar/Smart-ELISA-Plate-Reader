package com.example.as9221.kmeancluster;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class RiskActivity extends AppCompatActivity {
    ImageButton leftNav;
    ImageButton rightNav;
    protected static final String TAG = null;
    int patientId = 0;
    ArrayList<String> answer = new ArrayList<String>();



    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       /* AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Check Vulnerable Groups");
        alert.setMessage("Please insert your professional ID & Password");

        alert.setCancelable(false);

        Context context = this;
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

// Add a TextView here for the "Title" label, as noted in the comments
        final EditText idBox = new EditText(context);
        idBox.setHint("ID");
        layout.addView(idBox); // Notice this is an add method

// Add another TextView here for the "Description" label
        final EditText passwordBox = new EditText(context);
        passwordBox.setHint("Password");
        layout.addView(passwordBox); // Another add method

        alert.setView(layout); // Again this is a set method, not add


        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value1 = idBox.getText().toString();
                String value2 = passwordBox.getText().toString();
                // Do something with value!
                DBHandler db = new DBHandler(RiskActivity.this);

                //getting the last entered row
                Patient professional = new Patient();

                //professional.setIdprofessional(Integer.parseInt(value1));
                //professional.setPasswordprofessional(value2);
                //db.addPatientByProfessional(professional);


                professional = db.getProfessional(Integer.parseInt(value1), value2);
                if (professional != null) {
                    if (professional.getPasswordprofessional().equals(value2)) {
                        Toast.makeText(RiskActivity.this, "Professional details are correct", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RiskActivity.this, "Professional details are not correct", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else {
                    Toast.makeText(RiskActivity.this, "Do you want to insert your details", Toast.LENGTH_SHORT).show();
                    Log.d(TAG,"Value of value1 " + value1);
                    Patient professional1 = new Patient();


                    professional1.setIdprofessional(Integer.parseInt(value1));
                    professional1.setPasswordprofessional(value2);

                    db.addPatientByProfessional(professional1);
                }


            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                finish();
            }
        });

        alert.show();*/

        setContentView(R.layout.activity_risk);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.tb_launcher);
        setTitle("Check Risk Group");

        Intent mIntent = getIntent();
        patientId = mIntent.getIntExtra("patientID", 0);
        Log.d(TAG,"patientID in symptom " + patientId);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);

        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        indicator.setViewPager(mViewPager);

        leftNav = (ImageButton) findViewById(R.id.left_nav);
        rightNav = (ImageButton) findViewById(R.id.right_nav);
        //leftNav.setEnabled(false);

        /*Intent mIntent = getIntent();
        answer = mIntent.getStringArrayListExtra("answerList");*/


        // Images left navigation
        leftNav.setVisibility(View.INVISIBLE);
        leftNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tab = mViewPager.getCurrentItem();
                if (tab > 0) {
                    //rightNav.setEnabled(true);
                    rightNav.setVisibility(View.VISIBLE);
                    tab--;
                    mViewPager.setCurrentItem(tab);
                } if (tab == 0) {
                    mViewPager.setCurrentItem(tab);
                    //leftNav.setEnabled(false);
                    leftNav.setVisibility(View.INVISIBLE);

                }
            }
        });

        // Images right navigatin
        rightNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tab = mViewPager.getCurrentItem();
                switch (tab) {
                    case 0:
                        RiskGroup1 RG1 = new RiskGroup1();
                        //if (RG1.getAnswer()==null) {
                          //  Toast.makeText(RiskActivity.this, "Please select your answer", Toast.LENGTH_SHORT).show();
                        //} else {
                            //leftNav.setEnabled(true);
                            leftNav.setVisibility(View.VISIBLE);
                        rightNav.setVisibility(View.INVISIBLE);

                        tab++;
                            mViewPager.setCurrentItem(tab);
                            /* if (tab++ == 2) {
                                rightNav.setVisibility(View.INVISIBLE);
                            } else {
                                rightNav.setVisibility(View.VISIBLE);
                            }*/
                        //}
                        break;

                    case 1:
                        RiskGroup2 RG2 = new RiskGroup2();
                       // Log.i(TAG, "get answer : " +   RG2.getCurrentAnswer());
                        //if (RG2.getCurrentAnswer()==null) {
                          //  Toast.makeText(RiskActivity.this, "Please select your answer", Toast.LENGTH_SHORT).show();
                        //} else {
                            //leftNav.setEnabled(true);
                        leftNav.setVisibility(View.VISIBLE);
                        tab++;

                            mViewPager.setCurrentItem(tab);
                        //}
                        break;
                }

                if (tab == 2) {
                    mViewPager.setCurrentItem(tab);
                    //rightNav.setEnabled(false);
                    rightNav.setVisibility(View.INVISIBLE);

                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
          //  return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }


    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    RiskGroup1 RG1 = new RiskGroup1();
                    return RG1;
                case 1:
                    RiskGroup2 RG2 = new RiskGroup2();
                    return RG2;

            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

    }
}
