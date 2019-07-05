package com.yusufsargin.mpyeni;

import android.content.Context;

import java.util.ArrayList;

public class Manuf {
    String name;
    String latitute;
    String longitute;
    String ımage;
    String telno;
    Float locDifferant;
    String manufTableName;
    String email,MpPara,bolge,mpCode,reklamhakki,userid,website;

    ArrayList<String>manufEmail;
    ArrayList<String>manufTel;
    ArrayList<String>manufName;
    String id;

public Manuf(String name, String latitute, String longitute,String ımage,String telno,String email,String manufTableName) {
        this.name = name;
        this.manufTableName=manufTableName;
        this.latitute = latitute;
        this.longitute = longitute;
        this.ımage=ımage;
        this.telno=telno;
        this.email=email;

    }

    public Manuf(ArrayList<String>manufName,ArrayList<String>manufEmail,ArrayList<String >manufTel,String id){
    this.manufName=manufName;
    this.manufTel=manufTel;
    this.manufEmail=manufEmail;
    this.id=id;
    }

    public Manuf(String MpPara, String bolge, String email, String latitute, String longitute, String mpCode, String name, String reklamhakki, String telno, String userid, String website, String ımage) {
        this.name = name;
        this.latitute = latitute;
        this.longitute = longitute;
        this.ımage = ımage;
        this.telno = telno;
        this.email = email;
        this.MpPara = MpPara;
        this.bolge = bolge;
        this.mpCode = mpCode;
        this.reklamhakki = reklamhakki;
        this.userid = userid;
        this.website = website;
    }
    public Manuf(){

    }


    public String getTelno() {
        return telno;
    }

    public void setTelno(String telno) {
        this.telno = telno;
    }

    public String getMpPara() {
        return MpPara;
    }

    public void setMpPara(String mpPara) {
        MpPara = mpPara;
    }

    public String getBolge() {
        return bolge;
    }

    public void setBolge(String bolge) {
        this.bolge = bolge;
    }

    public String getMpCode() {
        return mpCode;
    }

    public void setMpCode(String MpCode) {
        this.mpCode = MpCode;
    }

    public String getReklamhakki() {
        return reklamhakki;
    }

    public void setReklamhakki(String reklamhakki) {
        this.reklamhakki = reklamhakki;
    }

    public String getUser() {
        return userid;
    }

    public void setUser(String userid) {
        this.userid = userid;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return telno;
    }

    public void setTel(String tel) {
        this.telno = tel;
    }

    public String getImage() {
        return ımage;
    }

    public void setImage(String ımage) {
        this.ımage = ımage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitute() {
        return latitute;
    }

    public void setLatitute(String latitute) {
        this.latitute = latitute;
    }

    public String getLongitute() {
        return longitute;
    }

    public void setLongitute(String longitute) {
        this.longitute = longitute;
    }

    public Float getLocDifferant() {
        return locDifferant;
    }

    public void setLocDifferant(Float locDifferant) {
        this.locDifferant = locDifferant;
    }
}
