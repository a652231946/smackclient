package com.simulate;


import com.simulate.utils.MessageUtil;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.chat2.IncomingChatMessageListener;
import org.jivesoftware.smack.chat2.OutgoingChatMessageListener;
import org.jivesoftware.smack.filter.StanzaFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.muc.MucEnterConfiguration;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.jid.parts.Resourcepart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

public class SmackChannel {

    private static final Logger log = LoggerFactory.getLogger(SmackChannel.class);
    private static final ConcurrentHashMap<String, SmackChannel> channelList = new ConcurrentHashMap<String, SmackChannel>();

    private ChatManager chatManager;
    private ChannelConfig channelConfig;
    private String currentUser;
    private MultiUserChatManager multiUserChatManager;
    // 用户连接
    private AbstractXMPPConnection abstractXMPPConnection;

    private ConcurrentHashMap<String, MultiUserChat> multiUserChatConcurrentHashMap = new ConcurrentHashMap<String, MultiUserChat>();

    public SmackChannel(ChannelConfig channelConfig) {
        this.channelConfig = channelConfig;
        this.connect();
    }

    public void login(String userName, String passwd) {

        try {
            currentUser = this.createJid(userName);
            abstractXMPPConnection.login(userName, passwd);
            channelList.put(userName,this);
            log.info("用户={},连接登录成功!", userName);
        } catch (Exception e) {
            log.error("连接发生错误:", e.getMessage());
        }
    }

    private void connect(){
        try{
            //对连接的配置
            XMPPTCPConnectionConfiguration.Builder builder = XMPPTCPConnectionConfiguration.builder();
            builder.setXmppDomain(channelConfig.getDomainName());
            builder.setHost(channelConfig.getHost());
            builder.setPort(channelConfig.getPort());
            //不加这行会报错，因为没有证书
            builder.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
            builder.enableDefaultDebugger();
//            builder.setDebuggerEnabled(true);
            XMPPTCPConnectionConfiguration config = builder.build();

            //建立连接并登陆
            abstractXMPPConnection = new XMPPTCPConnection(config);
            abstractXMPPConnection.addStanzaSendingListener(new StanzaListener(){
                public void processStanza(Stanza stanza) throws SmackException.NotConnectedException, InterruptedException, SmackException.NotLoggedInException {
                    log.info("in StanzaListener:{}", stanza.getFrom());
                }
            }, new StanzaFilter(){
                @Override
                public boolean accept(Stanza st) {
                    log.info("in StanzaFilter:{}", st.getFrom());
                    return false;
                }
            });
            abstractXMPPConnection.connect();
            multiUserChatManager = MultiUserChatManager.getInstanceFor(abstractXMPPConnection);
            MultiUserChat multiUserChat = multiUserChatManager.getMultiUserChat(JidCreate.entityBareFrom(channelConfig.getGroupID()+"@"+channelConfig.getGroupSubDomain()));
            multiUserChat.addMessageListener(new MessageListener() {
                @Override
                public void processMessage(Message message) {
                    System.out.println("收到群消息"+ message.getBody());
                }
            });
            multiUserChatConcurrentHashMap.put(channelConfig.getGroupID(), multiUserChat);


            this.chatManager =ChatManager.getInstanceFor(abstractXMPPConnection);
            this.chatManager.addIncomingListener(new IncomingChatMessageListener(){
                @Override
                public void newIncomingMessage(EntityBareJid entityBareJid, Message message, Chat chat) {
                    log.info("收到新的消息: {}", message.getBody());
                }
            });
            this.chatManager.addOutgoingListener(new OutgoingChatMessageListener() {
                @Override
                public void newOutgoingMessage(EntityBareJid entityBareJid, Message message, Chat chat) {
                    log.info("发送新的消息: {}", message.getBody());
                }
            });

        }catch(Exception e){
            log.error("连接错误:", e);
        }
    }

    private String createJid(String to){
        return to + "@"+ channelConfig.getDomainName();
    }

