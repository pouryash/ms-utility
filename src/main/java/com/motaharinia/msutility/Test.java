package com.motaharinia.msutility;

public class Test {
    public static void main(String[] args) {
        String test="redis://127.0.0.1:7000,redis://127.0.0.1:7001,redis://127.0.0.1:7002";
        String[] redisClusteredUrlArray = test.split(",",0);
        System.out.println(redisClusteredUrlArray.length);
        System.out.println(redisClusteredUrlArray[0]);
        System.out.println(redisClusteredUrlArray[1]);
        System.out.println(redisClusteredUrlArray[2]);
    }
}
