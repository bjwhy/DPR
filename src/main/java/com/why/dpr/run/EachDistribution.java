package com.why.dpr.run;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.why.dpr.scenario.ThreadGroupController;
import com.why.dpr.scenario.ThreadGroupVariable;

public class EachDistribution implements Runnable {

	private static final Logger logger = LogManager
			.getLogger(EachDistribution.class);

	public void run() {
		Properties prop = new Properties();
		String filePath = System.getProperty("user.dir")
				+ "/resources/scenario.properties";

		try {
			InputStream in = new BufferedInputStream(new FileInputStream(
					filePath));
			prop.load(in);
			in.close();
		} catch (IOException e) {
			logger.warn(e.getMessage());
		}

		int threadsNum = Integer.valueOf(prop
				.getProperty("scenario.threadsNum"));
		int rampUpPeriod = Integer.valueOf(prop
				.getProperty("scenario.rampUpPeriod"));
		int runTimes = Integer.valueOf(prop.getProperty("scenario.runTimes"));
		int perRampUp = Integer.valueOf(prop.getProperty("scenario.perRampUp"));
		ThreadGroupVariable vara = ThreadGroupVariable.getInstance();
		vara.setRampUpPeriod(rampUpPeriod);
		vara.setRunTimes(runTimes);
		vara.setThreadsNum(threadsNum);
		vara.setPerRampUp(perRampUp);
		ThreadGroupController tgc = new ThreadGroupController(vara);
		tgc.control();
	}
}