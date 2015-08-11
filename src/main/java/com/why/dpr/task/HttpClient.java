package com.why.dpr.task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HttpClient extends AbstractClient {
	private static final Logger logger = LogManager.getLogger(HttpClient.class);
	private String ip;
	private List<String> ipList;
	private int ipLen;
	private static final int CONNECT_TIMEOUT = 3000;

	private static final int READ_TIMEOUT = 3000;

	public HttpClient(String ip, int runTimes, CountDownLatch doneSignal) {
		this.ip = ip;
		this.runTimes = runTimes;
		this.doneSignal = doneSignal;
	}

	public HttpClient(List<String> ipList, int runTimes,
			CountDownLatch doneSignal) {
		this.ipList = ipList;
		this.runTimes = runTimes;
		this.doneSignal = doneSignal;
		this.ipLen = ipList.size();
	}

	public HttpClient(String ip, int runTimes, CountDownLatch doneSignal,
			CyclicBarrier barrier) {
		this.ip = ip;
		this.runTimes = runTimes;
		this.doneSignal = doneSignal;
		this.barrier = barrier;
	}

	public HttpClient(List<String> ipList, int runTimes,
			CountDownLatch doneSignal, CyclicBarrier barrier) {
		this.ipList = ipList;
		this.runTimes = runTimes;
		this.doneSignal = doneSignal;
		this.ipLen = ipList.size();
		this.barrier = barrier;
	}

	@Override
	public void init() {
		// TODO write here to init env
	}

	@Override
	public void runOnce() {
		// TODO write what to do and what to verify

		String result = "";
		try {
			URL u = new URL(new String("".getBytes("UTF8")));
			HttpURLConnection huc = (HttpURLConnection) u.openConnection();
			huc.setConnectTimeout(CONNECT_TIMEOUT);
			huc.setReadTimeout(READ_TIMEOUT);
			huc.setRequestMethod("POST");
			huc.setDoOutput(true);
			huc.setDoInput(true);
			huc.setUseCaches(false);
			if (ipList == null) {
				huc.setRequestProperty("X-Forwarded-For", ip);
				huc.setRequestProperty("HTTP-Client-IP", ip);
			} else {
				int thread = Integer.valueOf(Thread.currentThread().getName());
				huc.setRequestProperty("X-Forwarded-For",
						ipList.get((thread % ipLen)));
				huc.setRequestProperty("HTTP-Client-IP",
						ipList.get((thread % ipLen)));
			}
			huc.connect();

			long start = System.currentTimeMillis();
			OutputStream outStream = huc.getOutputStream();
			outStream.write("".getBytes());
			outStream.flush();
			outStream.close();

			int responseCode = huc.getResponseCode();
			if (huc.getResponseCode() == 200) {
				InputStream inStream = huc.getInputStream();
				long end = System.currentTimeMillis();
				result = streamToString(inStream);
				logger.info("{0}--costTime:{1}", result, end - start);
			} else {
				logger.error("http status wrong:{0}", responseCode);
			}
		} catch (Exception e) {
			logger.error("Exception catch:{}", e.getMessage());
		}

	}

	public static String streamToString(InputStream in) throws IOException {
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1;) {
			out.append(new String(b, 0, n, "utf-8"));
		}
		return out.toString();
	}
}