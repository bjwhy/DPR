package com.why.dpr.distributed.message;

import java.io.Serializable;

import com.why.dpr.common.DefinedCode;

public abstract class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2773561761822042251L;

	private byte head = DefinedCode.PAK_HEAD;
	private byte tail = DefinedCode.PAK_TAIL;
	private byte func;

	public byte getHead() {
		return this.head;
	};

	public byte getTail() {
		return this.tail;
	};

	public byte getFunc() {
		return this.func;
	}

	public abstract void setFunc();
}
