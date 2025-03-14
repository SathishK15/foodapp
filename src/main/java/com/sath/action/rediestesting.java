package com.sath.action;

import redis.clients.jedis.Jedis;

public class rediestesting {
	private static Jedis jedis;

    public static Jedis getJedis() {
    	 System.out.println("Conn Redis!");
        if (jedis == null) {
            jedis = new Jedis("localhost", 6379);  // Adjust if using Memurai or WSL
            System.out.println("Connected to Redis!");
        }
        return jedis;
    }
}
