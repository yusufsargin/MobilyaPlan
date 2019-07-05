package com.yusufsargin.mpyeni;

public class CustomerFromDatabase {

    String CustomerName,assignManufEmail,assignManufTel,contact,customerid,phonenumber,type,useremail;
    String assignManufName;


    public CustomerFromDatabase(String customerName, String assignManufEmail, String assignManufTel, String contact, String customerid, String phonenumber, String type, String useremail,String assignManufName) {
        this.CustomerName = customerName;
        this.assignManufEmail = assignManufEmail;
        this.assignManufTel = assignManufTel;
        this.contact = contact;
        this.customerid = customerid;
        this.phonenumber = phonenumber;
        this.type = type;
        this.useremail = useremail;
        this.assignManufName=assignManufName;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getAssignManufEmail() {
        return assignManufEmail;
    }

    public void setAssignManufEmail(String assignManufEmail) {
        this.assignManufEmail = assignManufEmail;
    }

    public String getAssignManufTel() {
        return assignManufTel;
    }

    public void setAssignManufTel(String assignManufTel) {
        this.assignManufTel = assignManufTel;
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

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }
}
