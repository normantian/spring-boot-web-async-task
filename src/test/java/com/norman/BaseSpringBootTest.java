package com.norman;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author tianfei
 * @version 1.0.0
 * @description </br>
 * @date 2018/12/5 10:18 AM.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseSpringBootTest {
    @Before
    public void setUp() throws Exception {
        System.out.println("=========begin==========");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("=========end==========");
    }
}
