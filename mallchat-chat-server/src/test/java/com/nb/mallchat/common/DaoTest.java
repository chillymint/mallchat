package com.nb.mallchat.common;

import com.nb.mallchat.common.common.thread.MyUncaughtExceptionHandler;
import com.nb.mallchat.common.common.utils.JwtUtils;
import com.nb.mallchat.common.common.utils.RedisUtils;
import com.nb.mallchat.common.user.LoginService;
import com.nb.mallchat.common.user.dao.UserDao;
import com.nb.mallchat.common.user.domain.entity.User;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DaoTest {
    public static final long UID = 12717L;
    @Autowired
    UserDao userDao;
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    LoginService loginService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void redis() {
        RLock lock = redissonClient.getLock("123");
        lock.lock();
        System.out.println();
        lock.unlock();
        // redisTemplate.opsForValue().set("name","卷心菜");
        // String name = (String) redisTemplate.opsForValue().get("name");
        // System.out.println(name); //卷心菜
    }

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Test
    public void thread() throws InterruptedException {
        Thread thread = new Thread(() ->{
            if(1 == 1) {
                log.error("123");
                // throw new RuntimeException("true");
            }
            });

        thread.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        thread.start();
        Thread.sleep(200);
    }


    @Test
    public void redis2() {
        RedisUtils.set("names","高坚果");
        String names = RedisUtils.get("names");
        System.out.println(names);
    }

    @Test
    public void jwt(){
        String token1 = jwtUtils.createToken(1L);
        String token2 = jwtUtils.createToken(1L);
        System.out.println(token1 + "<br/>" +token2);
        // String login = loginService.login(UID);
        // System.out.println(login);
    }

    @Test
    public  void testCreateToken(){
        String token = loginService.login(10276L);
        System.out.println("token=>:" + token);
    }

    @Test
    public void test() throws WxErrorException {
//        User byId = userDao.getById(1);
//        User insert = new User();
//        insert.setName("11");
//        insert.setOpenId("123");
//        boolean save = userDao.save(insert);
//        System.out.println(save);
        WxMpQrCodeTicket wxMpQrCodeTicket = wxMpService.getQrcodeService().qrCodeCreateTmpTicket(1, 10000);
        String url = wxMpQrCodeTicket.getUrl();
        System.out.println(url);
    }
}
