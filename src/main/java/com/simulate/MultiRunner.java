package com.simulate;

import com.simulate.data.UserData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class MultiRunner {
    private static final Logger log = LoggerFactory.getLogger(MultiRunner.class);
    private static final ExecutorService executors = Executors.newFixedThreadPool(1000);
    private static final AtomicInteger count = new AtomicInteger(0);

    static class MultiRunnerBuile implements Runnable{
        private SmackChannel smackChannel;
        private CountDownLatch countDownLatch;
        private CountDownLatch endDownLatch;
        private UserData userData;

        public MultiRunnerBuile(ChannelConfig channelConfig,UserData userData, CountDownLatch countDownLatch, CountDownLatch endDownLatch) {
            this.countDownLatch = countDownLatch;
            this.endDownLatch = endDownLatch;
            this.userData = userData;
            this.smackChannel = new SmackChannel(channelConfig);
            this.smackChannel.login(userData.getUserName(), userData.getPasswd());
        }

        @Override
        public void run() {
            try {
                countDownLatch.await();
                log.info("{}开始发送消息",Thread.currentThread().getName());
                for (int i=0; i<5000;i++){
                    if(!smackChannel.sendGroupOK("codm_1080032016", Thread.currentThread().getName()+"Helo0000000000000000000000")) {
                        count.addAndGet(1);
                    }
                }
                endDownLatch.countDown();
            } catch (InterruptedException e) {
                log.error("发送消息异常", e);
//                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) throws Exception{
        ChannelConfig proChannel = new ChannelConfig();
        proChannel.setDomainName("xcom0015");
        proChannel.setGroupID("codm_1080032016");
        proChannel.setGroupSubDomain("conference.xcom0015");
        proChannel.setHost("192.168.0.9");
        proChannel.setPort(5222);

        List<UserData> userDataList = new ArrayList<UserData>();
        userDataList.add(new UserData("cod_60002850","ff4913d7c5388b2fbd86e9820818e346"));
        userDataList.add(new UserData("cod_60002858","a123456"));
        userDataList.add(new UserData("cod_60001793","e7bd83bc8cd963b2350e407be0af08f3"));
        userDataList.add(new UserData("cod_60000018","cbb6d7c11a2aeaf734ea84e87f360a60"));
        userDataList.add(new UserData("cod_60000161","0564efad78ee91abc2c072f12606a335"));
        userDataList.add(new UserData("cod_60000051","4d896dcb668251ece2aa0d5baca3c7ba"));
        userDataList.add(new UserData("cod_60000044","f6d10e6ce80ac6fa614d144ca8951b53"));
        userDataList.add(new UserData("cod_60000002","66c76c3ae1eb3eedd88bbb76d0ae8aed"));
        userDataList.add(new UserData("cod_60000001","fbc3752e68b5709db0c2fd7230212e67"));
        userDataList.add(new UserData("cod_60000022","cfa174fc31a0e405211aa50ee391edf0"));
        userDataList.add(new UserData("cod_60001944","cfa174fc31a0e405211aa50ee391edf0"));

        CountDownLatch countDownLatch = new CountDownLatch(1);
        CountDownLatch endCount = new CountDownLatch(userDataList.size());
        for(UserData userData : userDataList) {
            executors.execute(new MultiRunnerBuile(proChannel,userData,countDownLatch,endCount));
        }
        long start = System.currentTimeMillis();
        countDownLatch.countDown();
//        endCount.await();
//        long end = System.currentTimeMillis();
//        long costTime = (end - start) / 1000;
//         double f =  10*1000 / costTime;
//        log.info("本次共耗时={}s,失败次数={}, 平均每次发送{}次", costTime, count.get(), f);

    }
}
