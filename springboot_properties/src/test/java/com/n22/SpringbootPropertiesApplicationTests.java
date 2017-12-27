package com.n22;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootPropertiesApplicationTests {

	@Test
	public void contextLoads() {
        System.out.println("自定义属性加载,第一次加载："+AppYml.getMaxSize()+"---------"+AppYml.getMinSize()+"----------"+AppYml.getUuid());
    }

}
