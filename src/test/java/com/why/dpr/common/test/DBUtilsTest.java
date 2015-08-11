package com.why.dpr.common.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.why.dpr.common.DBUtils;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class DBUtilsTest {
	@Test
	public void getInstanceTest() {
		DBUtils db1 = DBUtils.getInstance();
		DBUtils db2 = DBUtils.getInstance();
		assertEquals(db1, db2);
	}

	@Test
	public void querySingleTest() {
		DBUtils db = DBUtils.getInstance();
		Object[] result = db
				.querySingle("select * from em_version_key where version='3.200'");
		assertEquals(result[2].toString(), "1234567890123456");
	}

	@Test
	public void querylistTest() {
		DBUtils db = DBUtils.getInstance();
		List<Object[]> result = db
				.queryList("select rolename from session_role where sessiontime='1800'");
		List<String> result_list = new ArrayList<String>();
		int result_len = result.size();
		for (int i = 0; i < result_len; i++) {
			result_list.add(result.get(i)[0].toString());
		}
		assertThat(result_list, hasItem("微信"));
		assertThat(result_list, hasItem("专车"));
		assertThat(result_list, hasItem("神州"));
	}
}