package com.why.dpr.scenario;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.why.dpr.common.TypeUtils;
import com.why.dpr.task.HttpClient;
import com.why.dpr.task.IClient;

public class PerRampUpThreadGroup extends AbstractThreadGroup {
	private static final Logger logger = LogManager
			.getLogger(PerRampUpThreadGroup.class);

	public PerRampUpThreadGroup(ThreadGroupVariable vara) {
		super(vara);
	}

	@Override
	public void start() {
		IClient cli;
		CountDownLatch doneSignal = new CountDownLatch(vara.getThreadsNum());
		CyclicBarrier barrier = new CyclicBarrier(vara.getPerRampUp());
		if (vara.isVirtualIp()) {
			List<String> ipList = TypeUtils.ipToList(vara.getIp());
			cli = new HttpClient(ipList, vara.getRunTimes(), doneSignal,
					barrier);/*
							 * change code here
							 */
		} else {
			String ip = vara.getIp();
			cli = new HttpClient(ip, vara.getRunTimes(), doneSignal, barrier); /*
																				 * change
																				 * code
																				 * here
																				 */
		}
		cli.init();
		manage_threads(cli);
		try {
			doneSignal.await();
		} catch (InterruptedException e) {
			logger.warn(e.getMessage());
		}

	}

	@Override
	/**
	 * 每隔特定时间启动多个线程
	 * @param cli
	 *            业务线程
	 */
	public void manage_threads(IClient cli) {
		int threadsNum = vara.getThreadsNum();
		int rampUpPeriod = vara.getRampUpPeriod();
		int perRampUp = vara.getPerRampUp();

		int rampUpTimes = threadsNum / perRampUp;
		int threadName = 0;
		for (int i = 0; i < rampUpTimes; i++) {
			for (int j = 0; j < perRampUp; j++) {
				new Thread(cli, String.valueOf(threadName)).start();
				threadName++;
			}
			try {
				TimeUnit.MILLISECONDS.sleep(rampUpPeriod);
			} catch (InterruptedException e) {
				logger.warn(e.getMessage());
			}
		}
	}

}
