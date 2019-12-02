package com.example.as9221.kmeancluster;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by as9221 on 07/11/2017.
 */

public class Symptom0 extends Fragment {
    protected static final String TAG = null;
    protected static String symptomT;
    protected static String symptomD;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = View.inflate(getActivity(), R.layout.symptom0, null);

        ImageButton conveyMesImg1 = (ImageButton) view.findViewById(R.id.conveyMesImg1);
        ImageButton conveyMesImg2 = (ImageButton) view.findViewById(R.id.conveyMesImg2);
        ImageButton conveyMesImg3 = (ImageButton) view.findViewById(R.id.conveyMesImg3);
        ImageButton conveyMesImg4 = (ImageButton) view.findViewById(R.id.conveyMesImg4);
        ImageButton conveyMesImg5 = (ImageButton) view.findViewById(R.id.conveyMesImg5);
        ImageButton conveyMesImg6 = (ImageButton) view.findViewById(R.id.conveyMesImg6);
        ImageButton conveyMesImg7 = (ImageButton) view.findViewById(R.id.conveyMesImg7);
        ImageButton conveyMesImg8 = (ImageButton) view.findViewById(R.id.conveyMesImg8);
        ImageButton conveyMesImg9 = (ImageButton) view.findViewById(R.id.conveyMesImg9);
        ImageView conveyMesImg10 = (ImageView) view.findViewById(R.id.conveyMesImg10);

        conveyMesImg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                symptomT = getString(R.string.sympT1);
                symptomD = getString(R.string.sympD1);
                inflateAlertDialog(symptomT, symptomD);

            }
        });

        conveyMesImg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                symptomT = getString(R.string.sympT2);
                symptomD = getString(R.string.sympD2);
                inflateAlertDialog(symptomT, symptomD);

            }
        });

        conveyMesImg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                symptomT = getString(R.string.sympT3);
                symptomD = getString(R.string.sympD3);
                inflateAlertDialog(symptomT, symptomD);

            }
        });

        conveyMesImg4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                symptomT = getString(R.string.sympT4);
                symptomD = getString(R.string.sympD4);
                inflateAlertDialog(symptomT, symptomD);

            }
        });

        conveyMesImg5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                symptomT = getString(R.string.sympT5);
                symptomD = getString(R.string.sympD5);
                inflateAlertDialog(symptomT, symptomD);

            }
        });

        conveyMesImg6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                symptomT = getString(R.string.sympT6);
                symptomD = getString(R.string.sympD6);
                inflateAlertDialog(symptomT, symptomD);

            }
        });

        conveyMesImg7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                symptomT = getString(R.string.sympT7);
                symptomD = getString(R.string.sympD7);
                inflateAlertDialog(symptomT, symptomD);

            }
        });

        conveyMesImg8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                symptomT = getString(R.string.sympT8);
                symptomD = getString(R.string.sympD8);
                inflateAlertDialog(symptomT, symptomD);

            }
        });

        conveyMesImg9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                symptomT = getString(R.string.sympT9);
                symptomD = getString(R.string.sympD9);
                inflateAlertDialog(symptomT, symptomD);

            }
        });


        return view;
    }

    protected void inflateAlertDialog(String symptom_T, String symptom_D) {
        final View v1 = View.inflate(getActivity(), R.layout.education_dialog_layout, null);
        final android.support.v7.app.AlertDialog dialog = new android.support.v7.app.AlertDialog.Builder(getActivity(), R.style.AlertDialogCustom)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })/*.setPositiveButton("Download PDF file", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //download pdf file

                    }
                })*/
                .setView(v1)
                .create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(android.support.v7.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
                dialog.getButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
                Button btnPositive = dialog.getButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE);
                Button btnNegative = dialog.getButton(android.support.v7.app.AlertDialog.BUTTON_NEGATIVE);


                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) btnPositive.getLayoutParams();
                layoutParams.weight = 10;
                btnPositive.setLayoutParams(layoutParams);
                btnNegative.setLayoutParams(layoutParams);

            }
        });

        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();


        TextView symptomTitle = (TextView) v1.findViewById(R.id.symptomTitle);
        TextView symptomDescription = (TextView) v1.findViewById(R.id.symptomDescription);
        symptomTitle.setText(symptom_T);
        symptomDescription.setText(symptom_D);


    }

}
