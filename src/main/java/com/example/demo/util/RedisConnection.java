package com.example.demo.util;

import redis.clients.jedis.Jedis;

public class RedisConnection {

    public static Jedis getJedisConnection(){
        Jedis jedis = new Jedis("47.93.184.41",6381);
        System.out.println("redis connected");
        System.out.println("server is running:"+jedis.ping());
        return jedis;
    }
}
