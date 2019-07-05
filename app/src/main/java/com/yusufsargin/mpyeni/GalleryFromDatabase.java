package com.yusufsargin.mpyeni;

public class GalleryFromDatabase {
    String baslikfoto,weburl,baslik,satinalurl;

    public GalleryFromDatabase(String baslik,String baslikfoto,String weburl,String satinalurl){
        this.baslik=baslik;
        this.baslikfoto=baslikfoto;
        this.weburl=weburl;
        this.satinalurl=satinalurl;
    }

    public String getBaslikfoto() {
        return baslikfoto;
    }

    public void setBaslikfoto(String baslikfoto) {
        this.baslikfoto = baslikfoto;
    }

    public String getWeburl() {
        return weburl;
    }

    public void setWeburl(String weburl) {
        this.weburl = weburl;
    }

    public String getBaslik() {
        return baslik;
    }

    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }

    public String getSatinalurl() {
        return satinalurl;
    }

    public void setSatinalurl(String satinalurl) {
        this.satinalurl = satinalurl;
    }
}
