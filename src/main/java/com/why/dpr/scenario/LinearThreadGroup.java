package com.why.dpr.scenario;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.why.dpr.common.TypeUtils;
import com.why.dpr.task.HttpClient;
import com.why.dpr.task.IClient;

public class LinearThreadGroup extends AbstractThreadGroup {

	private static final Logger logger = LogManager
			.getLogger(LinearThreadGroup.class);

	public LinearThreadGroup(ThreadGroupVariable vara) {
		super(vara);
	}

	@Override
	public void start() {
		IClient cli;
		CountDownLatch doneSignal = new CountDownLatch(vara.getThreadsNum());
		if (vara.isVirtualIp()) {
			List<String> ipList = TypeUtils.ipToList(vara.getIp());
			cli = new HttpClient(ipList, vara.getRunTimes(), doneSignal); /*
																		 * change
																		 * code
																		 * here
																		 */
		} else {
			String ip = vara.getIp();
			cli = new HttpClient(ip, vara.getRunTimes(), doneSignal); /*
																	 * change
																	 * code here
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
	 * 每个线程每隔特定时间启动
	 * @param cli
	 *            业务线程
	 */
	public void manage_threads(IClient cli) {
		int threadsNum = vara.getThreadsNum();
		int rampUpPeriod = vara.getRampUpPeriod();

		int perTthreadDelay = Math
				.round(((float) (rampUpPeriod * 1000) / (float) threadsNum));
		for (int i = 0; i < threadsNum; i++) {
			new Thread(cli, String.valueOf(i)).start();
			try {
				TimeUnit.MILLISECONDS.sleep(perTthreadDelay);
			} catch (InterruptedException e) {
				logger.warn(e.getMessage());
			}
		}
	}

}
