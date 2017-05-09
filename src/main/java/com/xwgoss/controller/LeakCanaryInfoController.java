package com.xwgoss.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.xwgoss.bean.LeakCanaryInfoBean;
import com.xwgoss.service.LeakCanaryInfoService;


@RestController
@RequestMapping("/leakcancary")
public class LeakCanaryInfoController {
	@Autowired
	private LeakCanaryInfoService leakCanaryInfoService;
	
	@RequestMapping(path="/upload/info",method=RequestMethod.POST)
	public Map<String, String> uploadLeakCanaryInfo(@RequestParam("infos") String infos){
		Map<String,String> map=new HashMap<String,String>();
		map.put("recode","1");
		List<LeakCanaryInfoBean> l=new Gson().fromJson(infos,  new TypeToken<List<LeakCanaryInfoBean>>() {  
        }.getType());
		if(leakCanaryInfoService.saveInfos(l))
			map.put("recode","0");
		return map;
	}
	
	@RequestMapping(path="/upload/infoAndFile",method=RequestMethod.POST)
	public Map<String,String> uploadLeakCanaryInfoAndFile(@RequestParam("file") MultipartFile file){
		Map<String,String> map=new HashMap<String,String>();
		map.put("recode","1");
		if(leakCanaryInfoService.saveHumpFile(file))
			map.put("recode", "0");
		return map;
	}
	
	@RequestMapping(path="/get/infos",method=RequestMethod.GET)
	public List<LeakCanaryInfoBean> getInfos(@RequestParam("pkg") String packagename){
		return leakCanaryInfoService.getInfos(packagename);
	}
	@RequestMapping(path="/get/packages",method=RequestMethod.GET)
	public Set<String> getInfos(){
		return leakCanaryInfoService.getPackagenames();
	}
	@RequestMapping(path="/get/leakcanaryInfo",method=RequestMethod.GET)
	public LeakCanaryInfoBean getLeakCanaryInfoBean(@RequestParam("id") String id){
		return leakCanaryInfoService.getLeakCanaryInfo(id);
	}
}