    public void sendGroupMessage(String to, String message){
        try {
            String roomId = to+ "@"+ channelConfig.getGroupSubDomain();
            Chat chat = this.chatManager.chatWith(JidCreate.entityBareFrom(this.createJid(to)));
            Message msg =  MessageUtil.generateGroupMessage(this.currentUser,roomId,message);
//            Message msg =  MessageUtil.generateGroupOldMessage(this.currentUser,roomId,message);
            chat.send(msg);
        } catch (Exception e) {
            System.out.println("发送群消息错误" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void sendGroup2Message(String to,String message) {
        try {
            log.info("开始发送群消息:{}", message);
            String roomId = to+ "@"+ channelConfig.getGroupSubDomain();
            MultiUserChat multiUserChat =multiUserChatConcurrentHashMap.get(to);
//            Message msg =  MessageUtil.generateGroupMessage(this.currentUser,roomId,message);
            Message msg =  MessageUtil.generateGroupOldMessage(this.currentUser,roomId,message);
            multiUserChat.sendMessage(msg);
        } catch (Exception e) {
            System.out.println("发送群消息错误" + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean sendGroupOK(String to,String message) {
        try {
            log.info("开始发送群消息:{}", message);
            String roomId = to+ "@"+ channelConfig.getGroupSubDomain();
            MultiUserChat multiUserChat =multiUserChatConcurrentHashMap.get(to);
//            Message msg =  MessageUtil.generateGroupMessage(this.currentUser,roomId,message);
            Message msg =  MessageUtil.generateGroupOldMessage(this.currentUser,roomId,message);
            multiUserChat.sendMessage(msg);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public void sendMessage(String to, String message){
        try {
            Chat chat = this.chatManager.chatWith(JidCreate.entityBareFrom(this.createJid(to)));
            Message msg = MessageUtil.generateSingleMessage(this.currentUser, this.createJid(to),message);
            chat.send(msg);
        } catch (Exception e) {
            System.out.println("发送消息错误" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 申请加入群
     * @param groupName
     * @param nickName
     * @return
     * @throws Exception
     */
    public MultiUserChat joinMultiUserChat(String groupName, String nickName) throws Exception {
        //群jid
        String jid = groupName + "@"+ channelConfig.getGroupSubDomain();
        //jid实体创建
        EntityBareJid groupJid = JidCreate.entityBareFrom(jid);

        //获取群管理对象
        MultiUserChatManager multiUserChatManager = MultiUserChatManager.getInstanceFor(abstractXMPPConnection);
        //通过群管理对象获取该群房间对象
        MultiUserChat multiUserChat = multiUserChatManager.getMultiUserChat(groupJid);

        MucEnterConfiguration.Builder builder = multiUserChat.getEnterConfigurationBuilder(Resourcepart.from(nickName));
        //只获取最后99条历史记录
        builder.requestMaxCharsHistory(99);
        MucEnterConfiguration mucEnterConfiguration = builder.build();
        //加入群
        multiUserChat.join(mucEnterConfiguration);
        return multiUserChat;
    }


//    public static void main(String[] args) {
//        SmackChannel smackChannel = new SmackChannel();
//        smackChannel.connect("cod_60002858", "a123456");
//
//        SmackChannel test002 = new SmackChannel();
////        test002.connect("cod_60002849", "a123456");
////        while (true);
//        while (true){
//            System.out.println("请输入：");
//            Scanner sc = new Scanner(System.in);
//            String str = sc.nextLine();
//            if(str.equals("exit")) {
//                System.out.println("Bye!");
//                return;
//            }
////            smackChannel.sendMessage("group001",str);
////            smackChannel.sendMessage("test002",str);
////            smackChannel.sendGroupMessage("group001", str);
////            codm_1080031967@conference.cod.xinhoo.com
//            smackChannel.sendGroup2Message("codm_1080031967", str);
//        }
//
//
//
//    }


}