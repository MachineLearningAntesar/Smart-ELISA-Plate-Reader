package com.example.as9221.kmeancluster;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;


/**
 * Created by as9221 on 07/11/2017.
 */

public class RiskGroup2 extends Fragment {
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

    Button buttonCheckReport;
    ArrayList<String> GR2Criterion = new ArrayList<>(Arrays.asList("NO", "NO", "NO", "NO", "NO", "NO", "NO", "NO", "NO", "NO", "NO"));
    Patient patient = new Patient();
    int patientID;
    private static ArrayList<String> gr1Values;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = View.inflate(getActivity(), R.layout.activity_risk_group2, null);
        patientID = ((RiskActivity) getActivity()).patientId;
        Log.d(TAG,"patientID " + patientID);

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

        gr1Values = getGR1CriterionValues();


        criterion1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(criterion1.isChecked()){
                    Log.d(TAG,"Checked");
                    GR2Criterion.set(0,"YES");
                }else{
                    Log.d(TAG, "Un-Checked");
                    GR2Criterion.set(0,"NO");
                }
                getGR2Criterion();
            }
        });

        criterion2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(criterion2.isChecked()){
                    Log.d(TAG,"Checked");
                    GR2Criterion.set(1,"YES");
                }else{
                    Log.d(TAG, "Un-Checked");
                    GR2Criterion.set(1,"NO");
                }
                getGR2Criterion();
            }
        });

        criterion3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(criterion3.isChecked()){
                    Log.d(TAG,"Checked");
                    GR2Criterion.set(2,"YES");
                }else{
                    Log.d(TAG, "Un-Checked");
                    GR2Criterion.set(2,"NO");
                }
                getGR2Criterion();
            }
        });

        criterion4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(criterion4.isChecked()){
                    Log.d(TAG,"Checked");
                    GR2Criterion.set(3,"YES");
                }else{
                    Log.d(TAG, "Un-Checked");
                    GR2Criterion.set(3,"NO");
                }
                getGR2Criterion();
            }
        });

        criterion5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(criterion5.isChecked()){
                    Log.d(TAG,"Checked");
                    GR2Criterion.set(4,"YES");
                }else{
                    Log.d(TAG, "Un-Checked");
                    GR2Criterion.set(4,"NO");
                }
                getGR2Criterion();
            }
        });

        criterion6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(criterion6.isChecked()){
                    Log.d(TAG,"Checked");
                    GR2Criterion.set(5,"YES");
                }else{
                    Log.d(TAG, "Un-Checked");
                    GR2Criterion.set(5,"NO");
                }
                getGR2Criterion();
            }
        });

        criterion7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(criterion7.isChecked()){
                    Log.d(TAG,"Checked");
                    GR2Criterion.set(6,"YES");
                }else{
                    Log.d(TAG, "Un-Checked");
                    GR2Criterion.set(6,"NO");
                }
                getGR2Criterion();
            }
        });

        criterion8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(criterion8.isChecked()){
                    Log.d(TAG,"Checked");
                    GR2Criterion.set(7,"YES");
                }else{
                    Log.d(TAG, "Un-Checked");
                    GR2Criterion.set(7,"NO");
                }
                getGR2Criterion();
            }
        });

        criterion9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(criterion9.isChecked()){
                    Log.d(TAG,"Checked");
                    GR2Criterion.set(8,"YES");
                }else{
                    Log.d(TAG, "Un-Checked");
                    GR2Criterion.set(8,"NO");
                }
                getGR2Criterion();
            }
        });

        criterion10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(criterion10.isChecked()){
                    Log.d(TAG,"Checked");
                    GR2Criterion.set(9,"YES");
                }else{
                    Log.d(TAG, "Un-Checked");
                    GR2Criterion.set(9,"NO");
                }
                getGR2Criterion();
            }
        });

        criterion11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(criterion11.isChecked()){
                    Log.d(TAG,"Checked");
                    GR2Criterion.set(10,"YES");
                }else{
                    Log.d(TAG, "Un-Checked");
                    GR2Criterion.set(10,"NO");
                }
                getGR2Criterion();
            }
        });

        buttonCheckReport = (Button) view.findViewById(R.id.buttonCheckReport);
        buttonCheckReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                getGR1CriterionValues();
                getGR2Criterion();
                Patient patient = null;
                createandDisplayPdf(text, patient);
            }
        });

        return view;
    }


    private ArrayList<String> getGR1CriterionValues() {
        RiskGroup1 RG1 = new RiskGroup1();
        Log.i(TAG, "Text from previous Risk Group 1: " + RG1.getGR1Criterion());
        return RG1.getGR1Criterion();
    }

    public ArrayList<String> getGR2Criterion() {
        Log.i(TAG, "This is the array list for Group Risk 2: " + GR2Criterion);
        return GR2Criterion;
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

            File file = new File(dir, "checkupReport.pdf");
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
            //doc.add( Chunk.NEWLINE );
            //doc.add(createBasicInfoTable(patient1));
            // add a couple of blank lines
            doc.add( Chunk.NEWLINE );
            Bundle b = this.getArguments();
            /*ArrayList<String> GR1CriterionValues = new ArrayList<>();
            if(b != null){
                Log.d(TAG, "b - null");

                GR1CriterionValues = b.getStringArrayList("Key");
                doc.add(createRiskGroup1(GR1CriterionValues));

            }*/
            //doc.add(createRiskGroup1(GR1CriterionValues));
            DBHandler db = new DBHandler(getActivity());

            //getting the last entered row

            patient = db.getPatient(patientID);
            int log = patient.getId();
            Log.d(TAG,"patientID in Risk Activity After getPatient " + log);
            doc.add(createBasicInfoTable(patient));

            doc.add( Chunk.NEWLINE );

            doc.add(createSymptomTable(patient));
            doc.add( Chunk.NEWLINE );


            doc.add(createRiskGroup1(getGR1CriterionValues()));



            doc.add( Chunk.NEWLINE );
            //ArrayList<String> GR2CriterionValues = getGR2Criterion();
            doc.add(createRiskGroup2(getGR2Criterion()));

        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        }
        finally {
            doc.close();
        }

        viewPdf("checkupReport.pdf", "Dir");
    }

    public static PdfPTable createRiskGroup1(ArrayList<String> GR1CriterionValues) {

        // a table with two columns
        PdfPTable table = new PdfPTable(2);
        // the cell object
        PdfPCell cell;
        // we add a cell with colspan 2
        cell = new PdfPCell(new Phrase("Do you have a high chance of being infected with TB bacteria due to the following reasons:"));
        cell.setColspan(2);
        table.addCell(cell);

        // we add the 11 remaining cells with addCell()
        table.addCell("Close connection of a person with active TB disease");
        table.addCell(GR1CriterionValues.get(0));
        table.addCell("Immigrated from areas of the world with high rates of TB");
        table.addCell(GR1CriterionValues.get(1));
        table.addCell("A child lives in the same household as a person who has a positive TB test");
        table.addCell(GR1CriterionValues.get(2));
        table.addCell("Homeless persons");
        table.addCell(GR1CriterionValues.get(3));
        table.addCell("Injection drug users");
        table.addCell(GR1CriterionValues.get(4));
        table.addCell("HIV persons");
        table.addCell(GR1CriterionValues.get(5));
        table.addCell("Persons who work or reside with people with high risk for TB in hospitals");
        table.addCell(GR1CriterionValues.get(6));
        table.addCell("Persons who work or reside in homeless shelters");
        table.addCell(GR1CriterionValues.get(7));
        table.addCell("Persons who work or reside in correctional facilities");
        table.addCell(GR1CriterionValues.get(8));
        table.addCell("Persons who work as nursing homes for those with active TB");
        table.addCell(GR1CriterionValues.get(9));
        table.addCell("Persons who work as residential homes for those with HIV");
        table.addCell(GR1CriterionValues.get(10));

        return table;
    }

    public static PdfPTable createRiskGroup2(ArrayList<String> GR2CriterionValues) {

        // a table with two columns
        PdfPTable table = new PdfPTable(2);
        // the cell object
        PdfPCell cell;
        // we add a cell with colspan 2
        cell = new PdfPCell(new Phrase("Do you have any conditions that weaken your immune system due to the following conditions:"));
        cell.setColspan(2);
        table.addCell(cell);

        // we add the 11 remaining cells with addCell()
        table.addCell("HIV infection");
        table.addCell(GR2CriterionValues.get(0));
        table.addCell("Substance abuse such as Illicit drug users (IDU)");
        table.addCell(GR2CriterionValues.get(1));
        table.addCell("Silicosis");
        table.addCell(GR2CriterionValues.get(2));
        table.addCell("Children less than 3 years of age");
        table.addCell(GR2CriterionValues.get(3));
        table.addCell("Diabetes mellitus");
        table.addCell(GR2CriterionValues.get(4));
        table.addCell("Severe kidney disease");
        table.addCell(GR2CriterionValues.get(5));
        table.addCell("Low body weight");
        table.addCell(GR2CriterionValues.get(6));
        table.addCell("Head or neck cancer");
        table.addCell(GR2CriterionValues.get(7));
        table.addCell("Treatments for organ transplants");
        table.addCell(GR2CriterionValues.get(8));
        table.addCell("Treatments for rheumatoid arthritis disease");
        table.addCell(GR2CriterionValues.get(9));
        table.addCell("Treatments for crohn’s disease");
        table.addCell(GR2CriterionValues.get(10));

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
