package com.atguigu.jedis;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

import java.util.Random;

public class PhoneCode {

    //模拟验证码发送
    @Test
    public void test1(){
        verifyCode("17357782237");
    }

    //模拟验证码校验
    @Test
    public void test2(){
        getRedisCode("17357782237","888");
    }

    //1、生成6位数字验证码
    public static String getCode(){
        Random random = new Random();
        String code = "";
        for (int i = 0;i < 6;i++){
            int ran = random.nextInt(10);
            code += ran;
        }
        return code;
    }

    //2、每个手机每天只能发三次，验证码放到redis中，设置过期时间
    public static void verifyCode(String phone){
        //连接redis
        Jedis jedis = new Jedis("192.168.174.188",6379);
        //拼接key
        //手机发送次数key
        String countKey = "VerifyCode" + phone + ":count";
        //验证码key
        String codeKey = "VerifyCode"+phone + ":code";
        //每个手机只能发送三次
        String count = jedis.get(codeKey);
        if (count == null){
            //没有发送次数，第一次发送
            //设置发送次数是1
            jedis.setex(countKey,24L*60L*60L,"1");
        }else if (Integer.parseInt(count) <= 2){
            //发送次数+1
            jedis.incr(codeKey);
        }else if (Integer.parseInt(count) > 2){
            //发送次数是三次了不能发送了
            System.out.println("今天发送次数已经超过三次");
            jedis.close();
        }

        //发送验证码到redis中
        String vcode = getCode();
        jedis.setex(codeKey,120L,vcode);
        jedis.close();
    }

    //3、验证码校验
    public static void getRedisCode(String phone,String code){
        //连接redis
        Jedis jedis = new Jedis("192.168.174.188",6379);
        //从redis中获取验证码
        //验证码key
        String codeKey = "VerifyCode"+phone + ":code";
        String redisCode = jedis.get(codeKey);
        if (redisCode == null){
            System.out.println("验证码为空");
        }else {
            if (redisCode.equals(code)){
                System.out.println("成功");
            }else {
                System.out.println("失败");
            }
        }

        jedis.close();
    }
}
