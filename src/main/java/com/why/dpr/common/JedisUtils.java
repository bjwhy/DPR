package com.why.dpr.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtils {

	private JedisPool pool = null;
	private static final Logger logger = LogManager.getLogger(DBUtils.class);

	private JedisUtils() {
		this.loadPropertyFile();
	}

	/**
	 * 实现加载jedis配置文件
	 * 
	 */
	private void loadPropertyFile() {
		InputStream jedis = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("jedis.properties");
		Properties jedis_conf = new Properties();
		try {
			jedis_conf.load(jedis);
		} catch (IOException e) {
			logger.warn(e.getMessage());
		}
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(Integer.valueOf(jedis_conf
				.getProperty("jedis.pool.maxActive")));
		config.setMaxIdle(Integer.valueOf(jedis_conf
				.getProperty("jedis.pool.maxIdle")));
		config.setMinIdle(Integer.valueOf(jedis_conf
				.getProperty("jedis.pool.minIdle")));
		config.setMaxWaitMillis(Integer.valueOf(jedis_conf
				.getProperty("jedis.pool.maxWait")));
		config.setTestOnBorrow(Boolean.valueOf(jedis_conf
				.getProperty("jedis.pool.testOnBorrow")));
		config.setTestOnReturn(Boolean.valueOf(jedis_conf
				.getProperty("jedis.pool.testOnReturn")));
		config.setTimeBetweenEvictionRunsMillis(Integer.valueOf(jedis_conf
				.getProperty("jedis.pool.timeBetweenEvictionRunsMillis")));
		config.setMinEvictableIdleTimeMillis(Integer.valueOf(jedis_conf
				.getProperty("jedis.pool.minEvictableIdleTimeMillis")));
		config.setTestWhileIdle(Boolean.valueOf(jedis_conf
				.getProperty("jedis.pool.testWhileIdle")));
		pool = new JedisPool(config, jedis_conf.getProperty("redis.ip"),
				Integer.valueOf(jedis_conf.getProperty("redis.port")));
	}

	/**
	 * 获取JedisUtils实例
	 * 
	 * @return JedisUtils
	 */
	public static JedisUtils getInstance() {
		JedisUtils jd = JedisUtilsHolder.instance;
		return jd;
	}

	/**
	 * @author bj_why 内部类实现lazy加载,线程安全
	 */
	private static class JedisUtilsHolder {
		public static JedisUtils instance = new JedisUtils();
	}

	/**
	 * 从连接池获取jedis实例
	 * 
	 * @return Jedis
	 */
	public Jedis getJedisFromPool() {
		return pool.getResource();
	}

	/**
	 * jedis实例放回连接池
	 * 
	 * @param resource
	 */
	public void getJedisBackToPool(Jedis resource) {
		resource.close();
	}
}
