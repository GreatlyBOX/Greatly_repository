package com.example.ehcache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootEhcacheApplicationTests {

	@Resource
	private ServiceImpl testService;
	@Test
	public void contextLoads() {
		String domovalue1=testService.findById(666666L);
		System.out.println("第一次查询，值："+domovalue1);
		String domovalue2=testService.findById(666666L);
		System.out.println("第二次查询，值："+domovalue2);

	}

}
