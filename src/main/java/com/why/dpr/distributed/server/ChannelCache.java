package com.why.dpr.distributed.server;

import com.why.dpr.distributed.message.Message;
import com.why.dpr.distributed.message.StartTestMessage;

import io.netty.channel.Channel;

public class ChannelCache implements ICache {
	private Channel obj;

	@Override
	public Channel getObj() {
		return obj;
	}

	@Override
	public void setObj(Object ctx) {
		obj = (Channel) ctx;
	}

	public void update() {
		Message st = new StartTestMessage();
		obj.writeAndFlush(st);
	}
}