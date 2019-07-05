package com.yusufsargin.mpyeni;

public class yeniurunler {
    String title;
    String ımage;
    String webUrl;


    public yeniurunler(String title, String ımage,String webUrl) {
        this.title = title;
        this.ımage = ımage;
        this.webUrl=webUrl;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getImage() {
        return ımage;
    }

    public void setImage(String ımage) {
        this.ımage = ımage;
    }
}
