package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * 配置spring和junit整合，Junit启动时加载springIOC容器
 * spring-test,junit(依赖)
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉Junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() {
        List<Seckill> list = seckillService.getSeckillList();
        logger.info("======>> list={}", list);
    }

    @Test
    public void getById() {
        long id = 1000;
        Seckill seckill = seckillService.getById(id);
        logger.debug("======>> seckill={}", seckill.getName());

    }

    //集成测试代码完整逻辑，注意可重复执行
    @Test
    public void testSeckillLogic() {
        long id = 1001;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        if (exposer.isExposed()) {

            logger.debug("======>> exposer={}", exposer);

            long phone = 18500300866L;
            String md5 = exposer.getMd5();
            try {
                SeckillExecution execution = seckillService.excuteSeckill(id, phone, md5);
                logger.debug("======>> result={}", execution);
            } catch (RepeatKillException e) {
                logger.error(e.getMessage());
            } catch (SeckillCloseException e) {
                logger.error(e.getMessage());
            }

        } else {
            //秒杀未开启
            logger.warn("======>> exposer={}", exposer);
        }

    }

/*

    @Test
    public void exportSeckillUrl() {
        long id = 1000;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        logger.info("======>> exposer={}",exposer);
        */
/**
 * 18:17:03.252 [main] INFO  o.seckill.service.SeckillServiceTest - ======>> exposer=Exposer{exposed=true,
 * md5='52355f43c43f6c673bba3e4278efadf3',
 * seckillId=1000, now=0, start=0, end=0}
 *//*

    }

    //由于这里的md5不应该每次先执行上一步再获得，所以将两个测试方法集成到一起
    @Test
    public void excuteSeckill() {
        long id = 1000;
        long phone = 12345678901L;
        String md5= "52355f43c43f6c673bba3e4278efadf3";
        try {
            SeckillExecution execution = seckillService.excuteSeckill(id, phone, md5);
            logger.info("======>> result={}", execution);
        }catch (RepeatKillException e){
            logger.error(e.getMessage());
        }catch (SeckillCloseException e){
            logger.error(e.getMessage());
        }

    }*/


    @Test
    public void executeSeckillProcedure(){
        long seckillId = 1001;
        long phone = 18500300866L;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if(exposer.isExposed()){
            String md5 = exposer.getMd5();
            SeckillExecution execution = seckillService.executeSeckillProdure(seckillId,phone,md5);
            logger.info(execution.getStateInfo());
        }
    }


}
