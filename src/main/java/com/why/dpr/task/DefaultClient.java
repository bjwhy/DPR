package com.why.dpr.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.net.URLEncoder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.why.dpr.common.VerifyUtils;

public class DefaultClient extends AbstractClient {
	private static final Logger logger = LogManager
			.getLogger(DefaultClient.class);

	private Map<String, ArrayList<String[]>> re_map;

	private List<String> ips;

	private int threadNums;

	private static final int CONNECT_TIMEOUT = 3000;

	private static final int READ_TIMEOUT = 3000;

	public DefaultClient(int threadNums, int runTimes, CountDownLatch doneSignal) {
		this.threadNums = threadNums;
		this.runTimes = runTimes;
		this.doneSignal = doneSignal;
	}

	public DefaultClient(int threadNums, int runTimes,
			CountDownLatch doneSignal, CyclicBarrier barrier) {
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
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(new FileInputStream(file),
								"UTF-8"));
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

		ips = new ArrayList<String>();

		try (FileInputStream in = new FileInputStream(
				System.getProperty("user.dir") + "/resources/ip.txt");
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(in, "UTF-8"));) {

			for (String line = null; (line = reader.readLine()) != null;) {
				if (line.trim().isEmpty()) {
					continue;
				}

				ips.add(line.trim());
			}
		} catch (IOException e) {
		}
	}

	@Override
	public void runOnce() {
		// TODO write what to do and what to verify
		int current_thread = Integer.valueOf(Thread.currentThread().getName());

		for (Entry<String, ArrayList<String[]>> entry : re_map.entrySet()) {
			String file_name = entry.getKey();
			ArrayList<String[]> content = entry.getValue();

			String httpUrl = "";

			for (String ip : ips) {

				switch (file_name) {
				case "KeywordSearch":
					httpUrl = "http://" + ip
							+ "/ucarlbsservice/v1/keywordSearch";
					break;
				case "InverseGeo":
					httpUrl = "http://" + ip
							+ "/ucarlbsservice/v1/inverseGeography";
					break;
				case "BestBoard":
					httpUrl = "http://" + ip + "/bestboard";
					break;
				default:
					httpUrl = "http://" + ip + "/ucarlbsservice/v1/nearSearch";
					break;
				}

				int content_line = content.size();

				try {
					StringBuilder request;
					for (int i = 0; i < content_line; i++) {
						if (i % threadNums == current_thread) {

							String[] line = content.get(i);
							int len = line.length;

							request = new StringBuilder(128);
							for (int j = 0; j < len; j++) {
								String one_param = line[j];

								int firstPosition = one_param.indexOf("=");

								String key = one_param.substring(0,
										firstPosition + 1);
								String value = URLEncoder.encode(one_param
										.substring(firstPosition + 1,
												one_param.length()), "UTF-8");

								if (j == len - 1) {
								} else if (j == len - 2) {
									request.append(key).append(value);
								} else {
									request.append(key).append(value)
											.append("&");
								}
							}

							String httpAll = request.insert(0, "?")
									.insert(0, httpUrl).toString();

							URL u = new URL(new String(
									httpAll.getBytes("UTF-8")));
							HttpURLConnection huc = (HttpURLConnection) u
									.openConnection();

							this.connectGet(huc);

							String response = this.writeAndGetResponse(huc,
									httpAll);

							if (response != "") {
								String[] expects = line[len - 1].split(",");
								this.verify(httpAll, response, expects);
							}
						}

						Thread.sleep(1000);

					}
				} catch (Exception e) {
					logger.error("Exception catch:{}", e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}

	public static String streamToString(InputStream in) throws IOException {

		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(in, "UTF-8"));

		StringBuilder out = new StringBuilder(128);
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			out.append(line);
		}

		return out.toString();
	}

	public void connectPost(HttpURLConnection huc) throws IOException {
		huc.setConnectTimeout(CONNECT_TIMEOUT);
		huc.setReadTimeout(READ_TIMEOUT);
		huc.setRequestMethod("POST");
		huc.setDoOutput(true);
		huc.setDoInput(true);
		huc.setUseCaches(false);
		huc.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");

		huc.connect();
	}

	public void connectGet(HttpURLConnection huc) throws IOException {
		huc.setConnectTimeout(CONNECT_TIMEOUT);
		huc.setReadTimeout(READ_TIMEOUT);
		huc.setRequestMethod("GET");
		huc.setUseCaches(false);

		huc.connect();
	}

	public String writeAndPostResponse(HttpURLConnection huc, String request)
			throws IOException {
		String result = "";

		OutputStream outStream = huc.getOutputStream();
		outStream.write(request.getBytes("UTF-8"));
		outStream.flush();
		outStream.close();

		int responseCode = huc.getResponseCode();
		if (huc.getResponseCode() == 200) {
			InputStream inStream = huc.getInputStream();

			result = streamToString(inStream);
			inStream.close();
			huc.disconnect();
		} else {
			logger.error("request:{} http status wrong:{}", request,
					responseCode);
		}
		return result;
	}

	public String writeAndGetResponse(HttpURLConnection huc, String request)
			throws IOException {
		String result = "";

		int responseCode = huc.getResponseCode();
		if (huc.getResponseCode() == 200) {
			InputStream inStream = huc.getInputStream();

			result = streamToString(inStream);
			inStream.close();
			huc.disconnect();
		} else {
			logger.error("request:{} http status wrong:{}", request,
					responseCode);
		}
		return result;
	}

	public void verify(String httpUrl, String response, String[] expects) {
		StringBuilder errMsg = new StringBuilder(128);
		for (String expect : expects) {
			if (!VerifyUtils.isContains(response, expect)) {
				errMsg.append("Missing:").append(expect).append(",");
			}
		}

		if (errMsg.length() > 0) {
			logger.error("request:{} response:{} {}", httpUrl, response,
					errMsg.toString());
		} else {
			logger.info("request:{} response:{}", httpUrl, response);
		}
	}
}