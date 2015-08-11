package com.why.dpr.run;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.why.dpr.common.TypeUtils;
import com.why.dpr.distributed.client.ISocket;
import com.why.dpr.distributed.client.SocketClient;
import com.why.dpr.distributed.server.CacheManager;
import com.why.dpr.distributed.server.SocketServer;

public class Index {
	private static final Logger logger = LogManager.getLogger(Index.class);

	public static void main(String[] args) {
		Properties prop = new Properties();
		String filePath = System.getProperty("user.dir")
				+ "/resources/distributed.properties";
		InputStream in;
		try {
			in = new BufferedInputStream(new FileInputStream(filePath));
			prop.load(in);
		} catch (IOException e) {
			logger.warn(e.getMessage());
			return;
		}
		int port = Integer.valueOf(prop.getProperty("distributed.port"));
		String type = prop.getProperty("distributed.type");
		int num = Integer.valueOf(prop.getProperty("distributed.num"));

		if (type.equals("server")) {
			if (num == 0) {
				EachDistribution test = new EachDistribution();
				new Thread(test).start();
			} else {
				CacheManager.cacheSize = num;
				SocketServer server = new SocketServer();
				server.bind(port);
			}
		} else if (type.equals("client")) {
			String ip = prop.getProperty("distributed.ip");
			if (!TypeUtils.isIpFormat(ip)) {
				logger.warn("ip format invalid,modify the config");
				return;
			}
			ISocket client = new SocketClient();
			client.tcpConnect(ip, port);
		} else {
			logger.warn("type is wrong,shuold be server or client");
			return;
		}
	}
}