package com.example.jpa;

import com.example.jpa.Model.User;
import com.example.jpa.Service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootJpaApplicationTests {
     @Autowired
    UserService userService;
	@Test
	public void contextLoads() {
	    User testUser;
        User testUser1;
        User testUser2;
    User user =new User();
    user.setName("12306");
    user.setPassword("123456");
        testUser=userService.findByName(user.getName());
        testUser1=userService.findByNameAndPassword(user.getName(),user.getPassword());
        testUser2=userService.findById(user);
        Page<User> users=userService.page(1,2);
        assert(testUser != null);
        assert(testUser1 != null);
        assert(testUser2 != null);
        assert(users.getSize() >0);

    }

}
