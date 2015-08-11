package com.why.dpr.common.test;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.why.dpr.common.VerifyUtils;

import static org.junit.Assert.*;

public class VerifyUtilsTest {
	@Test
	public void isEqualTest() {
		assertTrue(VerifyUtils.isEqual("北京", "北京"));
		assertTrue(VerifyUtils.isEqual(111, 111));
		assertTrue(VerifyUtils.isEqual(22.2, 22.2));
		assertTrue(VerifyUtils.isEqual(true, true));
		assertFalse(VerifyUtils.isEqual("北京1", "北京"));
		assertFalse(VerifyUtils.isEqual(22.2, 22.22));
		assertFalse(VerifyUtils.isEqual(true, false));
	}

	@Test
	public void isListEqualTest() {
		List<String> li1 = Arrays.asList("why", "what", "who", "when", "where",
				"how", "is there");
		List<String> li2 = Arrays.asList("where", "what", "who", "when", "why",
				"how", "is there");
		List<String> li3 = Arrays.asList("where", "what", "who", "when", "why",
				"how");
		List<Integer> li4 = Arrays.asList(11, 22, 33, 44, 55, 66, 77);
		List<Integer> li5 = Arrays.asList(11, 22, 33, 44, 55, 66, 77);
		List<Integer> li6 = Arrays.asList(11, 22, 33, 44, 55, 66, 88);
		List<Boolean> li7 = Arrays.asList(true, true, true, true, true, true,
				false);
		List<Boolean> li8 = Arrays.asList(true, true, true, true, true, true,
				false);
		List<Boolean> li9 = Arrays.asList(true, true, true, true, true, false,
				false);
		assertTrue(VerifyUtils.isListEqual(li1, li2));
		assertTrue(VerifyUtils.isListEqual(li4, li5));
		assertTrue(VerifyUtils.isListEqual(li7, li8));
		assertFalse(VerifyUtils.isListEqual(li1, li3));
		assertFalse(VerifyUtils.isListEqual(li4, li6));
		assertFalse(VerifyUtils.isListEqual(li7, li9));
	}

	@Test
	public void isArrayEqualTest() {
		String[] li1 = { "why", "what", "where", "when", "who", "" };
		String[] li2 = { "why", "what", "where", "when", "", "who" };
		String[] li3 = { "why", "what", "where", "when", "how", "who" };
		Integer[] li4 = { 11, 22, 33, 44, 55 };
		Integer[] li5 = { 11, 22, 55, 44, 33 };
		Integer[] li6 = { 11, 22, 33, 44, 66 };
		Boolean[] li7 = { true, false, false, false, false };
		Boolean[] li8 = { false, false, true, false, false };
		Boolean[] li9 = { true, false, false, false };
		assertTrue(VerifyUtils.isArrayEqual(li1, li2));
		assertTrue(VerifyUtils.isArrayEqual(li4, li5));
		assertTrue(VerifyUtils.isArrayEqual(li7, li8));
		assertFalse(VerifyUtils.isArrayEqual(li1, li3));
		assertFalse(VerifyUtils.isArrayEqual(li4, li6));
		assertFalse(VerifyUtils.isArrayEqual(li7, li9));
	}

	@Test
	public void isGreaterThanTest() {
		assertTrue(VerifyUtils.isGreaterThan(21, 20));
		assertTrue(VerifyUtils.isGreaterThan(40, -40));
		assertTrue(VerifyUtils.isGreaterThan("SSS", "SSA"));
		assertFalse(VerifyUtils.isGreaterThan(20, 90));
		assertFalse(VerifyUtils.isGreaterThan(90, 90));
		assertFalse(VerifyUtils.isGreaterThan("java", "javz"));
	}

	@Test
	public void isGreaterThanOrEqualToTest() {
		assertTrue(VerifyUtils.isGreaterThanOrEqualTo(21, 20));
		assertTrue(VerifyUtils.isGreaterThanOrEqualTo(40, 40));
		assertTrue(VerifyUtils.isGreaterThanOrEqualTo("SSS", "SSA"));
		assertFalse(VerifyUtils.isGreaterThanOrEqualTo(20, 90));
		assertFalse(VerifyUtils.isGreaterThanOrEqualTo(-20, 90));
		assertFalse(VerifyUtils.isGreaterThanOrEqualTo("java", "javz"));
	}

