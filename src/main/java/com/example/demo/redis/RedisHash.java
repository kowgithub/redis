package com.example.demo.redis;

import com.example.demo.util.RedisConnection;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RedisHash {
    private static Jedis jedis = RedisConnection.getJedisConnection();

    public static void main(String[] args) {

        jedis.flushAll();
        System.out.println("flashAll");

        //hset  赋值一个字段
        // mhset 赋值多个字段
        //hsetnx 只有在字段不存在的时候，才设置
        Map<String, String> map = new HashMap<String, String>();
        map.put("a", "1");
        map.put("b", "2");
        map.put("c", "3");
        map.put("d", "4");
        jedis.hmset("number", map);
        System.out.println("-----------------------------------------");

        //hget 获取指定字段的值
        //hexists 是否存在field
        //hdel 删除字符
        System.out.println("number:d = " + jedis.hget("number", "d"));
        System.out.println("before delete d:" + jedis.hexists("number", "d"));
        jedis.hdel("number", "d");
        System.out.println("after delete d:" + jedis.hexists("number", "d"));
        System.out.println("number:d = " + jedis.hget("number", "d"));
        System.out.println("-----------------------------------------");

        //HgetAll 获取key所有的字段
        System.out.println("获取当前key所有的字段");
        Map<String, String> map1 = jedis.hgetAll("number");
        for (Map.Entry<String, String> entry : map1.entrySet()) {
            System.out.println("key == " + entry.getKey() + ", Value = " + entry.getValue());
        }
        System.out.println("-----------------------------------------");

        //hincrby 整数加
        //hincrbyfloat 浮点数相加
        jedis.hincrBy("number", "a", 1);
        System.out.println("number:a =" + jedis.hget("number", "a"));
        jedis.hincrBy("number", "a", -3);
        System.out.println("number:a =" + jedis.hget("number", "a"));
        jedis.hincrByFloat("number", "a", 1.5);
        System.out.println("number:a =" + jedis.hget("number", "a"));
        jedis.hincrByFloat("number", "a", -3.5);
        System.out.println("number:a =" + jedis.hget("number", "a"));
        System.out.println("-----------------------------------------");

        //hkeys 获取所有的字段
        //hmget 获取指定的field值
        System.out.println("打印所有的字段");
        Set<String> set = jedis.hkeys("number");
        for (String s : set) {
            System.out.println("number:" + s);
        }

        //hvals 获取所有的值
        System.out.println("获取所有的值");
        List<String> list = jedis.hvals("number");
        for (String s : list) {
            System.out.println("number:* =" + s);
        }
    }
}
