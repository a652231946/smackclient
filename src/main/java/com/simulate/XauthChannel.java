package com.simulate;

public class XauthChannel {

    public static void main(String[] args) {
        ChannelConfig channelConfig = new ChannelConfig();
        channelConfig.setDomainName("xcom0012");
        channelConfig.setGroupID("Group001");
        channelConfig.setGroupSubDomain("conference.xcom0012");
        channelConfig.setHost("127.0.0.1");
        channelConfig.setPort(5222);
        SmackChannel channel = new SmackChannel(channelConfig);
        channel.xlogin("test001", "a123456");
        while (true);
    }
}
