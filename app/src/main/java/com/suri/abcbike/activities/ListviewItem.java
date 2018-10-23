package com.suri.abcbike.activities;

public class ListviewItem {
    private int startHour;
    private int startMinute;
    private String start;
    private String end;
    private String poster;
    private int nowPerson;
    private int maxPerson;
    public void setStartHour(int h){
        this.startHour=h;
    }
    public void setStartMinute(int m){
        this.startMinute=m;
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
    public  int getStartHour(){return this.startHour;}
    public  int getStartMinute(){return this.startMinute;}
    public String getStartTime(){return String.format("%d시 %d분",this.startHour,this.startMinute);}
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
    public String getMax(){return String.format("%d/%d",this.nowPerson,this.maxPerson);}
    public  ListviewItem(){
        this.startHour=0;
        this.startMinute=0;
        this.start=null;
        this.end=null;
        this.poster=null;
        this.nowPerson=0;
        this.maxPerson=0;
    }
    public  ListviewItem(int h,int mi,String s,String e,String p,int n,int m){
        this.startHour=h;
        this.startMinute=mi;
        this.start=s;
        this.end=e;
        this.poster=p;
        this.nowPerson=n;
        this.maxPerson=m;
    }
}
