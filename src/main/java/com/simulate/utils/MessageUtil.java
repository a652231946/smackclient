package com.simulate.utils;
import com.alibaba.fastjson.JSON;
import com.simulate.data.GroupData;
import com.simulate.data.SingleData;
import org.jivesoftware.smack.packet.Message;
import org.jxmpp.jid.impl.JidCreate;

import java.util.ArrayList;

public class MessageUtil {

    public static Message generateSingleMessage(String from, String to, String msgText) throws Exception{
        Message message = new Message();
        SingleData singleData = new SingleData();
        singleData.setBurn(0);
        singleData.setChatType(1);
        singleData.setEdited(0);
        singleData.setFw("");
        singleData.setFwn("");
        singleData.setL(0);
        singleData.setMsgType(1);
        singleData.setR("");
        singleData.setReceived(0);
        singleData.setReceiver(to);
        singleData.setReferTo(new ArrayList<String>());
        singleData.setRoomID(0);
        singleData.setRp("");
        singleData.setSendTime((int) System.currentTimeMillis());
        singleData.setSender(from);
        singleData.setBody(msgText);
        message.setTo(JidCreate.entityBareFrom(to));
        message.setBody(JSON.toJSONString(singleData));
        return message;
    }

    public static Message generateGroupMessage(String from, String to, String msgText) throws Exception{
        Message message = new Message();
        SingleData singleData = new SingleData();
        singleData.setBurn(0);
        singleData.setChatType(2);
        singleData.setEdited(0);
        singleData.setFw("");
        singleData.setFwn("");
        singleData.setL(0);
        singleData.setMsgType(1);
        singleData.setR("");
        singleData.setReceived(0);
        singleData.setReceiver(to);
        singleData.setReferTo(new ArrayList<String>());
        singleData.setRoomID(1000032065);
        singleData.setRp("");
        singleData.setSendTime((int) System.currentTimeMillis());
        singleData.setSender(from);
        singleData.setBody(msgText);
        message.setTo(JidCreate.entityBareFrom(to));
        message.setFrom(JidCreate.entityBareFrom(from));
        message.setType(Message.Type.chat);
        message.setBody(JSON.toJSONString(singleData));
        return message;
    }

    public static Message generateGroupOldMessage(String from, String to, String msgText) throws Exception{
        Message message = new Message();
        GroupData groupData = new GroupData();
        groupData.setBody(msgText);
        groupData.setBurn(0);
        groupData.setChatType(2);
        groupData.setEdited(0);
        groupData.setFw("");
        groupData.setFwn("");
        groupData.setL(0);
        groupData.setMsgType(1);
        groupData.setReceived(0);
        groupData.setReceiver(to);
        groupData.setReferTo(new ArrayList<String>());
        groupData.setRoomID(1000032065);
        groupData.setRp("");
        groupData.setSendTime((int) System.currentTimeMillis());
        groupData.setSender(from);
        message.setType(Message.Type.normal);
        message.setFrom(JidCreate.entityBareFrom(from));
        message.setTo(JidCreate.entityBareFrom(to));
        message.setBody(JSON.toJSONString(groupData));
        return message;
    }

}
