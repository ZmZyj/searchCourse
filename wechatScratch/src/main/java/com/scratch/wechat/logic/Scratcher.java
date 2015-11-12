package com.scratch.wechat.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.scratch.wechat.entity.Article;

public class Scratcher {

	public static List<Article> scratchArticles() {
		List<Article> result = new ArrayList<Article>();
		String url = "http://weixin.sogou.com/?p=73141200&kw=";
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			Element e = doc.getElementById("container");
			List<Element> list = e.getElementsByClass("wx-news2").first().getElementsByClass("wx-news2-left")
			.first().getElementsByClass("wx-box").first().getElementsByClass("wx-tabbox2");
			for(Element tmp: list){
				List<Element> uls = tmp.getElementsByTag("ul");
				for(Element ul: uls){
					List<Element> lis = ul.getElementsByTag("li");
					for(Element li :lis){
						Article article = getArticleByLi(li.getElementsByClass("wx-news-info2").first());
						result.add(article);
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i = 0; i < 20; i ++){
			List<Article> list = getTagArticles(i);
			result.addAll(list);
		}
		return result;
	}
	
	private static List<Article> getTagArticles(int i) {
		List<Article> list = new ArrayList<Article>();
		String url = "http://weixin.sogou.com/pcindex/pc/pc_" + i + "/pc_" + i + ".html";
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			List<Element> lis = doc.getElementsByTag("li");
			for(Element li :lis){
				Article article = getArticleByLi(li.getElementsByClass("wx-news-info2").first());
				list.add(article);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	private static Article getArticleByLi(Element info) {
		Article article = new Article();
		Element title = info.getElementsByTag("h4").first().getElementsByTag("a").first();
		article.setTitle(title.html());
		String url = title.attr("href");
		article.setUrl(url);
		String[] arr = url.split("&");
		String mid = "";
		String idx = "";
		for(String s: arr){
			if(s.startsWith("mid=")){
				mid = s.substring(4);
			}
			if(s.startsWith("idx=")){
				idx = s.substring(4);
			}
		}
		article.setArticleid(mid + "_" + idx);
		return article;
	}
	

	public static void main(String[] args) {
		String url = "http://weixin.sogou.com/pcindex/pc/pc_2/pc_2.html";
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			System.out.println(doc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
