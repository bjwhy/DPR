package com.why.dpr.common.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.why.dpr.common.ReflectUtils;

import java.lang.Comparable;
import java.util.ArrayList;
import java.util.List;

public class ReflectUtilsTest {
	@Test
	public void newInstanceTest() {
		Object value = null;
		try {
			value = ReflectUtils.newInstance("com.why.dpr.common.VerifyUtils");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotNull(value);
	}

	@Test
	public void invokePublicStaticMethodTest() {
		Class<?>[] paramTypes = { Object.class, Object.class };
		Object[] params = { "11111", "11111" };
		Object value = null;
		try {
			value = ReflectUtils.invokePublicStaticMethod(
					"com.why.dpr.common.VerifyUtils", "isEqual", paramTypes,
					params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue((boolean) value);

		Class<?>[] paramTypes1 = { Comparable.class, Comparable.class };
		Object[] params1 = { "11111", "11110" };
		Object value1 = null;
		try {
			value1 = ReflectUtils.invokePublicStaticMethod(
					"com.why.dpr.common.VerifyUtils", "isGreaterThan",
					paramTypes1, params1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue((boolean) value1);

		Class<?>[] paramTypes2 = { Comparable.class, Comparable.class };
		Object[] params2 = { 9999, 11110 };
		Object value2 = null;
		try {
			value2 = ReflectUtils.invokePublicStaticMethod(
					"com.why.dpr.common.VerifyUtils", "isGreaterThan",
					paramTypes2, params2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertFalse((boolean) value2);
	}

	@Test
	public void invokePublicStaticMethodTest1() {
		Class<?>[] paramTypes = { List.class, List.class };
		List<String> a1 = new ArrayList<String>();
		List<String> a2 = new ArrayList<String>();
		a1.add("111111");
		a1.add("123qwe");
		a1.add("cvbnm");
		a1.add("1qaz2wsx");
		a1.add("people");
		a2.add("people");
		a2.add("cvbnm");
		a2.add("123qwe");
		a2.add("1qaz2wsx");
		a2.add("111111");
		Object[] params = { a1, a2 };
		Object value = null;
		try {
			value = ReflectUtils.invokePublicStaticMethod(
					"com.why.dpr.common.VerifyUtils", "isListEqual",
					paramTypes, params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue((boolean) value);

		Class<?>[] paramTypes1 = { String.class, String.class };
		Object[] params1 = { "11111dafshgrejndferfqr325", "11111" };
		Object value1 = null;
		try {
			value1 = ReflectUtils.invokePublicStaticMethod(
					"com.why.dpr.common.VerifyUtils", "isStartsWith",
					paramTypes1, params1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue((boolean) value1);

		Class<?>[] paramTypes2 = { String.class, String.class };
		Object[] params2 = { "123qweasdzxc", "qwe" };
		Object value2 = null;
		try {
			value2 = ReflectUtils.invokePublicStaticMethod(
					"com.why.dpr.common.VerifyUtils", "isContains",
					paramTypes2, params2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue((boolean) value2);
	}
}