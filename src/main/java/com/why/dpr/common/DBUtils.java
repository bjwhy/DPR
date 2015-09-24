package com.why.dpr.common;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

public class DBUtils {

	private static final Logger logger = LogManager.getLogger(DBUtils.class);

	private DruidDataSource ds = null;

	private DBUtils() {
		this.loadPropertyFile();
	}

	/**
	 * 实现加载druid配置文件
	 * 
	 */
	private void loadPropertyFile() {
		InputStream druid = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("druid.properties");
		Properties durid_conf = new Properties();
		try {
			durid_conf.load(druid);
			ds = (DruidDataSource) DruidDataSourceFactory
					.createDataSource(durid_conf);
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}
	}

	/**
	 * 获取DB实例
	 * 
	 * @return DBUtility
	 * 
	 */
	public static DBUtils getInstance() {
		DBUtils db = DBUtilsHolder.instance;
		return db;
	}

	/**
	 * @author bj_why 内部类实现lazy加载,线程安全
	 */
	private static class DBUtilsHolder {
		public static DBUtils instance = new DBUtils();
	}

	/**
	 * 获取多条查询结果
	 * 
	 * @param sql
	 * @throws SQLException
	 * */
	public List<Object[]> queryList(String sql) throws SQLException {
		Connection conn = ds.getConnection();
		QueryRunner queryRunner = new QueryRunner();
		List<Object[]> arrayResult = queryRunner.query(conn, sql,
				new ArrayListHandler());
		conn.close();
		return arrayResult;
	}

	/**
	 * 获取单条查询结果
	 * 
	 * @param sql
	 * @throws SQLException
	 * */
	public Object[] querySingle(String sql) throws SQLException {
		Connection conn = ds.getConnection();
		QueryRunner queryRunner = new QueryRunner();
		Object[] arrayResult = queryRunner.query(conn, sql, new ArrayHandler());
		conn.close();
		return arrayResult;

	}

	/**
	 * 执行无参insert、update、delete操作
	 * 
	 * @param sql
	 * @throws SQLException
	 * */
	public int update(String sql) throws SQLException {
		Connection conn = ds.getConnection();
		QueryRunner queryRunner = new QueryRunner();
		int result = queryRunner.update(conn, sql);
		conn.close();
		return result;
	}

	/**
	 * 执行有参insert、update、delete操作
	 * 
	 * @param sql
	 * @throws SQLException
	 * */
	public int paramUpdate(String sql, Object... params) throws SQLException {
		Connection conn = ds.getConnection();
		QueryRunner queryRunner = new QueryRunner();
		int result = queryRunner.update(conn, sql, params);
		conn.close();
		return result;
	}
}
