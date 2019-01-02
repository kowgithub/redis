package com.example.demo.redis;

import com.example.demo.util.RedisConnection;
import redis.clients.jedis.Jedis;

public class RedisHyperLogLog {
    private static Jedis jedis = RedisConnection.getJedisConnection();

    public static void main(String[] args) {

        jedis.flushAll();
        System.out.println("flashAll");

        //pfadd
        //pfcount
        for (int i = 0; i < 10000; i++) {
            jedis.pfadd("pf", String.valueOf(i));
        }
        System.out.println("HyperLogLog的值为" + jedis.pfcount("pf"));
    }
}
