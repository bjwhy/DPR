package com.why.dpr.distributed.message;

public class JsonMessage extends Message {

	private String json;

	/**
	 * 
	 */
	private static final long serialVersionUID = -5599048644781651054L;

	@Override
	public void setFunc() {
		// TODO Auto-generated method stub

	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

}
