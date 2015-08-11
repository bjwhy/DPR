package com.why.dpr.common.test;

import java.util.List;

import org.junit.Test;

import com.why.dpr.common.TypeUtils;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TypeUtilsTest {
	@Test
	public void isIpFormatTest() {
		assertTrue(TypeUtils.isIpFormat("192.168.0.1"));
		assertTrue(TypeUtils.isIpFormat("10.10.10.3"));
		assertTrue(TypeUtils.isIpFormat("172.18.16.254"));
		assertFalse(TypeUtils.isIpFormat("0.0"));
		assertFalse(TypeUtils.isIpFormat("0.0.0."));
		assertFalse(TypeUtils.isIpFormat("192.168.266.366"));
	}

	@Test
	public void ipToListTest() {
		List<String> ips = TypeUtils.ipToList("192.168.14.1-192.168.14.30");
		assertEquals(ips.size(), 30);
		assertThat(
				ips,
				hasItems("192.168.14.1", "192.168.14.7", "192.168.14.15",
						"192.168.14.30"));
	}
}