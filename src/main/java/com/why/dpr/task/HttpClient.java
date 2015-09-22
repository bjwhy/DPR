package com.why.dpr.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HttpClient extends AbstractClient {
	private static final Logger logger = LogManager.getLogger(HttpClient.class);

	private Map<String, ArrayList<String[]>> re_map;

	private int threadNums;

	private static final int CONNECT_TIMEOUT = 3000;

	private static final int READ_TIMEOUT = 3000;

	public HttpClient(int threadNums, int runTimes, CountDownLatch doneSignal) {
		this.threadNums = threadNums;
		this.runTimes = runTimes;
		this.doneSignal = doneSignal;
	}

	public HttpClient(int threadNums, int runTimes, CountDownLatch doneSignal,
			CyclicBarrier barrier) {
		this.threadNums = threadNums;
		this.runTimes = runTimes;
		this.doneSignal = doneSignal;
		this.barrier = barrier;
	}

	@Override
	public void init() {
		// TODO write here to init env
		String path = System.getProperty("user.dir") + "/data";

		File root = new File(path);
		File[] files = root.listFiles();

		re_map = new HashMap<String, ArrayList<String[]>>();

		for (File file : files) {
			ArrayList<String[]> file_content = null;
			try {
				file_content = new ArrayList<String[]>();
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String line;
				while ((line = reader.readLine()) != null) {
					String[] keys = line.split("\t");
					if (keys != null)
						file_content.add(keys);
				}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (file_content != null)
				re_map.put(file.getName(), file_content);
		}
	}

	@Override
	public void runOnce() {
		// TODO write what to do and what to verify
		int current_thread = Integer.valueOf(Thread.currentThread().getName());

		for (Entry<String, ArrayList<String[]>> entry : re_map.entrySet()) {
			String file_name = entry.getKey();
			ArrayList<String[]> content = entry.getValue();

			int content_line = content.size();
			int len = content.get(0).length;

			switch (file_name) {
			case "KeywordSearch":
				try {
					StringBuilder request, errMsg;
					for (int i = 0; i < content_line; i++) {
						if (i % threadNums == current_thread) {
							String[] line = content.get(i);

							request = new StringBuilder(128);
							for (int j = 0; j < len; j++) {
								if (j == len - 2)
									request.append(line[j]);
								else {
									request.append(line[j]).append("&");
								}
							}

							String httpUrl = "http://lbsservice.10101111.com/ucarlbsservice/v1/keywordSearch";

							URL u = new URL(
									new String(httpUrl.getBytes("UTF8")));
							HttpURLConnection huc = (HttpURLConnection) u
									.openConnection();

							this.connect(huc);

							String response = this.writeAndGetResponse(huc,
									request.toString());

							if (response != "") {
								String[] expects = line[len - 1].split(",");
								errMsg = new StringBuilder(128);
								for (String expect : expects) {
									if (response.indexOf(expect) < 0) {
										errMsg.append("Missing:")
												.append(expect).append(",");
									}
								}

								if (errMsg.length() > 0) {
									logger.error(
											"request:{0} response:{1} {2}",
											request.insert(0, "?")
													.insert(0, httpUrl)
													.toString(), response,
											errMsg.toString());
								} else {
									logger.info(
											"request:{0} response:{1}",
											request.insert(0, "?")
													.insert(0, httpUrl)
													.toString(), response);
								}
							}
						}

					}
				} catch (Exception e) {
					logger.error("Exception catch:{0}", e.getMessage());
				}
			case "2":
			case "3":
			default:
			}
		}
	}

	public static String streamToString(InputStream in) throws IOException {
		StringBuilder out = new StringBuilder();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1;) {
			out.append(new String(b, 0, n, "utf-8"));
		}
		return out.toString();
	}

	public void connect(HttpURLConnection huc) throws IOException {
		huc.setConnectTimeout(CONNECT_TIMEOUT);
		huc.setReadTimeout(READ_TIMEOUT);
		huc.setRequestMethod("POST");
		huc.setDoOutput(true);
		huc.setDoInput(true);
		huc.setUseCaches(false);

		huc.connect();
	}

	public String writeAndGetResponse(HttpURLConnection huc, String request)
			throws IOException {
		String result = "";

		OutputStream outStream = huc.getOutputStream();
		outStream.write(request.getBytes("UTF8"));
		outStream.flush();
		outStream.close();

		int responseCode = huc.getResponseCode();
		if (huc.getResponseCode() == 200) {
			InputStream inStream = huc.getInputStream();

			result = streamToString(inStream);
			inStream.close();
		} else {
			logger.error("http status wrong:{0}", responseCode);
		}
		return result;
	}
}