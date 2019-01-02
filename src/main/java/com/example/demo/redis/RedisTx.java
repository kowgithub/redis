package com.example.demo.redis;

import com.example.demo.util.RedisConnection;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;

public class RedisTx {
    //获取redis连接
    private static Jedis jedis = RedisConnection.getJedisConnection();

    public static void main(String[] args) {
        //清空redis内存
        jedis.flushAll();
        System.out.println("flashAll");

        //watch 事务
        String watch = jedis.watch("abc");
        //下面这句触发watch
        //jedis.set("abc","2");
        System.out.println(Thread.currentThread().getName());
        Transaction multi = jedis.multi();
        multi.set("abc", "1");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<Object> exec = multi.exec();
        System.out.println(exec);
        jedis.unwatch();
    }
}
