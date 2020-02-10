package com.qupeng.concurrent.day07.part1;

import org.openjdk.jol.info.ClassLayout;

/**
 * synchronized关键字的底层原理
 * 加锁之后不睡眠则：变成了轻量级锁
 * 因为是JVM默认开启了偏向锁延迟开启的开关
 * 
 * 注意：偏向锁：101，的第一个1表示的是这个是可偏向状态，
 * 而不是说它已经是一个偏向锁了，如何辨别呢？
 * 看其他字节是否有存储数据，如果有就是已经是偏向锁了，
 * 如果没有，则还是处于一个可偏向状态的无锁。
 * -XX:BiasedLockingStartupDelay=0  可以设置延迟开启偏向锁的时间
 * @author qupeng
 */
public class SynchronizedBottomTest02 {

	private  int k=0;
	
	private Object myObjLock=new Object();

	public static  void main(String[] args) throws InterruptedException {
		/*
		 * JVM启动需要启动很多我们不知道的线程，比如GC，
		 * 要花费4秒时间，所以延迟开启偏向锁的时间默认为4秒
		 */
//		Thread.sleep(4400);
		SynchronizedBottomTest02 test=new SynchronizedBottomTest02();
		System.out.println("------------------------------加锁之前--------------------------------");
		System.out.println(ClassLayout.parseInstance(test).toPrintable());
		synchronized (test) {
			System.out.println("-------------------------------加锁之后------------------------------");
			System.out.println(ClassLayout.parseInstance(test).toPrintable());
		}
	}

}
