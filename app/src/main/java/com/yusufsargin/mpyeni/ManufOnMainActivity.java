package com.yusufsargin.mpyeni;

public class ManufOnMainActivity {
    String name,ımage,reklamhakki;

    public ManufOnMainActivity(String name, String image,String reklamhakki) {
        this.name = name;
        this.ımage = image;
        this.reklamhakki=reklamhakki;
    }

    public String getControl() {
        return reklamhakki;
    }

    public void setControl(String control) {
        this.reklamhakki = control;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return ımage;
    }

    public void setImage(String image) {
        this.ımage = image;
    }
}
