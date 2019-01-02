package com.example.demo.redis;

import com.example.demo.util.RedisConnection;
import redis.clients.jedis.Jedis;

import java.util.Set;

public class RedisZset {
    private static Jedis jedis = RedisConnection.getJedisConnection();

    public static void main(String[] args) {

        jedis.flushAll();
        System.out.println("flashAll");

        //zadd 向集合添加元素
        jedis.zadd("zset", 1, "a");
        jedis.zadd("zset", 4, "d");
        jedis.zadd("zset", 2, "b");
        jedis.zadd("zset", 3, "c");
        jedis.zadd("zset", 5, "e");
        print("zset");

        //zcount 指定分数区间
        System.out.println("2-4的个数为：" + jedis.zcount("zset", 2, 4));

        //zincrby
        jedis.zincrby("zset", 6, "a");
        System.out.println("zset中a的分数增加6");
        print("zset");

        //zlexcount
        System.out.println("a-c中的数量为：" + jedis.zlexcount("zset", "-", "+"));

        //zrange
        Set<String> set = jedis.zrange("zset", 1, 4);
        System.out.println("获取zset前4个");
        for (String s : set) {
            System.out.print(s);
            System.out.print(" ");
        }
        System.out.println("");

        //zrank 返回索引
        System.out.println("a的索引为：" + jedis.zrank("zset", "a"));

        //zrem
        jedis.zrem("zset", "a");
        System.out.println("移除a");
        System.out.println("a的索引为：" + jedis.zrank("zset", "a"));

        //zremrangebylex
        //zremrangebyscore
        //zremrangebysocre

        //zrevrange 反转
        //zrevrangebyscore 反转指定分数区间的元素
        print("zset");
        Set<String> set1 = jedis.zrevrange("zset", 0, 4);
        System.out.println("反转后：");
        for (String s : set1) {
            System.out.print(s);
            System.out.print(" ");
        }
        System.out.println();

        //zscore
        System.out.println("c的分数为：" + jedis.zscore("zset", "c"));

        //zrank
        System.out.println("c的排名为：" + jedis.zrank("zset", "c"));
    }

    //zrange
    //zcare
    private static void print(String name) {
        System.out.println(name + "的zset的个数为:" + jedis.zcard(name));
        Set<String> set = jedis.zrange(name, 0, 4);
        for (String s : set) {
            System.out.print(s);
            System.out.print(" ");
        }
        System.out.println();
    }
}
