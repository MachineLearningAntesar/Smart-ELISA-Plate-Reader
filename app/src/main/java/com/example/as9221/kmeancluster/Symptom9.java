package com.example.as9221.kmeancluster;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.google.gson.internal.UnsafeAllocator.create;


/**
 * Created by as9221 on 07/11/2017.
 */

public class Symptom9 extends Fragment {
    protected static final String TAG = null;
    private static String text = null;
    String risk = null;
    int patientID;
    ArrayList<String> answer = null;
    Patient patient = new Patient();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.symptom9, null);
        patientID = ((SymptomActivity) getActivity()).patientId;
        Log.d(TAG,"patientID in symptom9 after receiving it from checksymptomactivity " + patientID);

        RadioGroup radioAnswer = (RadioGroup) view.findViewById(R.id.radioAnswer);
        //RadioButton radioYES = (RadioButton) view.findViewById(R.id.radioYES);
        //RadioButton radioNO = (RadioButton) view.findViewById(R.id.radioNO);
        radioAnswer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
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

        Button buttonCheckAssessment = (Button) view.findViewById(R.id.buttonCheckAssessment);
        buttonCheckAssessment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (text == null) {
                    Toast.makeText(getActivity(), "Please select your answer", Toast.LENGTH_SHORT).show();
                } else {
                    int count = 0;
                    answer = getAnswer();
                    //now iterate on the current list
                    for (int j = 0; j < answer.size(); j++) {
                        String s = answer.get(j);
                        Log.i(TAG, "the array list for the assessment: " + s);

                        if (s.equals("YES")) {
                            count++;
                            Log.i(TAG, "count of YES: " + count);

                        }
                    }

                    DBHandler db = new DBHandler(getActivity());

                    //getting the last entered row

                    Log.d(TAG,"patientID in Symptom before getPatient " + patientID);
                    patient = db.getPatient(patientID);
                    String log = "Id: " + patient.getId() + " ,Age: " + patient.getAge() + " ,Address: " + patient.getAddress() + " ,Gender: " + patient.getGender() + " ,Symptom1: " + patient.getSymptom1()
                            + " ,Symptom2: " + patient.getSymptom2() + " ,Symptom3: " + patient.getSymptom3()+ " ,Symptom4: " + patient.getSymptom4() + " ,Symptom5: " + patient.getSymptom5()
                            + " ,Symptom6: " + patient.getSymptom6() + " ,Symptom7: " + patient.getSymptom7() + " ,Symptom8: " + patient.getSymptom8() + " ,Symptom9: " + patient.getSymptom9();
                    // Writing patients  to log
                    Log.d("Patient: : ", log);

                    Log.d(TAG,"patientID in Symptom After getPatient " + log);


                    //patient.setId(answer.get(7));
                    patient.setSymptom1(answer.get(0));
                    patient.setSymptom2(answer.get(1));
                    patient.setSymptom3(answer.get(2));
                    patient.setSymptom4(answer.get(3));
                    patient.setSymptom5(answer.get(4));
                    patient.setSymptom6(answer.get(5));
                    patient.setSymptom7(answer.get(6));
                    patient.setSymptom8(answer.get(7));
                    patient.setSymptom9(answer.get(8));

                    db.updateSymptom(patient);
                    //getPatientSymptom();

                    String assessment = null;

                    switch (count) {
                        case 9:
                            assessment = "You have developed 100% of the TB symptom";
                            risk = "Sever Risk";
                            break;
                        case 8:
                            assessment = "You have developed more than 85% of the TB symptom";
                            risk = "Sever Risk";
                            break;
                        case 7:
                            assessment = "You have developed more than 70% of the TB symptom";
                            risk = "High Risk";
                            break;
                        case 6:
                            assessment = "You have developed more than 55% of the TB symptom";
                            risk = "Moderate Risk";
                            break;
                        case 5:
                            assessment = "You have developed more than 40% of the TB symptom";
                            risk = "Moderate Risk";
                            break;
                        case 4:
                            assessment = "You have developed more than 25% of the TB symptom";
                            risk = "Intermediate Risk";
                            break;
                        case 3:
                            assessment = "You have developed more than 10% of the TB symptom";
                            risk = "Low Risk";
                            break;
                        case 2:
                            assessment = "You have developed more than 10% of the TB symptom";
                            risk = "Low Risk";
                            break;
                        case 1:
                            assessment = "You have developed more than 10% of the TB symptom";
                            risk = "Low Risk";
                            break;
                        case 0:
                            assessment = "You have not developed any related TB symptom";
                            risk = "No Risk";
                            break;
                        default:
                            assessment = "The assessment was not successful, please try again";
                            break;
                    }
                    final View v1 = View.inflate(getActivity(), R.layout.assessment_dialog_layout, null);
                    final android.support.v7.app.AlertDialog dialog = new android.support.v7.app.AlertDialog.Builder(getActivity(), R.style.AlertDialogCustom)
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    getActivity().finish();
                                }
                            }).setPositiveButton("Get Report", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    createandDisplayPdf("This is my first pdf file", patient);

                                }
                            })
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

                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.show();


                    TextView riskResult = (TextView) v1.findViewById(R.id.resultRiskTView);
                    TextView assessmentResult = (TextView) v1.findViewById(R.id.resultAssessTView);
                    SeekBar seekBarRisk = (SeekBar) v1.findViewById(R.id.seekBarRisk);

                    if (risk.equals("No Risk")) {

                        riskResult.setText(risk);
                        assessmentResult.setText(assessment);
                        Drawable bckgrndDr = new ColorDrawable(Color.RED);
                        Drawable secProgressDr = new ColorDrawable(Color.GRAY);
                        Drawable progressDr = new ScaleDrawable(new ColorDrawable(getResources().getColor(R.color.secondary_text)), Gravity.LEFT, 1, -1);
                        LayerDrawable resultDr = new LayerDrawable(new Drawable[] { bckgrndDr, secProgressDr, progressDr });

                        resultDr.setId(0, android.R.id.background);
                        resultDr.setId(1, android.R.id.secondaryProgress);
                        resultDr.setId(2, android.R.id.progress);
                        seekBarRisk.setProgress(0);
                        seekBarRisk.setProgressDrawable(resultDr);
                        seekBarRisk.setEnabled(false);

                    }

                    if (risk.equals("Low Risk")) {

                        riskResult.setText(risk);
                        //riskResult.setTextColor(Color.GREEN);
                        assessmentResult.setText(assessment);
                        Drawable bckgrndDr = new ColorDrawable(Color.RED);
                        Drawable secProgressDr = new ColorDrawable(Color.GRAY);
                        Drawable progressDr = new ScaleDrawable(new ColorDrawable(Color.GREEN), Gravity.LEFT, 1, -1);
                        LayerDrawable resultDr = new LayerDrawable(new Drawable[] { bckgrndDr, secProgressDr, progressDr });

                        resultDr.setId(0, android.R.id.background);
                        resultDr.setId(1, android.R.id.secondaryProgress);
                        resultDr.setId(2, android.R.id.progress);
                        seekBarRisk.setProgress(14);
                        seekBarRisk.setProgressDrawable(resultDr);
                        seekBarRisk.setEnabled(false);

                    }

                    if (risk.equals("Intermediate Risk")) {

                        riskResult.setText(risk);
                        //riskResult.setTextColor(Color.BLUE);
                        assessmentResult.setText(assessment);
                        Drawable bckgrndDr = new ColorDrawable(Color.RED);
                        Drawable secProgressDr = new ColorDrawable(Color.GRAY);
                        Drawable progressDr = new ScaleDrawable(new ColorDrawable(Color.BLUE), Gravity.LEFT, 1, -1);
                        LayerDrawable resultDr = new LayerDrawable(new Drawable[] { bckgrndDr, secProgressDr, progressDr });

                        resultDr.setId(0, android.R.id.background);
                        resultDr.setId(1, android.R.id.secondaryProgress);
                        resultDr.setId(2, android.R.id.progress);
                        seekBarRisk.setProgress(34);
                        seekBarRisk.setProgressDrawable(resultDr);
                        seekBarRisk.setEnabled(false);

                    }

                    if (risk.equals("Moderate Risk")) {

                        riskResult.setText(risk);
                        //riskResult.setTextColor(Color.YELLOW);
                        assessmentResult.setText(assessment);
                        Drawable bckgrndDr = new ColorDrawable(Color.RED);
                        Drawable secProgressDr = new ColorDrawable(Color.GRAY);
                        Drawable progressDr = new ScaleDrawable(new ColorDrawable(Color.YELLOW), Gravity.LEFT, 1, -1);
                        LayerDrawable resultDr = new LayerDrawable(new Drawable[] { bckgrndDr, secProgressDr, progressDr });

                        resultDr.setId(0, android.R.id.background);
                        resultDr.setId(1, android.R.id.secondaryProgress);
                        resultDr.setId(2, android.R.id.progress);
                        seekBarRisk.setProgress(64);
                        seekBarRisk.setProgressDrawable(resultDr);
                        seekBarRisk.setEnabled(false);


                    }

                    if (risk.equals("High Risk")) {

                        riskResult.setText(risk);
                        //riskResult.setTextColor(getResources().getColor(R.color.amber_risk));
                        assessmentResult.setText(assessment);
                        Drawable bckgrndDr = new ColorDrawable(Color.RED);
                        Drawable secProgressDr = new ColorDrawable(Color.GRAY);
                        Drawable progressDr = new ScaleDrawable(new ColorDrawable(getResources().getColor(R.color.amber_risk)), Gravity.LEFT, 1, -1);
                        LayerDrawable resultDr = new LayerDrawable(new Drawable[] { bckgrndDr, secProgressDr, progressDr });

                        resultDr.setId(0, android.R.id.background);
                        resultDr.setId(1, android.R.id.secondaryProgress);
                        resultDr.setId(2, android.R.id.progress);
                        seekBarRisk.setProgress(84);
                        seekBarRisk.setProgressDrawable(resultDr);
                        seekBarRisk.setEnabled(false);


                    }

                    if (risk.equals("Sever Risk")) {

                        riskResult.setText(risk);
                        //riskResult.setTextColor(Color.RED);
                        assessmentResult.setText(assessment);
                        Drawable bckgrndDr = new ColorDrawable(Color.RED);
                        Drawable secProgressDr = new ColorDrawable(Color.GRAY);
                        Drawable progressDr = new ScaleDrawable(new ColorDrawable(Color.RED), Gravity.LEFT, 1, -1);
                        LayerDrawable resultDr = new LayerDrawable(new Drawable[] { bckgrndDr, secProgressDr, progressDr });

                        resultDr.setId(0, android.R.id.background);
                        resultDr.setId(1, android.R.id.secondaryProgress);
                        resultDr.setId(2, android.R.id.progress);
                        seekBarRisk.setProgress(99);
                        seekBarRisk.setProgressDrawable(resultDr);
                        seekBarRisk.setEnabled(false);

                    }

                }
            }
        });

        return view;
    }

    private ArrayList<String> getPreviousAnswer() {
        Symptom8 symp8 = new Symptom8();
        Log.i(TAG, "This is the array list from 1 & 2 " + symp8.getAnswer());
        return symp8.getAnswer();
    }

    public String getCurrentAnswer() {
        String answer = text;
        return answer;
    }

    public ArrayList<String> getAnswer() {
        ArrayList<String> answer = new ArrayList<String>();
        answer = getPreviousAnswer();
        answer.add(text);
        Log.i(TAG, "This is the array list in 1 & 2 & 3 & 4 & 5 & 6 & 7 " + answer);
        return answer;
    }

    public void getPatientSymptom() {
        // Getting one patient
        DBHandler db = new DBHandler(getActivity());

        // Reading all shops
        Log.i(TAG, "Reading: Reading all patients..");
        List<Patient> patients = db.getAllPatients();

        for (Patient patient : patients) {
            String log = "Id: " + patient.getId() + " ,Age: " + patient.getAge() + " ,Address: " + patient.getAddress() + " ,Gender: " + patient.getGender() + " ,Symptom1: " + patient.getSymptom1()
                    + " ,Symptom2: " + patient.getSymptom2() + " ,Symptom3: " + patient.getSymptom3()+ " ,Symptom4: " + patient.getSymptom4() + " ,Symptom5: " + patient.getSymptom5()
                    + " ,Symptom6: " + patient.getSymptom6() + " ,Symptom7: " + patient.getSymptom7() + " ,Symptom8: " + patient.getSymptom8() + " ,Symptom9: " + patient.getSymptom9();
            // Writing patients  to log
            Log.d("Patient: : ", log);
        }
    }

    // Method for creating a pdf file from text, saving it then opening it for display
    public void createandDisplayPdf(String text, Patient patient1)
    {
        //Document doc = new Document();
        Document doc = new Document(PageSize.A4, 85.35826771653F, 56.90551181102F, 140, 130.90551181102F);


        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Dir";

            File dir = new File(path);
            if(!dir.exists())
                dir.mkdirs();

            File file = new File(dir, "newFile.pdf");
            FileOutputStream fOut = new FileOutputStream(file);

            PdfWriter.getInstance(doc, fOut);

            //open the document
            doc.open();


            addLogo(doc);
            //doc.add( Chunk.NEWLINE );
            //doc.add( Chunk.NEWLINE );

            //Paragraph p1 = new Paragraph(text);
            Paragraph p = new Paragraph("Disclaimer", new Font(Font.FontFamily.HELVETICA, 18));
           // p.setAlignment(Element.ALIGN_CENTER);
            Paragraph p1 = new Paragraph("Please note that the checkup report provided by the TB Test app is based on the information given by the user and match the symptom based model stored in the app and is not based on a Doctor opinion.");

            //add paragraph to document
            doc.add(p);
            //p.SpacingBefore
            doc.add(new Paragraph(" "));
            doc.add(new LineSeparator());
            doc.add(p1);
            doc.add( Chunk.NEWLINE );
            //the following 3 lines to add new separator
            //LineSeparator sep = new LineSeparator();
            //sep.setOffset(5);
            //doc.add(sep);
            doc.add( Chunk.NEWLINE );
            doc.add(createBasicInfoTable(patient1));
            // add a couple of blank lines
            doc.add( Chunk.NEWLINE );
            doc.add(createSymptomTable(patient1));

        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        }
        finally {
            doc.close();
        }

        viewPdf("newFile.pdf", "Dir");
    }

    public static PdfPTable createSymptomTable(Patient patient1) {


        // a table with two columns
        PdfPTable table = new PdfPTable(2);
        // the cell object
        PdfPCell cell;
        // we add a cell with colspan 2
        cell = new PdfPCell(new Phrase("Reported Symptoms"));
        cell.setColspan(2);
        table.addCell(cell);
        // we add the 9 remaining cells with addCell()
        table.addCell("Persistent Cough");
        table.addCell(patient1.getSymptom1());
        table.addCell("Chest Pain");
        table.addCell(patient1.getSymptom2());
        table.addCell("Coughing up blood");
        table.addCell(patient1.getSymptom3());
        table.addCell("Constant weakness");
        table.addCell(patient1.getSymptom4());
        table.addCell("Weight loss");
        table.addCell(patient1.getSymptom5());
        table.addCell("Appetite loss");
        table.addCell(patient1.getSymptom6());
        table.addCell("Chills");
        table.addCell(patient1.getSymptom7());
        table.addCell("Fever");
        table.addCell(patient1.getSymptom8());
        table.addCell("Night sweats");
        table.addCell(patient1.getSymptom9());
        return table;
    }

    public static PdfPTable createBasicInfoTable(Patient patient1) {
        // a table with two columns
        PdfPTable table = new PdfPTable(2);
        // the cell object
        PdfPCell cell;
        // we add a cell with colspan 2
        cell = new PdfPCell(new Phrase("Related Information"));
        cell.setColspan(2);
        table.addCell(cell);

        // we add the 3 remaining cells with addCell()
        table.addCell("Age");
        table.addCell(String.valueOf(patient1.getAge()));
        table.addCell("Gender");
        table.addCell(patient1.getGender());
        table.addCell("Postal Address");
        table.addCell(patient1.getAddress());
        return table;
    }

    // Method for opening a pdf file
    private void viewPdf(String file, String directory) {

        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/" + directory + "/" + file);
        Uri path = Uri.fromFile(pdfFile);

        // Setting the intent for pdf reader
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getActivity(), "Can't read pdf file", Toast.LENGTH_SHORT).show();
        }
    }

    /*public Image addImage() {
        Image signature = null;
        try {
            InputStream inputStream = getActivity().getAssets().open(
                    "tb_launcher2.png");
            Bitmap bmp = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);

            signature = Image.getInstance(stream.toByteArray());
            signature.setAbsolutePosition(500f, 700f);
            signature.scalePercent(100f);

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (BadElementException e) {
            e.printStackTrace();
        }
        return signature;
    }*/

    public void addLogo(Document document) throws DocumentException {
        try { // Get user Settings GeneralSettings getUserSettings =

            Rectangle rectDoc = document.getPageSize();
            float width = rectDoc.getWidth();
            float height = rectDoc.getHeight();
            float imageStartX = width - document.rightMargin() - 140f;
            float imageStartY = height - document.topMargin() - 5f; //reduce this number to go up

            System.gc();

            InputStream ims = getActivity().getAssets().open("tb_launcher4.png");
            Bitmap bmp = BitmapFactory.decodeStream(ims);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            bmp.compress(Bitmap.CompressFormat.PNG, 50, stream);

            byte[] byteArray = stream.toByteArray();
            // PdfImage img = new PdfImage(arg0, arg1, arg2)

            // Converting byte array into image Image img =
            Image img = Image.getInstance(byteArray); // img.scalePercent(50);
            img.setAlignment(Image.TEXTWRAP);
            //img.scaleAbsolute(130f, 50f);
            img.setAbsolutePosition(imageStartX, imageStartY); // Adding Image
            document.add(img);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
