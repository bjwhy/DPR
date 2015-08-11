package com.why.dpr.distributed.server;

import java.util.concurrent.TimeUnit;

import com.why.dpr.distributed.handler.MsgEncoder;
import com.why.dpr.distributed.handler.ProtocolDecoder;
import com.why.dpr.distributed.handler.SerMsgHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class SocketServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) {
		ChannelPipeline pipeline = ch.pipeline();

		pipeline.addLast(new IdleStateHandler(60, 15, 0, TimeUnit.SECONDS));

		pipeline.addLast(new ProtocolDecoder(128));
		pipeline.addLast(new MsgEncoder());

		pipeline.addLast(new SerMsgHandler());
	}
}