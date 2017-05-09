package com.xwgoss.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="match")
public class MatchConfig {
private String packageNameMatch;
private String deviceNameMatch;
private String androidVersionMatch;
private String apkVersion;
private String leakinfo;
private String detaiLeakInfo;
public String getPackageNameMatch() {
	return packageNameMatch;
}
public void setPackageNameMatch(String packageNameMatch) {
	this.packageNameMatch = packageNameMatch;
}
public String getDeviceNameMatch() {
	return deviceNameMatch;
}
public void setDeviceNameMatch(String deviceNameMatch) {
	this.deviceNameMatch = deviceNameMatch;
}
public String getAndroidVersionMatch() {
	return androidVersionMatch;
}
public void setAndroidVersionMatch(String androidVersionMatch) {
	this.androidVersionMatch = androidVersionMatch;
}

public String getApkVersion() {
	return apkVersion;
}
public void setApkVersion(String apkVersion) {
	this.apkVersion = apkVersion;
}

public String getLeakinfo() {
	return leakinfo;
}
public void setLeakinfo(String leakinfo) {
	this.leakinfo = leakinfo;
}
public String getDetaiLeakInfo() {
	return detaiLeakInfo;
}
public void setDetaiLeakInfo(String detaiLeakInfo) {
	this.detaiLeakInfo = detaiLeakInfo;
}
public String getStringByMatch(String str,String match){
	return this.getStringByMatch(str, match, 0);
}

public String getStringByMatch(String str,String match,int model){
	Pattern p=Pattern.compile(match,model);
	Matcher m=p.matcher(str);
	if(m.find())
		return m.group(1);
	return null;
}
}
