package com.kosam.carpool.activities.classGroup;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UnitInfoListItem {
    private String UserName;
    private String Phone;
    private int UserId;
    private boolean HaveCar;

    public void setUserName(String un){
        this.UserName=un;
    }
    public void setPhone(String p){
        this.Phone=p;
    }
    public void setUserId(int uid){this.UserId=uid;}
    public  void setHaveCar(boolean hc){
        this.HaveCar=hc;
    }

    public String getUserName(){
        return this.UserName;
    }
    public String getPhone(){
        return this.Phone;
    }
    public int getUserId(){return this.UserId;}
    public boolean getHaveCar(){
        return this.HaveCar;
    }

    public  UnitInfoListItem(){
        this.UserName=null;
        this.Phone=null;
        this.UserId=0;
        this.HaveCar=false;
    }
    private  UnitInfoListItem(String username,String phone,int uid,boolean havecar){
        this.UserName=username;
        this.Phone=phone;
        this.UserId=uid;
        this.HaveCar=havecar;
    }
}
