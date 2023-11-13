package com.nb.mallchat.common;

import com.nb.mallchat.common.user.dao.UserDao;
import com.nb.mallchat.common.user.domain.entity.User;
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

    @Test
    public void test(){
        User byId = userDao.getById(1);
        User insert = new User();
        insert.setName("11");
        insert.setOpenId("123");
        boolean save = userDao.save(insert);
        System.out.println(save);
    }
}
