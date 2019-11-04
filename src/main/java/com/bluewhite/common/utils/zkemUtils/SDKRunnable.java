package com.bluewhite.common.utils.zkemUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class SDKRunnable extends Thread {

	private String address;

	public SDKRunnable(String address) {
		this.address = address;
	}

	@Override
	public void run() {
		System.out.println("Thread开始====200秒等待设备实时事件");
		try {
			Thread.sleep(2000);
			ZkemSDKUtils.regEvent(address);
		} catch (InterruptedException e) {
			
		}
	} 



}
