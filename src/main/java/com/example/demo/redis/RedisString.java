package com.example.demo.redis;

import com.example.demo.util.RedisConnection;
import redis.clients.jedis.Jedis;

import java.util.List;

public class RedisString {
    private static Jedis jedis = RedisConnection.getJedisConnection();

    public static void main(String[] args) {
        jedis.flushAll();
        System.out.println("flashAll");

        //setnx只有key不存在时候才生效
        //set get
        jedis.set("firstName", "jiulong");
        jedis.set("lastName", "lee");
        System.out.println("firstName:" + jedis.get("firstName"));
        System.out.println("lastName:" + jedis.get("lastName"));
        System.out.println("-----------------------------------------");

        //getrange 返回子串
        System.out.println("firstName的前三个字符是：" + jedis.getrange("firstName", 0, 2));
        System.out.println("firstName的后四个字符是：" + jedis.getrange("firstName", -4, -1));
        System.out.println("-----------------------------------------");

        //Getset 修改String-value并返回旧value
        System.out.println("已修改firstName.firstName修改前的值为：" + jedis.getSet("firstName", "jiulong1"));
        System.out.println("firstName修改后的值为：" + jedis.get("firstName"));
        System.out.println("-----------------------------------------");

        //getbit 根据偏移量获取位值
        System.out.println("firstName的1 3 5 位分别为：" + jedis.getbit("firstName", 0) +
                jedis.getbit("firstName", 2) + jedis.getbit("firstName", 4));
        System.out.println("-----------------------------------------");

        //mget 获取多个value
        List<String> list = jedis.mget("firstName", "lastName");
        for (String s : list) {
            System.out.println(s);
        }
        System.out.println("-----------------------------------------");

        //setbit 对位进行修改
        jedis.setbit("firstName", 0, "1");
        System.out.println("第一位修改为1后：" + jedis.get("firstName"));
        jedis.setbit("firstName", 0, false);
        System.out.println("第一位还原后：" + jedis.get("firstName"));
        System.out.println("-----------------------------------------");

        //setex 赋值并设置过期时间
        //psetex 时间是毫秒格式
        jedis.setex("name", 3, "name");
        System.out.println("设置name的值为name，同时设置过期时间");
        System.out.println("name的过期时间为：" + jedis.ttl("name" + "s"));
        System.out.println("-----------------------------------------");

        //setrange 对value偏移量后面的值进行修改
        jedis.setrange("name", 1, "AME");
        System.out.println("name值除第一位进行修改：" + jedis.get("name"));
        System.out.println("-----------------------------------------");

        //strlen 返回字符串的长度
        System.out.println("firstName的长度为：" + jedis.strlen("firstName"));
        System.out.println("lastName的长度为：" + jedis.strlen("lastName"));
        System.out.println("-----------------------------------------");

        //mset同时设置多个key-values
        //msetnx 设置仅当给的不存在
        jedis.mset("a", "1", "b", "2", "c", "3", "d", "4");

        List<String> list1 = jedis.mget("a", "b", "c", "d");
        System.out.println("同时对a b c d进行赋值,并打印出来");
        for (String s : list1) {
            System.out.print(" ");
            System.out.print(s);
        }
        System.out.println();
        System.out.println("-----------------------------------------");

        //incr +1,  incrby +n
        //decr -1 decrBy -n
        jedis.incrBy("a", 3);
        jedis.incrBy("b", 2);
        jedis.incr("c");
        List<String> list2 = jedis.mget("a", "b", "c", "d");
        System.out.println("同时对a b c d进行+3 +2 +1 +0,并打印出来");
        for (String s : list2) {
            System.out.print(" ");
            System.out.print(s);
        }
        System.out.println();

        jedis.decr("a");
        jedis.decrBy("b", 2);
        jedis.decrBy("c", 3);
        jedis.decrBy("d", 4);
        List<String> list3 = jedis.mget("a", "b", "c", "d");
        System.out.println("同时对a b c d进行-1 -2 +-3 -4,并打印出来");
        for (String s : list3) {
            System.out.print(" ");
            System.out.print(s);
        }
        System.out.println();
        System.out.println("-----------------------------------------");

        //append 追加value值a
        System.out.println("before append" + "\n" + jedis.get("lastName"));
        jedis.append("lastName", "a");
        System.out.println("after append" + "\n" + jedis.get("lastName"));
        System.out.println("-----------------------------------------");
    }
}
