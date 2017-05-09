package com.xwgoss.service;

import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.xwgoss.bean.LeakCanaryInfoBean;

public interface LeakCanaryInfoService {
	public boolean saveInfos(List<LeakCanaryInfoBean> l);
	public boolean saveHumpFile(MultipartFile file);
	public List<LeakCanaryInfoBean> getInfos(String packagename);
	public Set<String> getPackagenames();
	public LeakCanaryInfoBean getLeakCanaryInfo(String id);
}
