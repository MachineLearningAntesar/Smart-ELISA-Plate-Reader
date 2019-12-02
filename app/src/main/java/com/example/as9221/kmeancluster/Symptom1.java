package com.example.as9221.kmeancluster;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by as9221 on 07/11/2017.
 */

public class Symptom1 extends Fragment {
    protected static final String TAG = null;
    private static String text = null;
    RadioButton radioYES;
    RadioButton radioNO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = View.inflate(getActivity(), R.layout.symptom1, null);
        RadioGroup radioAnswer = (RadioGroup) view.findViewById(R.id.radioAnswer);
        radioYES = (RadioButton) view.findViewById(R.id.radioYES);
        radioNO = (RadioButton) view.findViewById(R.id.radioNO);
        radioAnswer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                //switch(checkedId){
                    case R.id.radioYES:
                        // do operations specific to this selection
                        text = "YES";
                        getAnswer();
                        break;
                    case R.id.radioNO:
                        // do operations specific to this selection
                        text = "NO";
                        getAnswer();
                        break;
                    default:
                        Toast.makeText(getActivity(), "Please select your answer", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

       return view;
    }



    public String getAnswer() {
        return text;
    }
}
