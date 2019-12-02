package com.example.as9221.kmeancluster;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import java.util.ArrayList;

/**
 * Created by as9221 on 07/11/2017.
 */

public class Symptom8 extends Fragment {
    protected static final String TAG = null;
    private static String text = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.symptom8, null);

        RadioGroup radioAnswer = (RadioGroup) view.findViewById(R.id.radioAnswer);
        //RadioButton radioYES = (RadioButton) view.findViewById(R.id.radioYES);
        //RadioButton radioNO = (RadioButton) view.findViewById(R.id.radioNO);
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

                }
            }
        });

        return view;
    }
    private ArrayList<String> getPreviousAnswer() {
        Symptom7 symp7 = new Symptom7();
        Log.i(TAG, "This is the array list from 1 & 2 " + symp7.getAnswer());
        return symp7.getAnswer();
    }

    public String getCurrentAnswer() {
        String answer = text;
        return answer;
    }

    public ArrayList<String> getAnswer() {
        ArrayList<String> answer = new ArrayList<String>();
        answer = getPreviousAnswer();
        answer.add(text);
        Log.i(TAG, "This is the array list in 1 & 2 & 3 " +   answer);
        return answer;
    }

}
