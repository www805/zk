package com.avst.zk.common.util;

import java.net.InetAddress;

/**
 * 授权已经到了第三阶段 使用CPU序列号+C盘序列号
 */
public class NetTool {

	//取得LOCALHOST的IP地址
	public static String getMyIP() {
		InetAddress myIPaddress=null;
		try {
			myIPaddress=InetAddress.getLocalHost();
		}
		catch (Exception e) {}
		return (myIPaddress.getHostAddress());
	}
}  