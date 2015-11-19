package com.search.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.search.util.WebUtil;

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

}
