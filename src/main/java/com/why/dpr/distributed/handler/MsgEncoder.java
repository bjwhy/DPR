package com.why.dpr.distributed.handler;

import com.why.dpr.distributed.message.Message;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MsgEncoder extends MessageToByteEncoder<Message> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out)
			throws Exception {
		out.writeByte(msg.getHead());
		out.writeByte(msg.getFunc());
		out.writeByte(msg.getTail());
	}
}