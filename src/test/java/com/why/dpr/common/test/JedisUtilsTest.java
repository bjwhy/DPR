package com.why.dpr.common.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.why.dpr.common.JedisUtils;

public class JedisUtilsTest {
	@Test
	public void getInstanceTest() {
		JedisUtils jd1 = JedisUtils.getInstance();
		JedisUtils jd2 = JedisUtils.getInstance();
		assertEquals(jd1, jd2);
	}

	@Test
	public void getJedisFromPoolTest() {
		JedisUtils jd1 = JedisUtils.getInstance();
		assertNotNull(jd1.getJedisFromPool());
		assertNotNull(jd1.getJedisFromPool().get(
				"ucarsummary4java_42407018"));
	}
}