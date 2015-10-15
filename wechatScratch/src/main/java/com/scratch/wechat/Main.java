package com.scratch.wechat;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.scratch.wechat.entity.Article;
import com.scratch.wechat.logic.DbSaver;
import com.scratch.wechat.logic.IndexCreater;
import com.scratch.wechat.logic.Scratcher;


public class Main {

	private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        String configLocation = "applicationContext.xml";
        ApplicationContext context = new ClassPathXmlApplicationContext(configLocation);
        logger.info("Application start at " + context.getStartupDate());
        List<Article> list = Scratcher.scratchArticles();
        DbSaver.saveArticlesToDb(list);
        IndexCreater.createIndex();
    }
}
