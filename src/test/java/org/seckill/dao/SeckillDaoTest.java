package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 配置spring和junit整合，Junit启动时加载springIOC容器
 * spring-test,junit(依赖)
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉Junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

    //注入Dao实现依赖
    @Resource
    private SeckillDao seckillDao;

    @Test
    public void queryById() throws Exception {
        long id = 1000;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println("商品名称："+seckill.getName());
        System.out.println("秒杀具体信息："+seckill);
    }

    @Test
    public void queryAll() {

        //Caused by: org.apache.ibatis.binding.BindingException: Parameter 'offset' not found. Available parameters are [0, 1, param1, param2]
        //java没有保存形参的记录：List<Seckill> queryAll(int offset, int limit) ->queryAll(arg0,arg1)
        List<Seckill> seckills = seckillDao.queryAll(0, 3);
        for (Seckill seckill: seckills) {
            System.out.println(seckill);
        }
    }

    @Test
    public void reduceNumber() {
        Date killTime = new Date();
        int upadateCount = seckillDao.reduceNumber(1000L, killTime);
        System.out.println("=====>> upadateCount=" + upadateCount);
    }
}