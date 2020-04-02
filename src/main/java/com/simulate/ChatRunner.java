package com.simulate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class ChatRunner {

    private static final Logger log = LoggerFactory.getLogger(ChatRunner.class);

    static class SubSendMessage implements Runnable {
        SmackChannel smackChannel;
        CountDownLatch countDownLatch;
        public SubSendMessage(SmackChannel smackChannel, CountDownLatch countDownLatch) {
            this.smackChannel = smackChannel;
            this.countDownLatch = countDownLatch;
        }
        @Override
        public void run() {

            try {
                countDownLatch.await();
                log.info("{}开始发送消息",Thread.currentThread().getName());
                smackChannel.sendGroup2Message("codm_1080032016", Thread.currentThread().getName()+"Helo0000000000000000000000");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }

    public static void main(String[] args) {

        ChannelConfig channelConfig = new ChannelConfig();
        channelConfig.setDomainName("xcom0012");
        channelConfig.setGroupID("Group001");
        channelConfig.setGroupSubDomain("conference.xcom0012");
        channelConfig.setHost("127.0.0.1");
        channelConfig.setPort(5222);

        SmackChannel channel = new SmackChannel(channelConfig);
//        channel.xlogin("test001", "a123456");
        channel.login("test001", "a123456");

//        SmackChannel channel1 = new SmackChannel(channelConfig);
//        channel1.login("test002", "a123456");
//
//        SmackChannel channel2 = new SmackChannel(channelConfig);
//        channel2.login("test003", "a123456");

        while (true){
            System.out.println("请输入：");
            Scanner sc = new Scanner(System.in);
            String str = sc.nextLine();
            if(str.equals("exit")) {
                System.out.println("Bye!");
                return;
            }
//            channel.sendGroup2Message("group001", str);
        }
    }
}
