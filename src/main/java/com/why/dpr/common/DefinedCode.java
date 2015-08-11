package com.why.dpr.common;

public class DefinedCode {

	/**
	 * 包头
	 */
	public static final byte PAK_HEAD = (byte) 0x81;

	/**
	 * 包尾
	 */
	public static final byte PAK_TAIL = (byte) 0x84;

	/**
	 * 心跳包
	 */
	public static final byte PAK_HEARTBEAT = (byte) 0x80;

	/**
	 * 建链包
	 */
	public static final byte PAK_CONNECT = (byte) 0x41;

	/**
	 * 开始测试包
	 */
	public static final byte PAK_STARTTEST = (byte) 0x82;
}
