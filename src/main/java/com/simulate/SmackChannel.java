package com.simulate;


import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.chat2.IncomingChatMessageListener;
import org.jivesoftware.smack.chat2.OutgoingChatMessageListener;
import org.jivesoftware.smack.filter.StanzaFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.impl.JidCreate;

import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class SmackChannel {

    private static final ConcurrentHashMap<String, SmackChannel> channelList = new ConcurrentHashMap<String, SmackChannel>();
    private String domainName = "xcom0012";
    private String host = "127.0.0.1";
    private int port = 5222;
    private ChatManager chatManager;

    private String groupSubDomain = "conference.xcom0012";

    public  void connect(String userName, String passwd){
        try{
            //对连接的配置
            XMPPTCPConnectionConfiguration.Builder builder = XMPPTCPConnectionConfiguration.builder();
            builder.setUsernameAndPassword(userName, passwd);
            builder.setXmppDomain(domainName);
            builder.setHost(host);
            builder.setPort(port);
            //不加这行会报错，因为没有证书
            builder.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
            builder.enableDefaultDebugger();
//            builder.setDebuggerEnabled(true);
            XMPPTCPConnectionConfiguration config = builder.build();

            //建立连接并登陆
            AbstractXMPPConnection abstractXMPPConnection = new XMPPTCPConnection(config);

            abstractXMPPConnection.addStanzaSendingListener(new StanzaListener(){
                public void processStanza(Stanza stanza) throws SmackException.NotConnectedException, InterruptedException, SmackException.NotLoggedInException {
                    System.out.println("in StanzaListener:" + stanza.getFrom());
                }

            }, new StanzaFilter(){
                @Override
                public boolean accept(Stanza st) {
                    System.out.println("in StanzaFilter:" + st.getFrom());
                    return false;
                }
            });

            abstractXMPPConnection.connect();
            abstractXMPPConnection.login();
            this.chatManager =ChatManager.getInstanceFor(abstractXMPPConnection);
            this.chatManager.addIncomingListener(new IncomingChatMessageListener(){
                @Override
                public void newIncomingMessage(EntityBareJid entityBareJid, Message message, Chat chat) {
                    System.out.println("收到新的消息"+ message.getBody());
                }
            });
            this.chatManager.addOutgoingListener(new OutgoingChatMessageListener() {
                @Override
                public void newOutgoingMessage(EntityBareJid entityBareJid, Message message, Chat chat) {
                    System.out.println("收到新的消息1"+ message.getBody());
                }
            });
            channelList.put(userName,this);
            System.out.println("连接登录成功");

//            final Roster roster = Roster.getInstanceFor(abstractXMPPConnection);
//            Presence p = roster.getPresence("admin@127.0.0.1");
////            System.out.println(p.getType());
//            roster.addRosterListener(new RosterListener() {
//
//                @Override
//                public void entriesAdded(Collection<Jid> collection) {
//                    countPeople(roster);
//                }
//
//                @Override
//                public void entriesUpdated(Collection<Jid> collection) {
//                    countPeople(roster);
//                }
//
//                @Override
//                public void entriesDeleted(Collection<Jid> collection) {
//                    countPeople(roster);
//                }
//
//                public void presenceChanged(Presence presence) {countPeople(roster);}
//            });

//            //设置是否在线状态，和状态说明
//            Presence presence = new Presence(Presence.Type.unavailable);
//            presence.setStatus("Gone fishing");
//            c.sendStanza(presence);

            //会话管理者的建立和配置监听

        }catch(Exception e){
            System.out.println("连接登录错误"+ e.getMessage());
        }
    }

    private String createJid(String to){
        return to + "@"+ this.domainName;
    }

    public void sendGroupMessage(String to, String message){
        try {
            String roomId = to+ "@"+ this.groupSubDomain;
            Chat chat = this.chatManager.chatWith(JidCreate.entityBareFrom(roomId));
            Message msg = new Message();
            msg.setBody(message);
            chat.send(message);
        } catch (Exception e) {
            System.out.println("发送群消息错误" + e.getMessage());
        }
    }

    public void sendMessage(String to, String message){
        try {
            Chat chat = this.chatManager.chatWith(JidCreate.entityBareFrom(this.createJid(to)));
            Message msg = new Message();
            msg.setBody(message);
            chat.send(message);
        } catch (Exception e) {
            System.out.println("发送消息错误" + e.getMessage());
        }
    }

    public static void main(String[] args) {
//        SmackChannel smackChannel = new SmackChannel();
//        smackChannel.connect("test001", "123456");
//        while (true);
        while (true){
            System.out.println("请输入：");
            Scanner sc = new Scanner(System.in);
            String str = sc.nextLine();
            if(str.equals("exit")) {
                System.out.println("Bye!");
                return;
            }
            System.out.println(str);
        }



    }


}