package com.qupeng.concurrent.day07.part1;

import org.openjdk.jol.info.ClassLayout;

/**
 * synchronized关键字的底层原理
 * 
 * 演示整个锁的膨胀过程和成为重量级锁后
 * 所有线程退出同步代码区域后，MarkWord是什么锁状态
 * -XX:BiasedLockingStartupDelay=0
 * @author qupeng
 */
public class SynchronizedBottomTest09 {
	

	public static void main(String[] args) throws InterruptedException {
		Thread mainThread = Thread.currentThread();
		mainThread.setName("主线程：");
		final Test testLock=new Test();
		System.out.println("--------------------------所有线程加锁前------------------------------------");
		System.out.println(ClassLayout.parseInstance(testLock).toPrintable());//无锁
		Thread t=new Thread("子线程："){
			
			@Override
			public void run() {
				String threadName = Thread.currentThread().getName();
				synchronized (testLock) {
					System.out.println(threadName+"---------------------------------加锁后----------------------------");
					System.out.println(ClassLayout.parseInstance(testLock).toPrintable());//轻量级锁
					//休眠3秒
					try {Thread.sleep(3_000);} catch (InterruptedException e) {}
				}
				System.out.println(threadName+"---------------------------------释放了锁！----------------------------");
			}
			
		};
		t.start();
		Thread.sleep(1000);
		//让主线程和子线程产生竞争、抢锁
		synchronized (testLock) {
			System.out.println("------------------------------主线程和子线程发生竞争抢锁-------------------------------");
			System.out.println(ClassLayout.parseInstance(testLock).toPrintable());//重量级锁
		}
		Thread.sleep(5_000);
		System.out.println("---------------------------------主线程和子线程都释放了锁！----------------------------");
		System.out.println(ClassLayout.parseInstance(testLock).toPrintable());//重量级锁
		
	}
	
	
	static class Test{
		
	}
		
}
