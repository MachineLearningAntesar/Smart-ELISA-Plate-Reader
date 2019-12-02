package com.example.as9221.kmeancluster;

/**
 * Created by Bilal-PC on 010.10 Sep,15.
 */
public class Patient {

    private int id;
    private int age;
    private String gender;
    private String address;
    private String symptom1;
    private String symptom2;
    private String symptom3;
    private String symptom4;
    private String symptom5;
    private String symptom6;
    private String symptom7;
    private String symptom8;
    private String symptom9;

    private int idprofessional;
    private String passwordprofessional;


    public Patient()
    {
    }

    public Patient(int id, int age, String gender, String address, String symptom1, String symptom2, String symptom3, String symptom4, String symptom5, String symptom6, String symptom7, String symptom8, String symptom9)
    {
        this.id=id;
        this.age=age;
        this.gender=gender;
        this.address=address;
        this.symptom1=symptom1;
        this.symptom2=symptom2;
        this.symptom3=symptom3;
        this.symptom4=symptom4;
        this.symptom5=symptom5;
        this.symptom6=symptom6;
        this.symptom7=symptom7;
        this.symptom8=symptom8;
        this.symptom9=symptom9;
    }

    public Patient(int idprofessional, String passwordprofessional)
    {
        this.idprofessional=idprofessional;
        this.passwordprofessional=passwordprofessional;

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSymptom1(String symptom1) {
        this.symptom1 = symptom1;
    }

    public void setSymptom2(String symptom2) {
        this.symptom2 = symptom2;
    }

    public void setSymptom3(String symptom3) {
        this.symptom3 = symptom3;
    }

    public void setSymptom4(String symptom4) {
        this.symptom4 = symptom4;
    }

    public void setSymptom5(String symptom5) {
        this.symptom5 = symptom5;
    }

    public void setSymptom6(String symptom6) {
        this.symptom6 = symptom6;
    }

    public void setSymptom7(String symptom7) {
        this.symptom7 = symptom7;
    }

    public void setSymptom8(String symptom8) {
        this.symptom8 = symptom8;
    }

    public void setSymptom9(String symptom9) {
        this.symptom9 = symptom9;
    }

    public void setIdprofessional(int idprofessional) {
        this.idprofessional = idprofessional;
    }

    public void setPasswordprofessional(String passwordprofessional) {
        this.passwordprofessional = passwordprofessional;
    }


    public int getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public String getSymptom1() {
        return symptom1;
    }

    public String getSymptom2() {
        return symptom2;
    }

    public String getSymptom3() {
        return symptom3;
    }

    public String getSymptom4() {
        return symptom4;
    }

    public String getSymptom5() {
        return symptom5;
    }

    public String getSymptom6() {
        return symptom6;
    }

    public String getSymptom7() {
        return symptom7;
    }

    public String getSymptom8() {
        return symptom8;
    }

    public String getSymptom9() {
        return symptom9;
    }



    public int getIdprofessional() {
        return idprofessional;
    }


    public String getPasswordprofessional() {
        return passwordprofessional;
    }


}
