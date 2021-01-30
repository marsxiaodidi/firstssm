package com.atguigu.controller;

import com.atguigu.dao.UserDao;
import com.atguigu.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.Transaction;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

@Controller
public class UserController {
    @Autowired
    UserDao userDao;

    @RequestMapping("findAll")
    public String findAll() {
        User all = userDao.findAll();
        System.out.println(all);
        return "success";

    }

    @RequestMapping("getCode")
    public void getCode(@RequestParam("phoneNumber") String phoneNumber) {
        Jedis jedis = new Jedis("192.168.241.168",6379);
        Random random = new Random();
        int i = random.nextInt(90000) + 10000;
        System.out.println(i);
        System.out.println("怎么回事");
        jedis.set(phoneNumber, String.valueOf(i));
        String s = jedis.get(phoneNumber);
        System.out.println(s);
        System.out.println("是否经过了这里");

    }

    @RequestMapping("verifyCode")
    public String verifyCode(@RequestParam("phoneNumber")String phoneNumber,@RequestParam("code")String code) {
        Jedis jedis = new Jedis("192.168.241.168",6379);
        String s = jedis.get(phoneNumber);
        if (s.equals(code)) {
            System.out.println("登入成功");
            return "success";
        } else {
            return null;
        }

    }
    @RequestMapping("seckill")
    public void seckill(HttpServletResponse response,@RequestParam("itemId") String itemId) throws IOException {

        Jedis jedis = new Jedis("192.168.241.168",6379);
        //用来监听key为itemId的事件,只要事件的值变化了，其他事务的执行就取消
        jedis.watch(itemId);

        jedis.incr("1001number");

        int s = Integer.parseInt(jedis.get(itemId));


        if (s > 0) {
            //这个用来执行事务,减少一个
            //建立一个multi的事务
            Transaction multi = jedis.multi();
            multi.decr(itemId);
            //提交事务
            List<Object> exec = multi.exec();
            if (exec == null || exec.size() == 0) {
                System.out.println("秒杀失败");
                response.getWriter().write("false");
            } else {
                System.out.println("秒杀成功");
                response.getWriter().write("true");
            }


        } else {
            System.out.println("秒杀失败");

        }

    }





    public static void main(String[] args) {
        /*Jedis jedis = new Jedis("192.168.241.168",6379);
        jedis.set("xiaodidi", "312");
        String s = jedis.get("xiaodidi");
        System.out.println(s);*/
        HashSet<HostAndPort> hostAndPorts = new HashSet<>();
        hostAndPorts.add(new HostAndPort( "192.168.241.168",6379));
        JedisCluster jedis = new JedisCluster(hostAndPorts);
        String set = jedis.set("xiao", "gegehaoshuai");
        System.out.println(jedis.get("xiao"));
        System.out.println(jedis.get("mars"));
    }
}
