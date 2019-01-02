package com.example.demo.redis;

import com.example.demo.util.RedisConnection;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class RedisPublish {
    //发布线程
    //接收线程
    private static Jedis jedis = RedisConnection.getJedisConnection();
    private static Jedis jedis1 = RedisConnection.getJedisConnection();

    private static MyPubSub myPubSub = new MyPubSub();

    //publish 向频道发送消息
    //subscribe 订阅频道
    //psubscribe 订阅符合模式的频道
    //punsubscibe 取消符合模式的频道
    //unsubscribe 取消订阅频道
    public static void main(String[] args) {

        jedis.flushAll();
        System.out.println("flashAll");
        subscribe(myPubSub, "fm");
        publish("fm", "hello");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        myPubSub.unsubscribe();
        System.out.println("停止接收");
    }

    //subscribe线程
    private static void subscribe(MyPubSub myPubSub, String channel) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("subscribe线程开启");
                jedis.subscribe(myPubSub, channel);
                System.out.println("subscribe线程关闭");
            }
        }).start();
    }

    //publish线程
    public static void publish(String channel, String message) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("publish线程开启");
                System.out.println("向" + channel + "发布消息");
                jedis1.publish(channel, message);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("publish线程关闭");
            }
        }).start();
    }
}

//重写订阅人方法
class MyPubSub extends JedisPubSub {

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        System.out.println("订阅了" + channel);
    }

    @Override
    public void onMessage(String channel, String message) {
        System.out.println("接收到了：" + channel + " 消息：" + message);
    }

    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
        System.out.println("取消订阅" + channel);
    }
}

