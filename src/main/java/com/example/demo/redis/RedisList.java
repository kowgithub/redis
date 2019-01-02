package com.example.demo.redis;

import com.example.demo.util.RedisConnection;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;

import java.util.List;

public class RedisList {
    private static Jedis jedis = RedisConnection.getJedisConnection();

    public static void main(String[] args) {

        jedis.flushAll();
        System.out.println("flashAll");

        //Lpush，Lpushx(已存在的情况下)
        //Rpush, Ppushx
        jedis.lpush("list", "d", "e");
        jedis.lpushx("list", "R");
        jedis.rpush("list", "i");
        jedis.rpushx("list", "s");
        jedis.lpush("list", "s");
        jedis.lpush("list", "i");
        jedis.lpush("list", "d");
        jedis.lpush("list", "e");
        jedis.lpush("list", "R");
        System.out.println("-----------------------------------------");

        //llen  lrange 获取指定范围
        System.out.println("list的长度：" + jedis.llen("list"));
        printList("list");

        //Ltrim 剪取
        jedis.ltrim("list", 1, 8);
        System.out.println("剪取首尾俩元素");
        printList("list");

        //Blpop Brpop 移除，无元素会阻塞或超市
        System.out.println("弹出的元素为：" + jedis.blpop(10, "list"));
        printList("list");
        System.out.println("弹出的元素为：" + jedis.brpop(10, "list"));
        printList("list");

        //lset
        jedis.lset("list", 0, "2");
        System.out.println("将第一个字段的值设置成2");
        printList("list");

        //lrem
        jedis.lrem("list", 1, "2");
        System.out.println("从左到右移除1个2");
        printList("list");

        //lindex
        System.out.println("获取第一个值");
        System.out.println(jedis.lindex("list", 0));

        //linsert i前面插入k
        jedis.linsert("list", BinaryClient.LIST_POSITION.BEFORE, "i", "k");
        printList("list");

        //brpoplpush  list弹出到另一个list里面
        jedis.lpush("list1", "1");
        jedis.brpoplpush("list", "list1", 10);
        printList("list");
        printList("list1");

    }

    private static void printList(String name) {
        List<String> list = jedis.lrange(name, 0, 10);
        for (String s : list) {
            System.out.print(s);
            System.out.print(" ");
        }
        System.out.println();
        System.out.println("-----------------------------------------");
    }

}
