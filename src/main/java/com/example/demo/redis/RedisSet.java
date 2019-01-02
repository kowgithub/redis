package com.example.demo.redis;

import com.example.demo.util.RedisConnection;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanResult;

import java.util.Set;

public class RedisSet {
    private static Jedis jedis = RedisConnection.getJedisConnection();

    public static void main(String[] args) {

        jedis.flushAll();
        System.out.println("flashAll");

        //sadd 添加
        //sadd 获取集合的成员数
        jedis.sadd("set1", "a", "b", "c");
        jedis.sadd("set2", "d", "b", "c");
        jedis.sadd("set3", "d", "e", "c");
        print("set1");
        print("set2");
        print("set3");

        //smove讲一个元素从一个set移动到另一个set中
        jedis.smove("set1", "set2", "a");
        System.out.println("将a从set1中移动到set2中");
        print("set1");
        print("set2");
        print("set3");

        //Sdiff SdiffSotre
        System.out.println("set2和set1的差集");
        Set<String> set4 = jedis.sdiff("set2", "set1");
        for (String s : set4) {
            System.out.print(s);
            System.out.print(" ");
        }
        System.out.println();

        //Sinter SinterStore
        System.out.println("set1和set2的交集");
        Set<String> set5 = jedis.sinter("set1", "set2");
        for (String s : set5) {
            System.out.print(s);
            System.out.print(" ");
        }
        System.out.println();

        //Sunion SunionStore
        System.out.println("set1和set2的并集");
        Set<String> set6 = jedis.sunion("set1", "set2");
        for (String s : set6) {
            System.out.print(s);
            System.out.print(" ");
        }
        System.out.println();

        //Sismember
        System.out.println("a是set2的元素么？：" + jedis.sismember("set2", "a"));

        //spop
        System.out.println("随机移除Set1元素" + jedis.spop("set1"));

        //Srandmember 随机数
        String s = jedis.srandmember("set1");
        System.out.println("随机获取set2的元素" + s);

        //srem 随机移除 pass
    }

    private static void print(String name) {
        System.out.println(name + "的元素的个数一共有" + jedis.scard(name) + "个");
        Set<String> set = jedis.smembers(name);
        for (String s : set) {
            System.out.print(s);
            System.out.print(" ");
        }
        System.out.println();
        System.out.println("-----------------------------------------");
    }
}
