package com.example.zightoo;

public class listitem {
    String date;
    Double confidence;
    String image;
    String device_ID;
    String is_public;



    public listitem(String date, Double confidence, String image, String device_ID, String is_public) {

        this.date = date;
        this.confidence = confidence;
        this.image = image;
        this.device_ID = device_ID;
        this.is_public = is_public;
    }

    public String getDevice_ID() {
        return device_ID;
    }

    public String getIs_public() {
        return is_public;
    }



    public String getimage() {
        return image;
    }

    public listitem(){

    }


    public String getdate() {
        return date;
    }

    public Double getConfidence() {
        return confidence;
    }
}
