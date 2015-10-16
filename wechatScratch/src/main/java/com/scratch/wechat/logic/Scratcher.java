package com.scratch.wechat.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.scratch.wechat.entity.Article;
import com.scratch.wechat.util.WebUtil;

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
					System.out.println(lis.size());
					for(Element li :lis){
						Article article = getArticleByLi(li);
						result.add(article);
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	private static Article getArticleByLi(Element li) {
		Article article = new Article();
		Element info = li.getElementsByClass("wx-news-info2").first();
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
		String r = WebUtil.get(url, null);
		System.out.println(r);
	}

}
