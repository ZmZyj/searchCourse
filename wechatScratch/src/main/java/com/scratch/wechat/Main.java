package com.scratch.wechat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Main {

	private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        String configLocation = "applicationContext.xml";
        ApplicationContext context = new ClassPathXmlApplicationContext(configLocation);
        logger.info("Application start at " + context.getStartupDate());
    }
}
