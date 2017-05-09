package com.xwgoss.util;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class ConfigUtil {
private String hprof_path;

public String getHprof_path() {
	return hprof_path;
}

public void setHprof_path(String hprof_path) {
	this.hprof_path = hprof_path;
}



}
