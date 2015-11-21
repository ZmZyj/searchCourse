package com.search.controllers;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.search.consts.Consts;
import com.search.service.MainService;

import net.sf.json.JSONObject;

@Controller
public class ShowController {
	
	@Resource(name = "mainService")
	private MainService mainService;
	

	/** 
	* params --> {q:'',start:''}
	* 
	* @return
	*/ 
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return "index";
	}
	
	/** 
	 * 列表页面
	 * 
	 * body --> {'page':1,'pageSize':10,'q':'keyword'}
	 * 
	 * result --> {'total':100,'list':[{'id':'1','createTime':'2015-3-4 15:21', 'type':'keyword','typeName':'关键词问题','title':'成功的三要素','studentNum':100,'creator':'刘建宏老师'}]}
	 * 
	 * @return
	 */ 
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject list(@RequestBody String body) {
		try{
			JSONObject params = JSONObject.fromObject(body);
			return mainService.list(params);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return Consts.JSON_FAIL;
	}
}
