package com.chiliexe.springBlog;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBlogApplicationTests {

	enum teste {
		role1, role2
	}

	@Test
	void contextLoads() {
		teste role = teste.role1;
		System.out.println(role.ordinal());
	}

}
