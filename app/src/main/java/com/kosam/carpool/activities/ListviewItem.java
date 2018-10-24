package com.kosam.carpool.activities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ListviewItem {
    private Date startTime;
    private String start;
    private String end;
    private String poster;
    private int nowPerson;
    private int maxPerson;
    public void setStartTime(Date t){
        this.startTime=t;
    }
    public void setEnd(String s){
        this.end=s;
    }
    public void setPoster(String s){
        this.poster=s;
    }
    public  void setMaxPerson(int m){
        this.maxPerson=m;
    }
    public  void setNowPerson(int n){
        this.nowPerson=n;
    }
    public  Date getStartTimes(){return this.startTime;}
    public String getStartTime(){return (new SimpleDateFormat("MM월 dd일 hh시 mm분")).format(this.startTime);}
    public String getStart(){
        return this.start;
    }
    public String getEnd(){
        return this.end;
    }
    public String getPoster(){
        return this.poster;
    }
    public int getNowPerson(){
        return this.nowPerson;
    }
    public int getMaxPerson(){
        return this.maxPerson;
    }
    public String getMax(){return String.format("%d/%d명",this.nowPerson,this.maxPerson);}
    public  ListviewItem(){
        this.startTime=null;
        this.start=null;
        this.end=null;
        this.poster=null;
        this.nowPerson=0;
        this.maxPerson=0;
    }
    public  ListviewItem(Date d,String s,String e,String p,int n,int m){
        this.startTime=d;
        this.start=s;
        this.end=e;
        this.poster=p;
        this.nowPerson=n;
        this.maxPerson=m;
    }
}
