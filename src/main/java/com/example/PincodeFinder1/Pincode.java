package com.example.PincodeFinder1;

public class Pincode {
    private String Area;
    private String lat;
    private String lng;
    private String District;
    private String State;
    private String Pincode;

    // Getters and setters
    public String getArea() { return Area; }
    public void setArea(String area) { Area = area; }

    public String getLat() { return lat; }
    public void setLat(String lat) { this.lat = lat; }

    public String getLng() { return lng; }
    public void setLng(String lng) { this.lng = lng; }

    public String getDistrict() { return District; }
    public void setDistrict(String district) { District = district; }

    public String getState() { return State; }
    public void setState(String state) { State = state; }

    public String getPincode() { return Pincode; }
    public void setPincode(String pincode) { Pincode = pincode; }
}
