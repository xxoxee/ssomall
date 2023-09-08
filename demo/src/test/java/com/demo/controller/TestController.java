package com.demo.controller;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.demo.service.MemberService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class TestController {

	@Inject
	MemberService service;
	
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	/* ID duplicate check Test */
	@Test
	public void testCheckIdDuplicated() throws Exception{
		
		int count = service.checkIdDuplicate("123r");
		System.out.println(count);
	}
}
