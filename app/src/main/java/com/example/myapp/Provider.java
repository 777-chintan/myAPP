package com.example.myapp;

import java.io.Serializable;
import java.util.Comparator;

public class Provider implements Serializable {
    public String age;
    public String phoneNumber;
    public String name;
    public String id;
    public Float rating;
    public int numberofraters;
    public int price;
    public double lat;
    public double lng;
    public String address;
    public boolean status;
    public double distance;

    public Provider() {
    }
    public Provider(String age, String phoneNumber, String name, String id, Float rating) {
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.id = id;
        this.rating = rating;
    }
    public Provider(String age, String phoneNumber, String name, String id) {
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.id = id;
    }
    public String getAge() {
        return age;
    }
    public void setAge(String age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public Float getRating() {
        return rating;
    }
    public void setRating(Float rating) {
        this.rating = rating;
    }

    public double getLatitude(){ return lat;}
    public void setlatitude(double lat){this.lat=lat;}

    public double getLongitude(){ return lng;}
    public void setlongitude(double lng){this.lng=lng;}

    public int getNumberofraters(){return numberofraters;}
    public void setNumberofraters(int numberofraters){this.numberofraters=numberofraters;}

    public int getPrice(){return price;}
    public void setPrice(int price){this.price=price;}

    public String getAddress(){return address;}
    public void setAddress(String address){this.address=address;}

    public boolean getStatus(){return status;}
    public void setStatus(boolean status){this.status=status;}

    public double getDistance(){return distance;}
    public void setDistance(double distance){this.distance=distance;}

    public static Comparator<Provider> RatingComparator = new Comparator<Provider>(){

        public int compare(Provider p1,Provider p2){
            Float f1=p1.rating;
            Float f2=p2.rating;
            return f2.compareTo(f1);
        }
    };

    public static Comparator<Provider> PriceComparator = new Comparator<Provider>(){

        public int compare(Provider p1,Provider p2){
            int f1=p1.price;
            int f2=p2.price;
            if(f2<=f1)
                return 0;
            else
                return 1;
        }
    };

}
