package com.why.dpr.common;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.*;

public class VerifyUtils {
	private static <T> Boolean assertThat(T actual, Matcher<? super T> matcher) {
		if (matcher.matches(actual)) {
			return true;
		}
		return false;
	}

	public static boolean isEqual(Object response, Object expected) {
		return assertThat(response, equalTo(expected));
	}

	public static <T extends java.lang.Comparable<T>> boolean isGreaterThan(
			T response, T expected) {
		return assertThat(response, greaterThan(expected));
	}

	public static <T extends java.lang.Comparable<T>> boolean isGreaterThanOrEqualTo(
			T response, T expected) {
		return assertThat(response, greaterThanOrEqualTo(expected));
	}

	public static <T extends java.lang.Comparable<T>> boolean isLessThan(
			T response, T expected) {
		return assertThat(response, lessThan(expected));
	}

	public static <T extends java.lang.Comparable<T>> boolean isLessThanOrEqualTo(
			T response, T expected) {
		return assertThat(response, lessThanOrEqualTo(expected));
	}

	public static <T extends java.lang.Comparable<T>> boolean isListEqual(
			List<T> response, List<T> expected) {
		Collections.sort(response);
		Collections.sort(expected);
		int value_len = response.size();
		int expect_len = expected.size();
		if (value_len != expect_len) {
			return false;
		} else {
			for (int i = 0; i < value_len; i++) {
				boolean result = response.get(i).equals(expected.get(i)) ? true
						: false;
				if (!result) {
					return false;
				}
			}
			return true;
		}
	}

	public static <T extends java.lang.Comparable<T>> boolean isArrayEqual(
			T[] response, T[] expected) {
		Arrays.sort(response);
		Arrays.sort(expected);
		int value_len = response.length;
		int expect_len = expected.length;
		if (value_len != expect_len) {
			return false;
		} else {
			for (int i = 0; i < value_len; i++) {
				boolean result = response[i].equals(expected[i]) ? true : false;
				if (!result) {
					return false;
				}
			}
			return true;
		}
	}

	@SafeVarargs
	public static <T> boolean isListHasItems(List<T> response, T... expected) {
		return assertThat(response, hasItems(expected));
	}

	public static boolean isNull(Object response) {
		return assertThat(response, nullValue());
	}

	public static boolean isStartsWith(String response, String expected) {
		return assertThat(response, startsWith(expected));
	}

	public static boolean isListAllStartsWith(List<String> response,
			String expected) {
		return assertThat(response, everyItem(startsWith(expected)));
	}

	public static boolean isEndsWith(String response, String expected) {
		return assertThat(response, endsWith(expected));
	}

	public static boolean isListAllEndsWith(List<String> response,
			String expected) {
		return assertThat(response, everyItem(endsWith(expected)));
	}

	public static boolean isContains(String response, String expected) {
		return response.indexOf(expected) == -1 ? false : true;
	}

	public static boolean isSubString(String response, String expected) {
		return expected.indexOf(response) == -1 ? false : true;
	}
}