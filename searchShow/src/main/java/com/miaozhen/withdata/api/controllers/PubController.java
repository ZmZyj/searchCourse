package com.miaozhen.withdata.api.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/** 
* 公共控制器
* 
* @author zhangmin@miaozhen.com
* @date 2015-3-2 下午2:51:26 
* 
*/ 
@Controller
@RequestMapping("pub")
public class PubController {
	
	public Logger logger = LoggerFactory.getLogger(PubController.class);
	
}
