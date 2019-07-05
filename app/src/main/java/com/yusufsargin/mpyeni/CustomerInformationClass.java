package com.yusufsargin.mpyeni;

import java.util.ArrayList;

public class CustomerInformationClass {

    ArrayList<String >assignmanufNameArray;
    ArrayList<String>assingManufTelArray;
    ArrayList<String>assingManufEmailArray;
    String name;
    String email;
    String telno;
    String type;
    String  contact;
    String customerid;
    Integer choose;


    public CustomerInformationClass(ArrayList<String> assignmanufNameArray, ArrayList<String> assingManufTelArray, ArrayList<String> assingManufEmailArray, String name, String email, String telno, String type, String contact, String customerid) {
        this.assignmanufNameArray = assignmanufNameArray;
        this.assingManufTelArray = assingManufTelArray;
        this.assingManufEmailArray = assingManufEmailArray;
        this.name = name;
        this.email = email;
        this.telno = telno;
        this.type = type;
        this.contact = contact;
        this.customerid = customerid;
    }

    public ArrayList<String> getAssignmanufNameArray() {
        return assignmanufNameArray;
    }

    public void setAssignmanufNameArray(ArrayList<String> assignmanufNameArray) {
        this.assignmanufNameArray = assignmanufNameArray;
    }

    public ArrayList<String> getAssingManufTelArray() {
        return assingManufTelArray;
    }

    public void setAssingManufTelArray(ArrayList<String> assingManufTelArray) {
        this.assingManufTelArray = assingManufTelArray;
    }

    public ArrayList<String> getAssingManufEmailArray() {
        return assingManufEmailArray;
    }

    public void setAssingManufEmailArray(ArrayList<String> assingManufEmailArray) {
        this.assingManufEmailArray = assingManufEmailArray;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelno() {
        return telno;
    }

    public void setTelno(String telno) {
        this.telno = telno;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public Integer getChoose() {
        return choose;
    }

    public void setChoose(Integer choose) {
        this.choose = choose;
    }
}
