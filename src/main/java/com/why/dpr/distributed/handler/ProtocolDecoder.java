package com.why.dpr.distributed.handler;

import java.util.List;

import com.why.dpr.common.DefinedCode;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class ProtocolDecoder extends ByteToMessageDecoder {
	private final ByteBuf[] delimiter;
	private final int maxFrameLength;

	public ProtocolDecoder(int maxFrameLength) {
		validateMaxFrameLength(maxFrameLength);
		this.delimiter = msgDelimiter();
		validateDelimiter(delimiter);
		this.maxFrameLength = maxFrameLength;
	}

	private static ByteBuf[] msgDelimiter() {
		return new ByteBuf[] { Unpooled.wrappedBuffer(new byte[] {
				DefinedCode.PAK_HEAD, DefinedCode.PAK_TAIL }) };
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) {
		ByteBuf decoded = decode(ctx, in);
		if (decoded != null) {
			out.add(decoded);
		}
	}

	protected ByteBuf decode(ChannelHandlerContext ctx, ByteBuf in) {
		byte startTag = delimiter[0].getByte(0);
		byte endTag = delimiter[0].getByte(1);
		ByteBuf frame;

		int msgSize = in.readableBytes();
		if (msgSize > maxFrameLength) {
			in.skipBytes(msgSize);
			return null;
		}

		int start_po = indexOf(startTag, in);
		int tail_po = indexOf(endTag, in);

		if (start_po != -1 && tail_po != -1) {
			frame = in.readBytes(tail_po + 1);
			return frame;
		} else {
			return null;
		}
	}

	private static int indexOf(byte be, ByteBuf buffer) {
		int length = buffer.writerIndex();
		for (int i = 0; i < length; i++) {
			if (buffer.getByte(i) == be) {
				return i;
			}
		}
		return -1;
	}

	private static void validateMaxFrameLength(int maxFrameLength) {
		if (maxFrameLength <= 0) {
			throw new IllegalArgumentException(
					"maxFrameLength must be a positive integer: "
							+ maxFrameLength);
		}
	}

	private static void validateDelimiter(ByteBuf[] delimiters) {
		if (delimiters == null) {
			throw new NullPointerException("delimiter");
		}
		if (delimiters.length != 1) {
			throw new IllegalArgumentException("invalid delimiter");
		}
		if (delimiters[0].readableBytes() != 2) {
			throw new IllegalArgumentException("invalid delimiter");
		}
	}

}