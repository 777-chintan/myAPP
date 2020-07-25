package com.example.myapp;

public class AddressSetup {
    private String Address;
    private double lat;
    private double Lng;

    public AddressSetup(){}

    public AddressSetup(String Address,double lat,double Lng){
        this.Address=Address;
        this.lat=lat;
        this.Lng=Lng;
    }

    public String getAddress(){return Address;}
    public void setAddress(String Address){this.Address=Address;}

    public double getlat(){return lat;}
    public void setlat(float lat){this.lat=lat;}

    public double getLng(){return Lng;}
    public void setLng(float Lng){this.Lng=Lng;}
}
