package com.xwgoss;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.xwgoss.util.MatchConfig;

import junit.framework.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LeakcancaryReportApplicationTests {
	@Autowired
	private MatchConfig mc;
	@Test
	public void contextLoads() {
		Assert.assertEquals("com.xwgoss.leakcanarytest", mc.getStringByMatch("In com.xwgoss.leakcanarytest:1.0:1. ", mc.getPackageNameMatch()));
		Assert.assertEquals("Xiaomi Xiaomi MI MAX hydrogen", mc.getStringByMatch("* Device: Xiaomi Xiaomi MI MAX hydrogen ", mc.getDeviceNameMatch()));
		Assert.assertEquals("1.0:1", mc.getStringByMatch("In com.xwgoss.leakcanarytest:1.0:1. ", mc.getApkVersion()));
		Assert.assertEquals("6.0.1 API: 23", mc.getStringByMatch("Android Version: 6.0.1 API: 23 LeakCanary: 1.5 00f37f5 ", mc.getAndroidVersionMatch()));
		Assert.assertNotNull(mc.getStringByMatch("* Details: \r\n"+
"* Class com.xwgoss.leakcanarytest.ActivityManager", mc.getDetaiLeakInfo()));
	}

}
