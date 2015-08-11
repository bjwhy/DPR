package com.why.dpr.distributed.client;

import com.why.dpr.distributed.handler.CliMsgHandler;
import com.why.dpr.distributed.handler.MsgEncoder;
import com.why.dpr.distributed.handler.ProtocolDecoder;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class SocketClientInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new ProtocolDecoder(128));
		pipeline.addLast(new MsgEncoder());

		pipeline.addLast(new CliMsgHandler());
	}
}
