package com.atguigu.jedis;


import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;


public class JedisDemo {

    public static void main(String[] args) {
        //创建Jedis对象
        Jedis jedis = new Jedis("192.168.174.188",6379);

        //测试连接
        String ping = jedis.ping();
        System.out.println("连接成功："+ping);
        jedis.close();
    }

    //1、string
    @Test
    public void test1(){
        //创建Jedis对象
        Jedis jedis = new Jedis("192.168.174.188",6379);
        jedis.set("name","lucy");
        System.out.println(jedis.get("name"));
    }

    //2、list
    @Test
    public void test2(){
        //创建Jedis对象
        Jedis jedis = new Jedis("192.168.174.188",6379);
        jedis.lpush("age","22","23","24","25");
        System.out.println(jedis.lrange("age",0,-1));
    }

    //3、set
    @Test
    public void test3(){
        //创建Jedis对象
        Jedis jedis = new Jedis("192.168.174.188",6379);
        jedis.sadd("language","java","php","c++");
        System.out.println(jedis.smembers("language"));
    }

    //4、hash
    @Test
    public void test4(){
        //创建Jedis对象
        Jedis jedis = new Jedis("192.168.174.188",6379);
        jedis.hset("user","id","1");
        jedis.hset("user","name","look");
        System.out.println(jedis.hget("user","name"));
        Map<String,String> map = new HashMap<>();
        map.put("id","1");
        map.put("name","waaw");
        map.put("age","22");
        jedis.hmset("user1", map);
        System.out.println(jedis.hmget("user1","id","name","age"));
    }

    //5、zSet
    @Test
    public void test5(){
        //创建Jedis对象
        Jedis jedis = new Jedis("192.168.174.188",6379);
        Map<String,Double> map = new HashMap<>();
        map.put("java",100d);
        map.put("c++",200d);
        map.put("php",300d);
        jedis.zadd("key",map);
        System.out.println(jedis.zrange("key", 0, -1));
        jedis.zadd("key1",100d,"mysql");
        jedis.zadd("key1",200d,"sqlserver");
        jedis.zadd("key1",300d,"redis");
        System.out.println(jedis.zrange("key1", 0, -1));
    }


}
