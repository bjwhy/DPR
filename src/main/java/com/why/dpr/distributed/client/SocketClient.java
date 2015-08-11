package com.why.dpr.distributed.client;

import com.why.dpr.distributed.message.Message;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class SocketClient implements ISocket {
	private ChannelFuture cf;

	@Override
	public void tcpConnect(String serverAdress, int serverPort) {

		EventLoopGroup workerGroup = new NioEventLoopGroup(2);

		Bootstrap bootstrap = new Bootstrap();

		bootstrap.group(workerGroup).channel(NioSocketChannel.class)
				.handler(new SocketClientInitializer());

		cf = bootstrap.connect(serverAdress, serverPort);
		cf.awaitUninterruptibly();
	}

	@Override
	public void sendMsg(Message msg) {
		cf.channel().writeAndFlush(msg);
	}
}