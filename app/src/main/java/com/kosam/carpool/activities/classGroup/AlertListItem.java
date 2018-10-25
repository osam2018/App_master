package com.kosam.carpool.activities.classGroup;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AlertListItem {
    private int AlertId;
    private int Type;
    private int SenderId;
    private String SenderName;
    private int TakerId;

    public void setAlertId(int a){
        this.AlertId=a;
    }
    public void setType(int t){
        this.Type=t;
    }
    public void setSenderId(int s){this.SenderId=s;}
    public void setSenderName(String s){this.SenderName=s;}
    public  void setTakerId(int t){
        this.TakerId=t;
    }

    public int getAlertId(){
        return this.AlertId;
    }
    public int getType(){
        return this.Type;
    }
    public int getSenderId(){return this.SenderId;}
    public String getSenderName(){return this.SenderName;}
    public int getTakerId(){
        return this.TakerId;
    }
    public  AlertListItem(){
        this.AlertId=0;
        this.Type=0;
        this.SenderId=0;
        this.SenderName=null;
        this.TakerId=0;
    }
    private  AlertListItem(int aid,int type,int sid,String sn,int tid){
        this.AlertId=aid;
        this.Type=type;
        this.SenderId=sid;
        this.SenderName=sn;
        this.TakerId=tid;
    }
}
