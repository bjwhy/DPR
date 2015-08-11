package com.why.dpr.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReportUtils {
	private static final Logger logger = LogManager
			.getLogger(ReportUtils.class);

	private long sumRunTimes;
	private long successRunTimes;
	private long sumSeconds;
	private List<Short> responseTimeList = new ArrayList<Short>(100000);

	public void generate() {
		String filePath = System.getProperty("user.dir") + "/logs";
		File root = new File(filePath);
		File[] files = root.listFiles();
		for (File file : files) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String line;
				String start = null, end = null;
				while ((line = reader.readLine()) != null) {
					String[] line_info = line.split(" ");
					if (start == null) {
						start = line_info[0];
					}
					end = line_info[0];
					this.add(line_info, line_info.length);
				}
				reader.close();
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				Date d1 = sdf.parse(start);
				Date d2 = sdf.parse(end);
				long duration = (d2.getTime() - d1.getTime()) / 1000;
				sumSeconds += duration;
			} catch (IOException | ParseException e) {
				logger.warn(e.getMessage());
			}
		}
		this.calculate();
	}

	public void add(String[] line_info, int len) {
		if (line_info[2].equals("INFO")) {
			successRunTimes++;
			sumRunTimes++;
			responseTimeList.add(Short.valueOf(line_info[len - 1]));
		} else if (line_info[2].equals("ERROR")) {
			sumRunTimes++;
		}
	}

	public void calculate() {
		Collections.sort(responseTimeList);
		int size = responseTimeList.size();
		int size_90 = (int) (0.9 * size);
		int averge, averge_90 = 0;
		long sum = 0;
		for (int i = 0; i < size; i++) {
			sum += responseTimeList.get(i);
			if (i == size_90) {
				averge_90 = (int) (sum / size_90);
			}
		}
		averge = (int) (sum / size);
		logger.info(
				"sumRunTimes:{},successRunTimes:{},minResponseTime:{},maxResponseTime:{},90%AvergeResponseTime:{},100%AvergeResponseTime:{},requestPerSecond:{}",
				sumRunTimes, successRunTimes, responseTimeList.get(0),
				responseTimeList.get(size - 1), averge_90, averge, sumRunTimes
						/ sumSeconds);
	}
}