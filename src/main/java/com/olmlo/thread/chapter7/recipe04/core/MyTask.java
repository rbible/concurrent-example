package com.olmlo.thread.chapter7.recipe04.core;

import java.util.concurrent.TimeUnit;

/**
 * Task to check the MyThread and MyThreadFactory classes. It sleeps
 * the thread for two seconds
 *
 */
public class MyTask implements Runnable {

	/**
	 * Main method of the task. It sleeps the thread for two seconds
	 */
	@Override
	public void run() {
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