	@Test
	public void isLessThanTest() {
		assertFalse(VerifyUtils.isLessThan(21, 20));
		assertFalse(VerifyUtils.isLessThan(40, 40));
		assertFalse(VerifyUtils.isLessThan("SSS", "SSA"));
		assertTrue(VerifyUtils.isLessThan(20, 90));
		assertTrue(VerifyUtils.isLessThan(-20, 90));
		assertTrue(VerifyUtils.isLessThan("java", "javz"));
	}

	@Test
	public void isLessThanOrEqualToTest() {
		assertTrue(VerifyUtils.isLessThanOrEqualTo(20, 21));
		assertTrue(VerifyUtils.isLessThanOrEqualTo(40, 40));
		assertTrue(VerifyUtils.isLessThanOrEqualTo("SSA", "SSS"));
		assertFalse(VerifyUtils.isLessThanOrEqualTo(90, -90));
		assertFalse(VerifyUtils.isLessThanOrEqualTo(90, 30));
		assertFalse(VerifyUtils.isLessThanOrEqualTo("javz", "java"));
	}

	@Test
	public void isListHasItemsTest() {
		List<String> li1 = Arrays.asList("why", "what", "who", "when", "where",
				"how", "is there");
		List<Integer> li4 = Arrays.asList(11, 22, 33, 44, 55, 66, 77);
		List<Boolean> li7 = Arrays.asList(true, true, true, true, true, true,
				true);
		assertTrue(VerifyUtils.isListHasItems(li1, "who", "when"));
		assertTrue(VerifyUtils.isListHasItems(li4, 22));
		assertTrue(VerifyUtils.isListHasItems(li7, true));
		assertFalse(VerifyUtils.isListHasItems(li1, "www"));
		assertFalse(VerifyUtils.isListHasItems(li4, 45));
		assertFalse(VerifyUtils.isListHasItems(li7, false));
	}

	@Test
	public void isNullTest() {
		assertTrue(VerifyUtils.isNull(null));
		assertFalse(VerifyUtils.isNull(""));
		assertFalse(VerifyUtils.isNull(22));
		assertFalse(VerifyUtils.isNull(true));
	}

	@Test
	public void isStartsWithTest() {
		assertTrue(VerifyUtils.isStartsWith("addffasfa", "add"));
		assertTrue(VerifyUtils.isStartsWith("addffasfa", "addff"));
		assertFalse(VerifyUtils.isStartsWith("fddffasfa", "addff"));
		assertFalse(VerifyUtils.isStartsWith("fddffasfa", "addfff"));
	}

	@Test
	public void isListAllStartsWithTest() {
		List<String> li1 = Arrays.asList("why", "what", "who", "when", "where");
		assertTrue(VerifyUtils.isListAllStartsWith(li1, "wh"));
		assertTrue(VerifyUtils.isListAllStartsWith(li1, "w"));
		assertFalse(VerifyUtils.isListAllStartsWith(li1, "addff"));
		assertFalse(VerifyUtils.isListAllStartsWith(li1, "wa"));
	}

	@Test
	public void isEndsWithTest() {
		assertTrue(VerifyUtils.isEndsWith("addffasfa", "fa"));
		assertTrue(VerifyUtils.isEndsWith("addffasfa", "asfa"));
		assertFalse(VerifyUtils.isEndsWith("fddffasfa", "addff"));
		assertFalse(VerifyUtils.isEndsWith("fddffasfa", "bb"));
	}

	@Test
	public void isListAllEndsWithTest() {
		List<String> li1 = Arrays.asList("whye", "whate", "whoe", "whene",
				"wheree");
		assertTrue(VerifyUtils.isListAllEndsWith(li1, "e"));
		assertFalse(VerifyUtils.isListAllEndsWith(li1, "addff"));
		assertFalse(VerifyUtils.isListAllEndsWith(li1, "wa"));
	}

	@Test
	public void isContainsTest() {
		assertTrue(VerifyUtils.isContains("qwe isError:false want more",
				"isError:false"));
		assertTrue(VerifyUtils
				.isContains("qwe isError:false want more", "more"));
		assertFalse(VerifyUtils.isContains("qwe isError:false want more",
				"isError:true"));
		assertFalse(VerifyUtils
				.isContains("qwe isError:false want more 0", "1"));
	}

	@Test
	public void isSubStringTest() {
		assertTrue(VerifyUtils.isSubString("beijing", "city:beijing"));
		assertTrue(VerifyUtils.isSubString("date:2014-06-20",
				"city:beijing date:2014-06-20"));
		assertFalse(VerifyUtils.isSubString("bj", "city:beijing"));
		assertFalse(VerifyUtils.isSubString("eee", "city:beijing"));
	}
}