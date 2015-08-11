package com.why.dpr.distributed.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.why.dpr.distributed.message.DoConnectMessage;
import com.why.dpr.distributed.message.HeartBeatMessage;
import com.why.dpr.distributed.message.Message;
import com.why.dpr.run.EachDistribution;
import com.why.dpr.common.DefinedCode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class CliMsgHandler extends ChannelInboundHandlerAdapter {
	private static final Logger logger = LogManager
			.getLogger(CliMsgHandler.class);

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		logger.warn(cause.getMessage());
		ctx.close();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		Message conn = new DoConnectMessage();
		ctx.writeAndFlush(conn);
	};

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		judgeMsg(ctx, msg);
		ReferenceCountUtil.release(msg);
		ctx.fireChannelReadComplete();
	}

	private void judgeMsg(ChannelHandlerContext ctx, Object msg) {
		ByteBuf buf = (ByteBuf) msg;
		byte func = buf.getByte(1);
		if (func == DefinedCode.PAK_HEARTBEAT) {
			Message hb = new HeartBeatMessage();
			ctx.writeAndFlush(hb);
		} else if (func == DefinedCode.PAK_STARTTEST) {
			EachDistribution test = new EachDistribution();
			new Thread(test).start();
		}
	}
}