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
		Object value = ReflectUtils
				.newInstance("com.why.dpr.common.VerifyUtils");
		assertNotNull(value);
	}

	@Test
	public void invokePublicStaticMethodTest() {
		Class<?>[] paramTypes = { Object.class, Object.class };
		Object[] params = { "11111", "11111" };
		Object value = ReflectUtils
				.invokePublicStaticMethod("com.why.dpr.common.VerifyUtils",
						"isEqual", paramTypes, params);
		assertTrue((boolean) value);

		Class<?>[] paramTypes1 = { Comparable.class, Comparable.class };
		Object[] params1 = { "11111", "11110" };
		Object value1 = ReflectUtils.invokePublicStaticMethod(
				"com.why.dpr.common.VerifyUtils", "isGreaterThan", paramTypes1,
				params1);
		assertTrue((boolean) value1);

		Class<?>[] paramTypes2 = { Comparable.class, Comparable.class };
		Object[] params2 = { 9999, 11110 };
		Object value2 = ReflectUtils.invokePublicStaticMethod(
				"com.why.dpr.common.VerifyUtils", "isGreaterThan", paramTypes2,
				params2);
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
		Object value = ReflectUtils.invokePublicStaticMethod(
				"com.why.dpr.common.VerifyUtils", "isListEqual", paramTypes,
				params);
		assertTrue((boolean) value);

		Class<?>[] paramTypes1 = { String.class, String.class };
		Object[] params1 = { "11111dafshgrejndferfqr325", "11111" };
		Object value1 = ReflectUtils.invokePublicStaticMethod(
				"com.why.dpr.common.VerifyUtils", "isStartsWith", paramTypes1,
				params1);
		assertTrue((boolean) value1);

		Class<?>[] paramTypes2 = { String.class, String.class };
		Object[] params2 = { "123qweasdzxc", "qwe" };
		Object value2 = ReflectUtils.invokePublicStaticMethod(
				"com.why.dpr.common.VerifyUtils", "isContains", paramTypes2,
				params2);
		assertTrue((boolean) value2);
	}
}