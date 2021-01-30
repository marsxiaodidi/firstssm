package com.atguigu.dao;

import com.atguigu.domain.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import static org.junit.Assert.*;

public class UserDaoTest {
    @Autowired
    UserDao userDao;

    @Test
    public void findAll() {

        Jedis jedis = new Jedis();

    }
}