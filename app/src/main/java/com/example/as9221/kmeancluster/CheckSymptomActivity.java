package com.example.as9221.kmeancluster;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.text.TextWatcher;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class CheckSymptomActivity extends AppCompatActivity {
    private static int id = 0;
    private static int idProf = 0;
    private static int idPatient = 0;
    private static int age = 0;
    private static String address = null;
    private static String gender = null;
    private static String password = null;


    protected static final String TAG = null;
    Button buttonCheckTBPatient;
    Button buttonCheckTBProfessional;

    RadioButton selectedRadioButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_symptom);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.tb_launcher);
        setTitle("Check Symptom");

        buttonCheckTBPatient = (Button) findViewById(R.id.buttonCheckTBPatient);
        buttonCheckTBProfessional = (Button) findViewById(R.id.buttonCheckTBProfessional);



        buttonCheckTBPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show the ProgressDialog on this thread

        View v1 = View.inflate(CheckSymptomActivity.this, R.layout.dialog_layout_info, null);


        final EditText etId = (EditText) v1.findViewById(R.id.etId);
        final EditText etAge = (EditText) v1.findViewById(R.id.etAge);
        final EditText etAddress = (EditText) v1.findViewById(R.id.etPostal);
        final RadioGroup radiogender = (RadioGroup) v1.findViewById(R.id.radioGender);

                final AlertDialog dialog = new AlertDialog.Builder(CheckSymptomActivity.this, R.style.AlertDialogCustom)
                .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //dialog.dismiss();
                        //boolean wantToCloseDialog = false;


                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //Toast.makeText(CameraActivity.this, "Result Image Saved to" + imageTFile , Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        //finish();
                    }
                })
                .setView(v1)
                .create();

                dialog.setCancelable(false);

                dialog.setOnShowListener( new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(CheckSymptomActivity.this, R.color.colorPrimary));
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(CheckSymptomActivity.this, R.color.colorPrimary));

            }
        });

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();

                //Overriding the handler immediately after show is probably a better approach than OnShowListener as described below
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Boolean wantToCloseDialog1 = false;
                        Boolean wantToCloseDialog2 = false;

                        DBHandler db = new DBHandler(CheckSymptomActivity.this);

                        String strId = etId.getText().toString();
                        if (strId.trim().equals("")) {
                            etId.setError(null);
                            Toast.makeText(getApplicationContext(), "plz enter your id ", Toast.LENGTH_SHORT).show();
                            etId.requestFocus();
                            wantToCloseDialog1 = false;
                            //return;
                        } else {
                            id = Integer.parseInt(etId.getText().toString());
                            wantToCloseDialog1 = true;
                        }

                        String strAge = etAge.getText().toString();
                        if (strAge.trim().equals("")) {
                            etAge.setError(null);
                            Toast.makeText(getApplicationContext(), "plz enter your age ", Toast.LENGTH_SHORT).show();
                            etAge.requestFocus();
                            wantToCloseDialog2 = false;
                            //return;
                        } else {
                            age = Integer.parseInt(etAge.getText().toString());
                            wantToCloseDialog2 = true;


                        }


                        address = etAddress.getText().toString();

                        if(radiogender.getCheckedRadioButtonId()==-1)
                        {
                            Toast.makeText(getApplicationContext(), "Please select Gender", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            // get selected radio button from radioGroup
                            int selectedId = radiogender.getCheckedRadioButtonId();
                            if (selectedId == R.id.radioMale) {
                                gender = "Male";
                            } else {
                                gender = "Female";
                            }

                            //Toast.makeText(getApplicationContext(), selectedRadioButton.getText().toString()+" is selected", Toast.LENGTH_SHORT).show();
                        }


                        Log.d(TAG, "gender value .." + gender);

                        // Inserting Shop/Rows
                        Log.d("Insert: ", "Inserting ..");
                        db.addPatient(new Patient(id, age, gender, address, "s1", "s2", "s3", "s4", "s5", "s6", "s7", "s8", "s9"));
                        Log.d("Being Inserted: ", "Inserting ..");

                        //getting the last entered row
                        //Patient patient = new Patient();

                        //patient = db.getLastPatient();
                        //int log = patient.getId();
                        Log.d(TAG,"wantToCloseDialog1: " + wantToCloseDialog1);

                        if(wantToCloseDialog1 == true && wantToCloseDialog2 == true) {

                            Log.d(TAG,"wantToCloseDialog2: " + wantToCloseDialog2);

                            //passing information with the activity
                            Intent myIntent = new Intent(CheckSymptomActivity.this, SymptomActivity.class);
                            myIntent.putExtra("patientID", id);
                            Log.d(TAG,"PatientID in main: " + id);
                            dialog.dismiss();
                            startActivity(myIntent);
                        } else {
                            if (wantToCloseDialog1 == false) etId.requestFocus();
                            if (wantToCloseDialog2 == false) etAge.requestFocus();
                            if(wantToCloseDialog1 == false && wantToCloseDialog2 == false) etId.requestFocus();

                        }
                        //startActivity(new Intent(CheckSymptomActivity.this, CheckSymptomActivity.class));

                        //db.addPatient(new Patient("Dunkin Donuts", "White Plains, NY 10601"));
                        //db.addPatient(new Patient("Pizza Porlar", "North West Avenue, Boston , USA"));
                        //db.addPatient(new Patient("Town Bakers", "Beverly Hills, CA 90210, USA"));

                        // Reading all shops
								/*Log.d("Reading: ", "Reading all shops..");
								List<Shop> shops = db.getAllShops();

								for (Shop shop : shops) {
									String log = "Id: " + shop.getId() + " ,Name: " + shop.getName() + " ,Address: " + shop.getAddress();
									// Writing shops  to log
									Log.d("Shop: : ", log);
								}*/
                        //finish();
                    }
                });


            }

        });


        buttonCheckTBProfessional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show the ProgressDialog on this thread

                View v1 = View.inflate(CheckSymptomActivity.this, R.layout.dialog_layout_info_professional, null);


                final EditText etIdProf = (EditText) v1.findViewById(R.id.etIdProf);
                final EditText etIdPatient = (EditText) v1.findViewById(R.id.etIdPatient);
                final EditText etPassword= (EditText) v1.findViewById(R.id.etPassword);

                final AlertDialog dialog = new AlertDialog.Builder(CheckSymptomActivity.this, R.style.AlertDialogCustom)
                        .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                            }
                        })
                        .setView(v1)
                        .create();

                dialog.setOnShowListener( new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface arg0) {
                        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(CheckSymptomActivity.this, R.color.colorPrimary));
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(CheckSymptomActivity.this, R.color.colorPrimary));

                    }
                });

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();

                dialog.setCancelable(false);

                //Overriding the handler immediately after show is probably a better approach than OnShowListener as described below
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Boolean wantToCloseDialog1 = false;
                        Boolean wantToCloseDialog2 = false;

                        DBHandler db = new DBHandler(CheckSymptomActivity.this);

                        String strId = etIdProf.getText().toString();
                        if (strId.trim().equals("")) {
                            etIdProf.setError(null);
                            Toast.makeText(getApplicationContext(), "plz enter your professional id ", Toast.LENGTH_SHORT).show();
                            etIdProf.requestFocus();
                            wantToCloseDialog1 = false;
                            //return;
                        } else {
                            idProf = Integer.parseInt(etIdProf.getText().toString());
                            wantToCloseDialog1 = true;
                        }

                        String strAge = etIdPatient.getText().toString();
                        if (strAge.trim().equals("")) {
                            etIdPatient.setError(null);
                            Toast.makeText(getApplicationContext(), "plz enter the patient id ", Toast.LENGTH_SHORT).show();
                            etIdPatient.requestFocus();
                            wantToCloseDialog2 = false;
                            //return;
                        } else {
                            idPatient = Integer.parseInt(etIdPatient.getText().toString());
                            wantToCloseDialog2 = true;


                        }


                        password = etPassword.getText().toString();



                        Log.d(TAG,"wantToCloseDialog1: " + wantToCloseDialog1);

                        if(wantToCloseDialog1 == true && wantToCloseDialog2 == true) {

                            Log.d(TAG,"wantToCloseDialog2: " + wantToCloseDialog2);
                            //getting the last entered row
                            Patient professional = new Patient();


                            professional = db.getProfessional(idProf, password);
                            if (professional != null) {
                                if (professional.getPasswordprofessional().equals(password)) {
                                    Toast.makeText(CheckSymptomActivity.this, "Professional details are correct", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(CheckSymptomActivity.this, "Professional details are not correct", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            } else {
                                Toast.makeText(CheckSymptomActivity.this, "Do you want to insert your details", Toast.LENGTH_SHORT).show();
                                //Log.d(TAG,"Value of value1 " + value1);
                                Patient professional1 = new Patient();


                                professional1.setIdprofessional(idProf);
                                professional1.setPasswordprofessional(password);

                                db.addPatientByProfessional(professional1);
                            }

                            //passing information with the activity
                            Intent myIntent = new Intent(CheckSymptomActivity.this, RiskActivity.class);
                            myIntent.putExtra("patientID", idPatient);
                            Log.d(TAG,"PatientID in main: " + idPatient);
                            dialog.dismiss();
                            startActivity(myIntent);
                        } else {
                            if (wantToCloseDialog1 == false) etIdProf.requestFocus();
                            if (wantToCloseDialog2 == false) etIdPatient.requestFocus();
                            if(wantToCloseDialog1 == false && wantToCloseDialog2 == false) etIdProf.requestFocus();

                        }
                        //startActivity(new Intent(CheckSymptomActivity.this, CheckSymptomActivity.class));

                        //db.addPatient(new Patient("Dunkin Donuts", "White Plains, NY 10601"));
                        //db.addPatient(new Patient("Pizza Porlar", "North West Avenue, Boston , USA"));
                        //db.addPatient(new Patient("Town Bakers", "Beverly Hills, CA 90210, USA"));

                        // Reading all shops
								/*Log.d("Reading: ", "Reading all shops..");
								List<Shop> shops = db.getAllShops();

								for (Shop shop : shops) {
									String log = "Id: " + shop.getId() + " ,Name: " + shop.getName() + " ,Address: " + shop.getAddress();
									// Writing shops  to log
									Log.d("Shop: : ", log);
								}*/
                        //finish();
                    }
                });


            }

        });
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
