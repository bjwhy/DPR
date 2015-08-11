package com.why.dpr.scenario;

public class ThreadGroupController {
	private ThreadGroupVariable vara;

	public ThreadGroupController(ThreadGroupVariable vara) {
		this.vara = vara;
	}

	public ThreadGroupVariable getVara() {
		return vara;
	}

	public void control() {
		int perRampUp = vara.getPerRampUp();

		if (perRampUp != 0) {
			PerRampUpThreadGroup ptg = new PerRampUpThreadGroup(vara);
			ptg.start();
		} else {
			LinearThreadGroup ltg = new LinearThreadGroup(vara);
			ltg.start();
		}
	}
}
