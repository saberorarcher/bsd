package com.mos.bsd.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest

public class Test_IBsdVipService {
	 @Autowired  
	 @Qualifier("com.mos.bsd.service.impl.BsdVipServiceImpl")
     private IBsdVipService vip_service;  
	 
	 @Test  
     public void findAllUsers()  {  
		 vip_service.name();
           
     }  
}
