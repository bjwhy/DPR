package com.why.dpr.scenario;

public class ThreadGroupVariable {
	private int threadsNum;
	private int rampUpPeriod;
	private int runTimes;
	private int perRampUp;

	public static ThreadGroupVariable getInstance() {
		return ThreadGroupVariableHolder.instance;
	}

	private static class ThreadGroupVariableHolder {
		static ThreadGroupVariable instance = new ThreadGroupVariable();
	}

	public int getThreadsNum() {
		return threadsNum;
	}

	public void setThreadsNum(int threadsNum) {
		this.threadsNum = threadsNum;
	}

	public int getRampUpPeriod() {
		return rampUpPeriod;
	}

	public void setRampUpPeriod(int rampUpPeriod) {
		this.rampUpPeriod = rampUpPeriod;
	}

	public int getRunTimes() {
		return runTimes;
	}

	public void setRunTimes(int runTimes) {
		this.runTimes = runTimes;
	}

	public int getPerRampUp() {
		return perRampUp;
	}

	public void setPerRampUp(int perRampUp) {
		this.perRampUp = perRampUp;
	}
}