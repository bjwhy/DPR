package com.why.dpr.task;

public interface IClient extends Runnable {
	/**
	 * 线程启动前初始化配置信息
	 */
	public void init();
}