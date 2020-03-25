package com.simulate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatRunner {

    private static final Logger log = LoggerFactory.getLogger(ChatRunner.class);
    private static final ExecutorService executors = Executors.newFixedThreadPool(10000);

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
        channelConfig.setGroupID("group001");
        channelConfig.setGroupSubDomain("conference.xcom0012");
        channelConfig.setHost("192.168.0.146");
        channelConfig.setPort(5222);

//        SmackChannel channel = new SmackChannel(channelConfig);
//        channel.login("test001", "123456");


        // codm_1080032016
        ChannelConfig proChannel = new ChannelConfig();
        proChannel.setDomainName("xcom0015");
        proChannel.setGroupID("codm_1080032016");
        proChannel.setGroupSubDomain("conference.xcom0015");
        proChannel.setHost("192.168.0.9");
        proChannel.setPort(5222);
        SmackChannel proSmack = new SmackChannel(proChannel);
        proSmack.login("cod_60002858", "a123456");

        int count = 0;
        CountDownLatch countDownLatch;
        for(int x=0;x<10;x++){
            countDownLatch = new CountDownLatch(1);
            for (int i=0;i<100;i++){
                executors.execute(new SubSendMessage(proSmack, countDownLatch));
            }
            countDownLatch.countDown();
//            try {
//                countDownLatch.await();
//                log.info("第{}批,1000个任务己执行完毕!", x+1);
//                count = count+1000;
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
        log.info("结束了..................!{}",count);

//        while (true){
//            System.out.println("请输入：");
//            Scanner sc = new Scanner(System.in);
//            String str = sc.nextLine();
////            log.info("{}", JSON.toJSONString(str.split("\\s")));
//            if(str.equals("exit")) {
//                System.out.println("Bye!");
//                return;
//            }
////            proSmack.sendGroup2Message("codm_1080032016", str);
//
//
//        }
    }
}
