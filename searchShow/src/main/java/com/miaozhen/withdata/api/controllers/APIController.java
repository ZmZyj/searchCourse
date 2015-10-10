package com.miaozhen.withdata.api.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/** 
* api控制器
* 
* @author zhangmin@miaozhen.com
* @date 2015-4-28 下午4:11:27 
* 
*/ 
@Controller
@RequestMapping("api")
public class APIController {
	
	public Logger logger = LoggerFactory.getLogger(APIController.class);
	
}
