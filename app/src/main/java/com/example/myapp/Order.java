package com.example.myapp;

public class Order {
    public String orderID;
    public String service;
    public String customerID;
    public String providerID;
    public String customerno;
    public String providerno;
    public int quantity;
    public int price;
    public int total;
    public String address;
    public double lat;
    public double lng;
    public boolean check;

    public Order(){}

    public Order(String id){
        orderID=id;
    }

    public String getOrderID(){return orderID;}
    public void setOrderID(String id){orderID=id;}

    public boolean Iscompleted(){return check;}
    public void setcompleted(boolean c){check=c;}

    public String getService(){return service;}
    public void setService(String s){service=s;}

    public int getPrice(){return price;}
    public void setPrice(int price){this.price=price;}

    public int getQuantity(){return quantity;}
    public void setQuantity(int Quantity){this.quantity=Quantity;}

    public double getLatitude(){ return lat;}
    public void setlatitude(double lat){this.lat=lat;}

    public double getLongitude(){ return lng;}
    public void setlongitude(double lng){this.lng=lng;}

    public String getAddress(){return address;}
    public void setAddress(String address){this.address=address;}

    public void setTotal() { total=quantity*price;}
    public void setTotal(int quantity,int price){total=quantity*price;}
    public int getTotal(){return total;}

    public String getCustomerID(){ return customerID;}
    public void setCustomerID(String id){customerID=id;}

    public String getProviderID(){ return providerID;}
    public void setProviderID(String id){providerID=id;}

    public String getCustomerno(){ return customerno;}
    public void setCustomerno(String id){customerno=id;}

    public String getProviderno(){ return providerno;}
    public void setProviderno(String id){providerno=id;}
}
