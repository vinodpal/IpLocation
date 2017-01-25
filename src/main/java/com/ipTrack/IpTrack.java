/**
 * 
 */
package com.ipTrack;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
/**
 * @author vinod<vinodpal458@gmail.com
 * 
 */
public class IpTrack {
	private static Logger logger = Logger.getLogger(IpTrack.class);
	private static final String[] HEADERS_TO_TRY = { "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP",
			"HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP",
			"HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR" };

	public static String getClientIpAddress(HttpServletRequest request, String token, String filterLocation) {
	/*	for (String header : HEADERS_TO_TRY) {
			String ip = request.getHeader(header);
			ip = (ip == null) ? request.getHeader(header.toUpperCase()) : null;
			if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
				return ip;
			}
		}*/
		String ipAddress = null;
		try{
			ipAddress = request.getRemoteHost().toString();
		}catch(Exception exception){
			logger.error("Faild Find For IP Remote Host : ", exception);
		}
		if ("0:0:0:0:0:0:0:1".equalsIgnoreCase(ipAddress)) {
			try {
				InetAddress inetAddress = InetAddress.getLocalHost();
				String ip = inetAddress.getHostAddress();
				ipAddress = ip;
			} catch (Exception exception) {
				logger.error("faild location find for ip : ", exception);

			}
		}
		// System.out.println(ipAddress);
		String ipLocation = null;
		String result = null;
		try {
			ipLocation = GetLocation.getCurrentLocation(
					ipAddress/* request.getRemoteHost().toString() */);
		} catch (NullPointerException nullPointerException) {
			logger.error("Null location find for ip : " + ipAddress);
		} catch (Exception exception) {
			logger.error("faild location find for ip : " + ipAddress);
		}
		try {
			IPupdate ipUpdate = new IPupdate();
			result = ipUpdate.getUpdateIpTrack(ipAddress, ipLocation, token, filterLocation);
		} catch (Exception exception) {
			logger.error("faild location find for ip : " + ipAddress);
		}
		if (result != null)
			return result;
		else
			return "unsuccess";

	}

	public static void main(String[] args) {
		String ipAddress = "59.180.241.114";
		String ipLocation = null;
		String result = null;
		try {
			ipLocation = GetLocation.getCurrentLocation(ipAddress);
		} catch (NullPointerException nullPointerException) {
			logger.error("Null location find for ip : " + ipAddress);
		} catch (Exception exception) {
			logger.error("faild location find for ip : " + ipAddress);
		}
		System.out.println("ipLocation : " + ipLocation);

		try {
			IPupdate ipUpdate = new IPupdate();
			result = ipUpdate.getUpdateIpTrack(ipAddress, ipLocation, "083bfeb2-55b9-4f3c-aa5b-0a270829ce1f", "kerla");
		} catch (Exception exception) {
			logger.error("faild location find for ip : " + ipAddress);
		}
		System.out.println(result);

		/*(new IpTrack()).pathTest();*/
		}

	public void pathTest() {
		String catalina_home = System.getenv("CATALINA_HOME");
		ClassLoader classLoader = getClass().getClassLoader();
		String databasePathName = null;
		if (catalina_home.contains("/"))
			databasePathName = "/database/GeoLiteCity.dat";
		else {
			databasePathName = "\\database\\GeoLiteCity.dat";
		}
		File file1 = new File (classLoader.getClass().getResource(databasePathName).getFile());
		
		
		System.out.println(file1.getAbsolutePath());
	}
}
