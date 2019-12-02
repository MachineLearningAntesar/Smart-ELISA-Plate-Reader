package com.example.as9221.kmeancluster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import me.relex.circleindicator.CircleIndicator;


public class SymptomActivity extends AppCompatActivity {
    ImageButton leftNav;
    ImageButton rightNav;
    protected static final String TAG = null;
    int patientId = 0;


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
        setContentView(R.layout.activity_symptom);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.tb_launcher);
        setTitle("Check Symptom");
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        Intent mIntent = getIntent();
        patientId = mIntent.getIntExtra("patientID", 0);
        Log.d(TAG,"patientID in symptom " + patientId);


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        indicator.setViewPager(mViewPager);

        leftNav = (ImageButton) findViewById(R.id.left_nav);
        rightNav = (ImageButton) findViewById(R.id.right_nav);
        leftNav.setVisibility(View.INVISIBLE);

        // Images left navigation
        leftNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tab = mViewPager.getCurrentItem();
                if (tab > 0) {
                    rightNav.setVisibility(View.VISIBLE);
                    tab--;
                    mViewPager.setCurrentItem(tab);
                } if (tab == 0) {
                    mViewPager.setCurrentItem(tab);
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
                        Symptom0 symp0 = new Symptom0();
                        /*if (symp1.getAnswer()==null) {
                            Toast.makeText(SymptomActivity.this, "Please select your answer", Toast.LENGTH_SHORT).show();
                        } else {*/
                            leftNav.setVisibility(View.VISIBLE);
                            tab++;
                            mViewPager.setCurrentItem(tab);
                       // }
                        break;

                    case 1:
                        Symptom1 symp1 = new Symptom1();
                        if (symp1.getAnswer()==null) {
                            Toast.makeText(SymptomActivity.this, "Please select your answer", Toast.LENGTH_SHORT).show();
                        } else {
                            leftNav.setVisibility(View.VISIBLE);
                            tab++;
                            mViewPager.setCurrentItem(tab);
                        }
                        break;

                    case 2:
                        Symptom2 symp2 = new Symptom2();
                        Log.i(TAG, "get answer : " +   symp2.getCurrentAnswer());
                        if (symp2.getCurrentAnswer()==null) {
                            Toast.makeText(SymptomActivity.this, "Please select your answer", Toast.LENGTH_SHORT).show();
                        } else {
                            leftNav.setVisibility(View.VISIBLE);
                            tab++;
                            mViewPager.setCurrentItem(tab);
                        }
                        break;

                    case 3:
                        Symptom3 symp3 = new Symptom3();
                        if (symp3.getCurrentAnswer()==null) {
                            Toast.makeText(SymptomActivity.this, "Please select your answer", Toast.LENGTH_SHORT).show();
                        } else {
                            leftNav.setVisibility(View.VISIBLE);
                            tab++;
                            mViewPager.setCurrentItem(tab);
                        }
                        break;

                    case 4:
                        Symptom4 symp4 = new Symptom4();
                        if (symp4.getCurrentAnswer()==null) {
                            Toast.makeText(SymptomActivity.this, "Please select your answer", Toast.LENGTH_SHORT).show();
                        } else {
                            leftNav.setVisibility(View.VISIBLE);
                            tab++;
                            mViewPager.setCurrentItem(tab);
                        }
                        break;

                    case 5:
                        Symptom5 symp5 = new Symptom5();
                        if (symp5.getCurrentAnswer()==null) {
                            Toast.makeText(SymptomActivity.this, "Please select your answer", Toast.LENGTH_SHORT).show();
                        } else {
                            leftNav.setVisibility(View.VISIBLE);
                            tab++;
                            mViewPager.setCurrentItem(tab);
                        }
                        break;

                    case 6:
                        Symptom6 symp6 = new Symptom6();
                        if (symp6.getCurrentAnswer()==null) {
                            Toast.makeText(SymptomActivity.this, "Please select your answer", Toast.LENGTH_SHORT).show();
                        } else {
                            leftNav.setVisibility(View.VISIBLE);
                            tab++;
                            mViewPager.setCurrentItem(tab);
                        }
                        break;

                    case 7:
                        Symptom7 symp7 = new Symptom7();
                        if (symp7.getCurrentAnswer()==null) {
                            Toast.makeText(SymptomActivity.this, "Please select your answer", Toast.LENGTH_SHORT).show();
                        } else {
                            leftNav.setVisibility(View.VISIBLE);
                            tab++;
                            mViewPager.setCurrentItem(tab);
                        }
                        break;

                    case 8:
                        Symptom8 symp8 = new Symptom8();
                        if (symp8.getCurrentAnswer()==null) {
                            Toast.makeText(SymptomActivity.this, "Please select your answer", Toast.LENGTH_SHORT).show();
                        } else {
                            leftNav.setVisibility(View.VISIBLE);
                            rightNav.setVisibility(View.INVISIBLE);
                            tab++;
                            mViewPager.setCurrentItem(tab);
                        }
                        break;

                }

                if (tab == 9) {
                    mViewPager.setCurrentItem(tab);
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
                    Symptom0 symp0 = new Symptom0();
                    return symp0;
                case 1:
                    Symptom1 symp1 = new Symptom1();
                    return symp1;
                case 2:
                    Symptom2 symp2 = new Symptom2();
                    return symp2;
                case 3:
                    Symptom3 symp3 = new Symptom3();
                    return symp3;
                case 4:
                    Symptom4 symp4 = new Symptom4();
                    return symp4;
                case 5:
                    Symptom5 symp5 = new Symptom5();
                    return symp5;
                case 6:
                    Symptom6 symp6 = new Symptom6();
                    return symp6;
                case 7:
                    Symptom7 symp7 = new Symptom7();
                    return symp7;
                case 8:
                    Symptom8 symp8 = new Symptom8();
                    return symp8;
                case 9:
                    Symptom9 symp9 = new Symptom9();
                    return symp9;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 10;
        }

    }
}
