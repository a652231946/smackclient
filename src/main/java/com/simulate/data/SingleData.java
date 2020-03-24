package com.simulate.data;

import java.io.Serializable;
import java.util.List;

public class SingleData implements Serializable {

    private String body;

    private int burn;

    private int chatType;

    private int edited;

    private String fw;

    private String fwn;

    private int l;

    private int msgType;

    private String r;

    private int received;

    private String receiver;

    private List<String> referTo;

    private int roomID;

    private String rp;

    private int sendTime;

    private String sender;

    public void setBody(String body){
        this.body = body;
    }
    public String getBody(){
        return this.body;
    }
    public void setBurn(int burn){
        this.burn = burn;
    }
    public int getBurn(){
        return this.burn;
    }
    public void setChatType(int chatType){
        this.chatType = chatType;
    }
    public int getChatType(){
        return this.chatType;
    }
    public void setEdited(int edited){
        this.edited = edited;
    }
    public int getEdited(){
        return this.edited;
    }
    public void setFw(String fw){
        this.fw = fw;
    }
    public String getFw(){
        return this.fw;
    }
    public void setFwn(String fwn){
        this.fwn = fwn;
    }
    public String getFwn(){
        return this.fwn;
    }
    public void setL(int l){
        this.l = l;
    }
    public int getL(){
        return this.l;
    }
    public void setMsgType(int msgType){
        this.msgType = msgType;
    }
    public int getMsgType(){
        return this.msgType;
    }
    public void setR(String r){
        this.r = r;
    }
    public String getR(){
        return this.r;
    }
    public void setReceived(int received){
        this.received = received;
    }
    public int getReceived(){
        return this.received;
    }
    public void setReceiver(String receiver){
        this.receiver = receiver;
    }
    public String getReceiver(){
        return this.receiver;
    }
    public void setReferTo(List<String> referTo){
        this.referTo = referTo;
    }
    public List<String> getReferTo(){
        return this.referTo;
    }
    public void setRoomID(int roomID){
        this.roomID = roomID;
    }
    public int getRoomID(){
        return this.roomID;
    }
    public void setRp(String rp){
        this.rp = rp;
    }
    public String getRp(){
        return this.rp;
    }
    public void setSendTime(int sendTime){
        this.sendTime = sendTime;
    }
    public int getSendTime(){
        return this.sendTime;
    }
    public void setSender(String sender){
        this.sender = sender;
    }
    public String getSender(){
        return this.sender;
    }
}
