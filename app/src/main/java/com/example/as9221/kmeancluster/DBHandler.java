package com.example.as9221.kmeancluster;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 13;


    // Tag to show messages
    protected static final String TAG = null;

    // Database Name
    private static final String DATABASE_NAME = "patientInformation";

    // Personal Info table name
    private static final String TABLE_PINFO1 = "personalInformation";

    // Professional Info
    private static final String TABLE_PRINFO1 = "professionalInformation";



    // Patients Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_AGE = "age";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_POSTAL_ADDR = "postal_address";
    private static final String SYMPTOM_1 = "symptom1";
    private static final String SYMPTOM_2 = "symptom2";
    private static final String SYMPTOM_3 = "symptom3";
    private static final String SYMPTOM_4 = "symptom4";
    private static final String SYMPTOM_5 = "symptom5";
    private static final String SYMPTOM_6 = "symptom6";
    private static final String SYMPTOM_7 = "symptom7";
    private static final String SYMPTOM_8 = "symptom8";
    private static final String SYMPTOM_9 = "symptom9";

    // Professional Table Columns names



    // Patients Symptom Table Columns names
    private static final String KEY_IDPR = "idprofessional";
    private static final String KEY_PASSPR = "password";


    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("Start Creating: ", "Creating ..");
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_PINFO1 + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_AGE + " INTEGER,"
                + KEY_GENDER + " TEXT," + KEY_POSTAL_ADDR + " TEXT," + SYMPTOM_1 + " TEXT," + SYMPTOM_2 + " TEXT,"
                + SYMPTOM_3 + " TEXT," + SYMPTOM_4 + " TEXT," + SYMPTOM_5 + " TEXT," + SYMPTOM_6 + " TEXT," + SYMPTOM_7
                + " TEXT," + SYMPTOM_8 + " TEXT," + SYMPTOM_9 + " TEXT" + ")";

        String CREATE_PROFESSIONAL_TABLE = "CREATE TABLE " + TABLE_PRINFO1 + "("
                + KEY_IDPR + " INTEGER PRIMARY KEY," + KEY_PASSPR + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_PROFESSIONAL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            //db.execSQL("ALTER TABLE foo ADD COLUMN new_column INTEGER DEFAULT 0");

            // Drop older table if existed
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PINFO1);
            Log.d("deleting: ", "Deleting ..");

            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRINFO1);
            Log.d("deleting: ", "Deleting ..");

            // Creating tables again
            onCreate(db);
        }
    }

    // Adding new patient
    public void addPatient(Patient patient) {
        Log.d("Start Insert: ", "Inserting ..");

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, patient.getId()); // Patient Name
        values.put(KEY_AGE, patient.getAge()); // Patient Name
        values.put(KEY_GENDER, patient.getGender()); // Patient Name
        values.put(KEY_POSTAL_ADDR, patient.getAddress()); // Patient Postal Address
        values.put(SYMPTOM_1, patient.getSymptom1()); // Patient Symptom 1
        values.put(SYMPTOM_2, patient.getSymptom2()); // Patient Symptom 2
        values.put(SYMPTOM_3, patient.getSymptom3()); // Patient Symptom 3
        values.put(SYMPTOM_4, patient.getSymptom4()); // Patient Symptom 4
        values.put(SYMPTOM_5, patient.getSymptom5()); // Patient Symptom 5
        values.put(SYMPTOM_6, patient.getSymptom6()); // Patient Symptom 6
        values.put(SYMPTOM_7, patient.getSymptom7()); // Patient Symptom 7
        values.put(SYMPTOM_8, patient.getSymptom8()); // Patient Symptom 8
        values.put(SYMPTOM_9, patient.getSymptom9()); // Patient Symptom 9

        // Inserting Row
        db.insert(TABLE_PINFO1, null, values);
        db.close(); // Closing database connection
    }

    // Adding new professional
    public void addPatientByProfessional(Patient patient) {
        Log.d("Start Insert: ", "Inserting ..");

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_IDPR, patient.getIdprofessional()); // Patient Name
        values.put(KEY_PASSPR, patient.getPasswordprofessional()); // Patient Name


        // Inserting Row
        db.insert(TABLE_PRINFO1, null, values);
        db.close(); // Closing database connection
    }

    // Getting one patient
    public Patient getPatient(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PINFO1, new String[]{KEY_ID,
                        KEY_AGE, KEY_GENDER, KEY_POSTAL_ADDR, SYMPTOM_1, SYMPTOM_2, SYMPTOM_3, SYMPTOM_4, SYMPTOM_5, SYMPTOM_6, SYMPTOM_7, SYMPTOM_8, SYMPTOM_9}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Patient contact = new Patient(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)),
                cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12));
        // return patient
        return contact;
    }

    // Getting one professional
    public Patient getProfessional(int idpr, String passwordpr) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PRINFO1, new String[]{KEY_IDPR,
                        KEY_PASSPR}, KEY_IDPR + "=?",
                new String[]{String.valueOf(idpr)}, null, null, null, null);

        //Cursor cursor = db.rawQuery("SELECT * FROM professionalInformation WHERE idprofessional = '?' AND password = '?'", new String[] {String.valueOf(idpr), String.valueOf(passwordpr)});

        if( cursor != null && cursor.moveToFirst() ) {

            Patient contact = new Patient(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1));

            // return professional
            return contact;
        }
        else {
            return null;
        }
    }

    // Getting last patient
    public Patient getLastPatient() {

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_PINFO1;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToLast()) {
            Patient patient = new Patient();
            patient.setId(Integer.parseInt(cursor.getString(0)));
            patient.setAge(Integer.parseInt(cursor.getString(1)));
            patient.setGender(cursor.getString(2));
            patient.setAddress(cursor.getString(3));
            patient.setSymptom1(cursor.getString(4));
            patient.setSymptom2(cursor.getString(5));
            patient.setSymptom3(cursor.getString(6));
            patient.setSymptom4(cursor.getString(7));
            patient.setSymptom5(cursor.getString(8));
            patient.setSymptom6(cursor.getString(9));
            patient.setSymptom7(cursor.getString(10));
            patient.setSymptom8(cursor.getString(11));
            patient.setSymptom9(cursor.getString(12));

            String log = "Id: " + patient.getId();

            //Log.d("Patient ID: ", log);

            return patient;
        }
        else
            return null;
    }

    // Getting All Patients
    public List<Patient> getAllPatients() {
        List<Patient> patientList = new ArrayList<Patient>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PINFO1;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Patient patient = new Patient();
                patient.setId(Integer.parseInt(cursor.getString(0)));
                patient.setAge(Integer.parseInt(cursor.getString(1)));
                patient.setGender(cursor.getString(2));
                patient.setAddress(cursor.getString(3));
                patient.setSymptom1(cursor.getString(4));
                patient.setSymptom2(cursor.getString(5));
                patient.setSymptom3(cursor.getString(6));
                patient.setSymptom4(cursor.getString(7));
                patient.setSymptom5(cursor.getString(8));
                patient.setSymptom6(cursor.getString(9));
                patient.setSymptom7(cursor.getString(10));
                patient.setSymptom8(cursor.getString(11));
                patient.setSymptom9(cursor.getString(12));
                // Adding contact to list
                patientList.add(patient);
            } while (cursor.moveToNext());
        }

        // return contact list
        return patientList;
    }

    // Getting patient Count
    public int getPatientCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PINFO1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // Updating a patient
    public int updatePatient(Patient patient) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_AGE, patient.getAge());
        values.put(KEY_GENDER, patient.getGender());
        values.put(KEY_POSTAL_ADDR, patient.getAddress());
        values.put(SYMPTOM_1, patient.getSymptom1());
        values.put(SYMPTOM_2, patient.getSymptom2());
        values.put(SYMPTOM_3, patient.getSymptom3());
        values.put(SYMPTOM_4, patient.getSymptom4());
        values.put(SYMPTOM_5, patient.getSymptom5());
        values.put(SYMPTOM_6, patient.getSymptom6());
        values.put(SYMPTOM_7, patient.getSymptom7());
        values.put(SYMPTOM_8, patient.getSymptom8());
        values.put(SYMPTOM_9, patient.getSymptom9());

        // updating row
        return db.update(TABLE_PINFO1, values, KEY_ID + " = ?",
                new String[]{String.valueOf(patient.getId())});
    }

    // Updating a patient
    public int updateSymptom(Patient patient) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(KEY_AGE, patient.getAge());
        //values.put(KEY_GENDER, patient.getGender());
        //values.put(KEY_POSTAL_ADDR, patient.getAddress());
        values.put(SYMPTOM_1, patient.getSymptom1());
        values.put(SYMPTOM_2, patient.getSymptom2());
        values.put(SYMPTOM_3, patient.getSymptom3());
        values.put(SYMPTOM_4, patient.getSymptom4());
        values.put(SYMPTOM_5, patient.getSymptom5());
        values.put(SYMPTOM_6, patient.getSymptom6());
        values.put(SYMPTOM_7, patient.getSymptom7());
        values.put(SYMPTOM_8, patient.getSymptom8());
        values.put(SYMPTOM_9, patient.getSymptom9());

        // updating row
        return db.update(TABLE_PINFO1, values, KEY_ID + " = ?",
                new String[]{String.valueOf(patient.getId())});
    }


    // Deleting a patient
    public void deletePatient(Patient patient) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PINFO1, KEY_ID + " = ?",
                new String[] { String.valueOf(patient.getId()) });
        db.close();
    }

    // Deleting all patient
    public void deleteAllPatient() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_PINFO1);

    }

    public void deleteTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS TABLE_PINFO");
    }


}

