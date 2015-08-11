package com.why.dpr.distributed.server;

public interface ICache {

	public Object getObj();

	public void setObj(Object message);

	public void update();
}