package com.simulate;


import com.simulate.data.XHSession;
import com.simulate.utils.MessageUtil;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.chat2.IncomingChatMessageListener;
import org.jivesoftware.smack.chat2.OutgoingChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.sasl.SASLErrorException;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionImpl;
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
    protected static final ConcurrentHashMap<String, SmackChannel> channelList = new ConcurrentHashMap<String, SmackChannel>();

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
//            abstractXMPPConnection.sendNonza();
            currentUser = this.createJid(userName);
            abstractXMPPConnection.login(userName, passwd);
            channelList.put(userName,this);
            log.info("用户={},连接登录成功!", userName);
        } catch (Exception e) {
            if(e instanceof SASLErrorException){
                SASLErrorException saslErrorException = (SASLErrorException) e;
                log.error("result:{}", saslErrorException.getSASLFailure().getDescriptiveText());
            }
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
            XHSession xhSession = new XHSession();
            xhSession.setUsername("test001");
            xhSession.setActive("0");
            xhSession.setChangeDate(99);
            xhSession.setDescription("fsdfds");
            xhSession.setDeviceID("fsfds");

            XMPPTCPConnectionImpl xmpptcpConnection =  new XMPPTCPConnectionImpl(config);
            xmpptcpConnection.setXhSession(xhSession);

            xmpptcpConnection.addStanzaInterceptor(new StanzaListener() {
                @Override
                public void processStanza(Stanza packet) throws SmackException.NotConnectedException, InterruptedException, SmackException.NotLoggedInException {
                        log.info("fdsfdsfds:{}", packet.toXML("").toString());
                }
            },null);
            abstractXMPPConnection = xmpptcpConnection;
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



}