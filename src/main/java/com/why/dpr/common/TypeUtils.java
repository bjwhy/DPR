package com.why.dpr.common;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TypeUtils {
	/**
	 * ip address verification
	 * 
	 * @param ip
	 * @return true if is ip address
	 */
	public static boolean isIpFormat(String ip) {
		String regEx = "^((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))$";

		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(ip);
		return mat.find();
	}

	/**
	 * 192.168.0.1-192.168.0.40 to ip_list
	 * 
	 * @param ip
	 * @return list
	 */
	public static List<String> ipToList(String ip) {
		String[] two_ips = ip.split("-");
		String[] first_ips = two_ips[0].split("\\.");
		String[] second_ips = two_ips[1].split("\\.");
		int len = first_ips.length;
		List<String> ipList = new ArrayList<String>();
		StringBuilder ipBuf = new StringBuilder();
		int start = Integer.valueOf(first_ips[len - 1]);
		int end = Integer.valueOf(second_ips[len - 1]);
		for (int i = 0; i < len - 1; i++) {
			ipBuf.append(first_ips[i]).append(".");
		}
		int ip_len = ipBuf.length();
		for (int i = start; i <= end; i++) {
			ipBuf.append(i);
			ipList.add(ipBuf.toString());
			ipBuf.delete(ip_len, ipBuf.length());
		}
		return ipList;
	}
}