package com.kosam.carpool.activities.classGroup;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UnitListItem {
    private String UnitName;
    private int ManagerId;

    public void setUnitName(String s){
        this.UnitName=s;
    }
    public  void setManagerId(int m){
        this.ManagerId=m;
    }

    public String getUnitName(){
        return this.UnitName;
    }
    public int getManagerId(){
        return this.ManagerId;
    }
    public  UnitListItem(){
        this.UnitName=null;
        this.ManagerId=0;
    }
    public  UnitListItem(String name,int mid){
        this.UnitName=name;
        this.ManagerId=mid;
    }
}
