package com.why.dpr.scenario;

import com.why.dpr.task.IClient;

public abstract class AbstractThreadGroup {
	public ThreadGroupVariable vara;

	public AbstractThreadGroup(ThreadGroupVariable vara) {
		this.vara = vara;
	}

	public ThreadGroupVariable getVara() {
		return vara;
	}

	public abstract void start();

	public abstract void manage_threads(IClient cli);
}