package com.why.dpr.distributed.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class SocketServer {

	private static final Logger logger = LogManager
			.getLogger(SocketServer.class);

	public void bind(int localPort) {

		EventLoopGroup bossGroup = new NioEventLoopGroup(2);
		EventLoopGroup workerGroup = new NioEventLoopGroup(4);

		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
				.childHandler(new SocketServerInitializer());
		try {
			b.bind(localPort).sync();
		} catch (InterruptedException e) {
			logger.warn(e.getMessage());
		}
	}
}