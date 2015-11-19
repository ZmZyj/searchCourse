package com.search.controllers;

import java.util.Enumeration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.search.service.MainService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("show")
public class ShowController {
	
	@Resource(name = "mainService")
	private MainService mainService;
	

	/** 
	* params --> {q:'',start:''}
	* 
	* @return
	*/ 
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		JSONObject paramJo = new JSONObject();
		paramJo.put("wt", "json");
		paramJo.put("indent", "true");
		paramJo.put("q", "*");
		Enumeration<String> params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String param = (String) params.nextElement();
			String value = request.getParameter(param);
 			paramJo.put(param, value);
		}
		JSONObject json = mainService.search(paramJo);
		model.addAttribute("json", json);
		return "show";
	}
}
