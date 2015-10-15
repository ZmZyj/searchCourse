package com.scratch.wechat.logic;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.scratch.wechat.entity.Article;
import com.scratch.wechat.util.WebUtil;

public class Scratcher {

	public static List<Article> scratchArticles() {
		String url = "http://weixin.sogou.com/?p=73141200&kw=";
		String r = WebUtil.get(url, null);
		System.out.println(r);
		return null;
	}
	
	public static void main(String[] args) {
		String url = "http://weixin.sogou.com/?p=73141200&kw=";
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			Element e = doc.getElementById("body");
			System.out.println(e.text());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
