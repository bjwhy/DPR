package com.why.dpr.distributed.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.why.dpr.common.DefinedCode;
import com.why.dpr.distributed.message.HeartBeatMessage;
import com.why.dpr.distributed.message.Message;
import com.why.dpr.distributed.server.CacheManager;
import com.why.dpr.distributed.server.ChannelCache;
import com.why.dpr.distributed.server.ICache;

public class SerMsgHandler extends ChannelInboundHandlerAdapter {
	private CacheManager SM = CacheManager.getInstance();
	private static final Logger logger = LogManager
			.getLogger(SerMsgHandler.class);

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		logger.warn(cause.getMessage());
		ctx.close();
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
			throws Exception {
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent e = (IdleStateEvent) evt;
			if (e.state() == IdleState.WRITER_IDLE) {
				Message hb = new HeartBeatMessage();
				ctx.writeAndFlush(hb);
			} else if (e.state() == IdleState.READER_IDLE) {
				ctx.close();
			}
		}
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		judgeMsg(ctx, msg);
		ReferenceCountUtil.release(msg);
		ctx.fireChannelReadComplete();
	}

	private void judgeMsg(ChannelHandlerContext ctx, Object msg) {
		ByteBuf buf = (ByteBuf) msg;
		byte func = buf.getByte(1);
		if (func == DefinedCode.PAK_CONNECT) {
			ICache session = new ChannelCache();
			session.setObj(ctx.channel());
			SM.addCache(session);
			SM.notifyObservers();
		}
	}
}