package com.atguigu.jedis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class JedisClusterTest {

    public static void main(String[] args) {

        HostAndPort hostAndPort = new HostAndPort("192.168.174.188",6379);
        JedisCluster jc = new JedisCluster(hostAndPort);

        jc.set("name","lucy");

        String k1 = jc.get("name");
        System.out.println("value: " + k1);
    }
}
