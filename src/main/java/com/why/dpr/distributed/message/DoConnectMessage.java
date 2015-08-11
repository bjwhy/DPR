package com.why.dpr.distributed.message;

import com.why.dpr.common.DefinedCode;

public class DoConnectMessage extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = -145381507074190309L;
	private byte func;

	public DoConnectMessage() {
		this.setFunc();
	}

	@Override
	public byte getFunc() {
		return func;
	}

	public void setFunc() {
		this.func = DefinedCode.PAK_CONNECT;
	}
}
