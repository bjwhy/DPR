package com.why.dpr.distributed.message;

import com.why.dpr.common.DefinedCode;

public class HeartBeatMessage extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6527399234371712211L;
	private byte func;

	public HeartBeatMessage() {
		this.setFunc();
	}

	@Override
	public byte getFunc() {
		return func;
	}

	public void setFunc() {
		this.func = DefinedCode.PAK_HEARTBEAT;
	}
}
