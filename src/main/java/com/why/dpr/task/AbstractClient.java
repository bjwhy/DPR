package com.why.dpr.task;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractClient implements IClient {
	private static final Logger logger = LogManager
			.getLogger(AbstractClient.class);
	public int runTimes;
	public CountDownLatch doneSignal;
	public CyclicBarrier barrier;

	@Override
	public void run() {
		if (barrier != null) {
			try {
				barrier.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				logger.warn(e.getMessage());
			}
		}

		if (runTimes > 0) {
			for (int i = 0; i < runTimes; i++) {
				runOnce();
			}
		} else {
			while (true) {
				runOnce();
			}
		}
		doneSignal.countDown();
	}

	/**
	 * 测试用例执行一次
	 */
	public abstract void runOnce();
}