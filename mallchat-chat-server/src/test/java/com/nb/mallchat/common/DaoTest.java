package com.nb.mallchat.common;

import com.nb.mallchat.common.user.dao.UserDao;
import com.nb.mallchat.common.user.domain.entity.User;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DaoTest {
    @Autowired
    UserDao userDao;
    @Autowired
    private WxMpService wxMpService;

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
