package com.search.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.search.util.StringUtil;
import com.search.util.WebUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("mainService")
public class MainService {

	public JSONObject search(JSONObject paramJo) {
		StringBuffer url = new StringBuffer("http://localhost:8080/solr/db/select");
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		@SuppressWarnings("unchecked")
		Set<String> keys = paramJo.keySet();
		for(String key: keys){
			parameterMap.put(key, paramJo.opt(key));
		}
		String re = WebUtil.get(url.toString(), parameterMap);
		return JSONObject.fromObject(re);
	}

	public JSONObject list(JSONObject params) {
		if(StringUtil.isEmpty(params.optString("q"))){
			params.put("q", "*");
		}
		int page = params.optInt("page");
		int pageSize = params.optInt("pageSize");
		params.put("wt", "json");
		params.put("indent", "true");
		params.put("df", "articleTitle");
		params.put("start", page * pageSize);
		params.remove("page");
		params.remove("pageSize");
		JSONObject data = search(params);
		JSONObject result = new JSONObject();
		JSONObject response = data.optJSONObject("response");
		int total = response.optInt("numFound");
		int pageNum = total/pageSize;
		pageNum = pageNum*pageSize < total?pageNum + 1: pageNum;
		result.element("total", total);
		result.element("pageNum", pageNum);
		JSONArray docs = response.optJSONArray("docs");
		List<JSONObject> jolist = new ArrayList<JSONObject>();
		for(int i = 0; i < docs.size(); i ++){
			JSONArray j1 = docs.getJSONObject(i).optJSONArray("articleTitle");
			JSONObject jo = new JSONObject();
			jo.element("articleTitle", j1.optString(0));
			jo.element("articleUrl", docs.getJSONObject(i).optString("articleUrl"));
			jolist.add(jo);
		}
		result.element("list", JSONArray.fromObject(jolist));
		
		return result;
	}

}
