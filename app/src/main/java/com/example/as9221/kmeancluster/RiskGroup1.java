package com.example.as9221.kmeancluster;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by as9221 on 07/11/2017.
 */

public class RiskGroup1 extends Fragment {
    protected static final String TAG = null;
    private static String text = null;
    CheckBox criterion1;
    CheckBox criterion2;
    CheckBox criterion3;
    CheckBox criterion4;
    CheckBox criterion5;
    CheckBox criterion6;
    CheckBox criterion7;
    CheckBox criterion8;
    CheckBox criterion9;
    CheckBox criterion10;
    CheckBox criterion11;

    // definition private static is important to pass the data to the next fragment
    private static ArrayList<String> GR1Criterion = new ArrayList<>(Arrays.asList("NO", "NO", "NO", "NO", "NO", "NO", "NO", "NO", "NO", "NO", "NO"));
    //private static ArrayList<String> GR1CriterionValues = new ArrayList<String>();


    // this is xml command to change the design of the checkbox
    // android:button="@drawable/custom_checkbox"

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = View.inflate(getActivity(), R.layout.activity_risk_group1, null);

        //criterion 1 to 11
        criterion1 = (CheckBox) view.findViewById(R.id.criterion1);
        criterion2 = (CheckBox) view.findViewById(R.id.criterion2);
        criterion3 = (CheckBox) view.findViewById(R.id.criterion3);
        criterion4 = (CheckBox) view.findViewById(R.id.criterion4);
        criterion5 = (CheckBox) view.findViewById(R.id.criterion5);
        criterion6 = (CheckBox) view.findViewById(R.id.criterion6);
        criterion7 = (CheckBox) view.findViewById(R.id.criterion7);
        criterion8 = (CheckBox) view.findViewById(R.id.criterion8);
        criterion9 = (CheckBox) view.findViewById(R.id.criterion9);
        criterion10 = (CheckBox) view.findViewById(R.id.criterion10);
        criterion11 = (CheckBox) view.findViewById(R.id.criterion11);

        criterion1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(criterion1.isChecked()){
                    Log.d(TAG,"Checked");
                    GR1Criterion.set(0, "YES");
                }else{
                    Log.d(TAG, "Un-Checked");
                    GR1Criterion.set(0,"NO");
                }
                Log.d(TAG,"Criterion1" + GR1Criterion.get(0));
                getGR1Criterion();

            }
        });

        criterion2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(criterion2.isChecked()){
                    Log.d(TAG,"Checked");
                    GR1Criterion.set(1,"YES");
                }else{
                    Log.d(TAG, "Un-Checked");
                    GR1Criterion.set(1,"NO");
                }
                Log.d(TAG,"Criterion2" + GR1Criterion.get(1));
                getGR1Criterion();

            }
        });

        criterion3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(criterion3.isChecked()){
                    Log.d(TAG,"Checked");
                    GR1Criterion.set(2,"YES");
                }else{
                    Log.d(TAG, "Un-Checked");
                    GR1Criterion.set(2,"NO");
                }
                Log.d(TAG,"Criterion3" + GR1Criterion.get(2));
                getGR1Criterion();

            }
        });

        criterion4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(criterion4.isChecked()){
                    Log.d(TAG,"Checked");
                    GR1Criterion.set(3,"YES");
                }else{
                    Log.d(TAG, "Un-Checked");
                    GR1Criterion.set(3,"NO");
                }
                Log.d(TAG,"Criterion4" + GR1Criterion.get(3));
                getGR1Criterion();

            }
        });

        criterion5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(criterion5.isChecked()){
                    Log.d(TAG,"Checked");
                    GR1Criterion.set(4,"YES");
                }else{
                    Log.d(TAG, "Un-Checked");
                    GR1Criterion.set(4,"NO");
                }
                Log.d(TAG,"Criterion5" + GR1Criterion.get(4));
                getGR1Criterion();

            }
        });

        criterion6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(criterion6.isChecked()){
                    Log.d(TAG,"Checked");
                    GR1Criterion.set(5,"YES");
                }else{
                    Log.d(TAG, "Un-Checked");
                    GR1Criterion.set(5,"NO");
                }
                Log.d(TAG,"Criterion6" + GR1Criterion.get(5));
                getGR1Criterion();

            }
        });

        criterion7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(criterion7.isChecked()){
                    Log.d(TAG,"Checked");
                    GR1Criterion.set(6,"YES");
                }else{
                    Log.d(TAG, "Un-Checked");
                    GR1Criterion.set(6,"NO");
                }
                Log.d(TAG,"Criterion7" + GR1Criterion.get(6));
                getGR1Criterion();

            }
        });

        criterion8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(criterion8.isChecked()){
                    Log.d(TAG,"Checked");
                    GR1Criterion.set(7,"YES");
                }else{
                    Log.d(TAG, "Un-Checked");
                    GR1Criterion.set(7,"NO");
                }
                Log.d(TAG,"Criterion8" + GR1Criterion.get(7));
                getGR1Criterion();

            }
        });

        criterion9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(criterion9.isChecked()){
                    Log.d(TAG,"Checked");
                    GR1Criterion.set(8,"YES");
                }else{
                    Log.d(TAG, "Un-Checked");
                    GR1Criterion.set(8,"NO");
                }
                Log.d(TAG,"Criterion9" + GR1Criterion.get(8));
                getGR1Criterion();

            }
        });

        criterion10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(criterion10.isChecked()){
                    Log.d(TAG,"Checked");
                    GR1Criterion.set(9,"YES");
                }else{
                    Log.d(TAG, "Un-Checked");
                    GR1Criterion.set(9,"NO");
                }
                Log.d(TAG,"Criterion10" + GR1Criterion.get(9));
                getGR1Criterion();

            }
        });

        criterion11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(criterion11.isChecked()){
                    Log.d(TAG,"Checked");
                    GR1Criterion.set(10,"YES");
                }else{
                    Log.d(TAG, "Un-Checked");
                    GR1Criterion.set(10,"NO");
                }
                Log.d(TAG,"Criterion11" + GR1Criterion.get(10));
                getGR1Criterion();

            }
        });


        return view;
    }

    public ArrayList<String> getGR1Criterion() {
        //Log.i(TAG, "This is the array list for Group Risk 1: " +   GR1Criterion);

        //GR1CriterionValues = GR1Criterion;
        Log.i(TAG, "This is the array list for Group Risk 1: " +   GR1Criterion);
        /*Bundle b = new Bundle();
        b.putStringArrayList("Key", GR1Criterion);

        RiskGroup2 RG2 = new RiskGroup2();
        RG2.setArguments(b);*/
        //getFragmentManager().beginTransaction().replace(R.id., RG2);
        return GR1Criterion;
    }


}
