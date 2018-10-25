package com.kosam.carpool.activities.classGroup;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UnitListItem {
    private String TopUnit;
    private String UnitName;
    private  int UnitId;
    private int ManagerId;

    public void setTopUnit(String s){
        this.TopUnit=s;
    }
    public void setUnitName(String s){
        this.UnitName=s;
    }
    public void setUnitId(int pid){this.UnitId=pid;}
    public  void setManagerId(int m){
        this.ManagerId=m;
    }

    public String getTopUnit(){
        return this.TopUnit;
    }
    public String getUnitName(){
        return this.UnitName;
    }
    public int getUnitId(){return this.UnitId;}
    public int getManagerId(){
        return this.ManagerId;
    }
    public  UnitListItem(){
        this.TopUnit=null;
        this.UnitName=null;
        this.UnitId=0;
        this.ManagerId=0;
    }
    private  UnitListItem(String top,String name,int uid,int mid){
        this.TopUnit=top;
        this.UnitName=name;
        this.UnitId=uid;
        this.ManagerId=mid;
    }
}
