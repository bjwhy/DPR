package com.why.dpr.distributed.message;

import com.why.dpr.common.DefinedCode;

public class StartTestMessage extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6414380199997707357L;
	private byte func;

	public StartTestMessage() {
		this.setFunc();
	}

	@Override
	public byte getFunc() {
		return func;
	}

	public void setFunc() {
		this.func = DefinedCode.PAK_STARTTEST;
	}
}
