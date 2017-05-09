package com.xwgoss.service.imp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.xwgoss.bean.LeakCanaryInfoBean;
import com.xwgoss.service.LeakCanaryInfoService;
import com.xwgoss.util.ConfigUtil;
import com.xwgoss.util.MatchConfig;
import com.xwgoss.util.StringUtil;
@Component
@EnableConfigurationProperties({ConfigUtil.class,MatchConfig.class})
public class LeakCanayInfoLocalServiceImp implements LeakCanaryInfoService {
	private static final Logger logger=Logger.getLogger(LeakCanayInfoLocalServiceImp.class);
	private Map<String,List<LeakCanaryInfoBean>> map=new ConcurrentHashMap<String,List<LeakCanaryInfoBean>>();
	private Map<String,LeakCanaryInfoBean> leakCanarys=new ConcurrentHashMap<String,LeakCanaryInfoBean>();
	@Autowired
	private ConfigUtil configUtil;
	@Autowired
	private MatchConfig matchConfig;
	@Override
	public synchronized boolean saveInfos(List<LeakCanaryInfoBean> l) {
		// TODO Auto-generated method stub
		for(LeakCanaryInfoBean leakCanary:l){
			String temp=leakCanary.getLeakinfo();
			leakCanary.setApk_packagename(matchConfig.getStringByMatch(temp,matchConfig.getPackageNameMatch()));
			leakCanary.setDevice_name(matchConfig.getStringByMatch(temp,matchConfig.getDeviceNameMatch()));
			leakCanary.setAndroid_version(matchConfig.getStringByMatch(temp,matchConfig.getAndroidVersionMatch()));
			leakCanary.setApk_version(matchConfig.getStringByMatch(temp, matchConfig.getApkVersion()));
			leakCanary.setLeakinfo(matchConfig.getStringByMatch(temp,matchConfig.getLeakinfo(),Pattern.DOTALL).replaceAll("\\*", "<br>"));
			leakCanary.setDetailInfo(matchConfig.getStringByMatch(temp,matchConfig.getDetaiLeakInfo(),Pattern.DOTALL).replaceAll("\\|", "<br>"));
			leakCanary.setId(leakCanary.toString());
			leakCanarys.put(leakCanary.getId(),leakCanary);
		}
		if(map.containsKey(l.get(0).getApk_packagename()))
			map.get(l.get(0).getApk_packagename()).addAll(l);
		else
			map.put(l.get(0).getApk_packagename(), l);
		return true;
	}
	
	@Override
	public boolean saveHumpFile(MultipartFile file) {
		// TODO Auto-generated method stub
		File f=null;
		//若未配置，则直接在当前目录生成
		if(configUtil.getHprof_path()==null||configUtil.getHprof_path().equals(""))
		{
			f=new File(file.getName());
		}else{
			f=new File(configUtil.getHprof_path()+File.separator+file.getOriginalFilename());
		}
		try {
			f.createNewFile();
			file.transferTo(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("生成文件时发生错误,错误信息：",e.fillInStackTrace());
			return false;
		}
		return true;
	}
	@Override
	public List<LeakCanaryInfoBean> getInfos(String packagename) {
		// TODO Auto-generated method stub
		return map.get(packagename);
	}
	@Override
	public Set<String> getPackagenames() {
		// TODO Auto-generated method stub
		return map.keySet();
	}

	@Override
	public LeakCanaryInfoBean getLeakCanaryInfo(String id) {
		// TODO Auto-generated method stub
		return leakCanarys.get(id);
	}

	

}
