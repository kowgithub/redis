package com.example.demo.redis;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.demo.util.RedisConnection;
import redis.clients.jedis.Jedis;

import java.util.Set;

public class RedisKey {
    private static Jedis jedis = RedisConnection.getJedisConnection();

    public static void main(String[] args) {
        jedis.flushAll();
        System.out.println("flushAll");
        //set 存入两个key  a-1  b-b c-c
        //del 删除key
        jedis.set("a", "1");
        jedis.set("b", "b");
        jedis.set("c", "c");
        System.out.println("新增a = " + jedis.get("a"));
        System.out.println("新增b = " + jedis.get("b"));
        System.out.println("新增c = " + jedis.get("c"));
        jedis.del("c");
        System.out.println("删除key:c");
        System.out.println("c = " + jedis.get("c"));
        System.out.println("-----------------------------------------");

        //dump 序列化key，返回序列化的值
        byte[] b_dump = jedis.dump("a");
        System.out.println("a 被序列化的值" + b_dump.toString());
        System.out.println("-----------------------------------------");

        //exitst 判断a和b 是否存在
        System.out.println("a是否存在：" + jedis.exists("a"));
        System.out.println("b是否存在：" + jedis.exists("b"));
        System.out.println("c是否存在：" + jedis.exists("c"));
        System.out.println("-----------------------------------------");

        //Expire给键c-c设定过期时间
        //Expireat 已毫秒计时
        //ttl 返回过期时间
        //persist移除过期时间
        jedis.expire("a", 20);
        jedis.pexpire("b", 20000);
        System.out.println("a和b将在20秒后过期");
        jedis.persist("a");
        System.out.println("移除了a的过期时间");
        System.out.println("a的过期时间为：" + jedis.ttl("a") + " or " + jedis.pttl("a"));
        System.out.println("b的过期时间为：" + jedis.ttl("b") + "s or " + jedis.pttl("b") + "ms");
        System.out.println("系统暂停10000ms");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("系统暂停10000ms");
        System.out.println("a的过期时间为：" + jedis.ttl("a") + " or " + jedis.pttl("a"));
        System.out.println("b的过期时间为：" + jedis.ttl("b") + "s or " + jedis.pttl("b") + "ms");
        System.out.println("-----------------------------------------");

        //keys 正则匹配key
        Set<String> ss = jedis.keys("*");
        System.out.println("打印所有匹配'*'的key");
        for (String s : ss) {
            System.out.println(s);
        }
        System.out.println("-----------------------------------------");

        //move将key移动到指定数据库
        jedis.set("d", "d");
        jedis.move("d", 1);
        System.out.println("将d-d移动到index=1的数据库中");
        System.out.println("-----------------------------------------");

        //randomkey
        String key = jedis.randomKey();
        System.out.println("随机选取一个key：" + key);
        System.out.println("-----------------------------------------");

        //rename 修改key的名称
        //renamenx newkey不存在时使用
        jedis.rename("a", "aa");
        System.out.println("a修改后的key-value为" + "aa-" + jedis.get("aa"));
        jedis.rename("aa", "a");
        System.out.println("-----------------------------------------");
        try {
            jedis.renamenx("a", "b");
        } catch (Exception e) {
            System.out.println("b已经存在，不能将a改为b");
        }
        System.out.println("-----------------------------------------");

        //type 返回key值得类型
        System.out.println("a的值得类型为：" + jedis.type("a"));
        System.out.println("b的值得类型为：" + jedis.type("b"));
        System.out.println("c的值得类型为：" + jedis.type("c"));
        System.out.println("-----------------------------------------");
    }
}
