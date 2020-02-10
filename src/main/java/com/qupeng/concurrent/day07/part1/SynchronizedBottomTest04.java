package com.qupeng.concurrent.day07.part1;

import org.openjdk.jol.info.ClassLayout;

/**
 * synchronized关键字的底层原理
 * 
 *  -XX:BiasedLockingStartupDelay=0
 * 演示计算了hashCode的对象不能成为偏向锁
 * 会直接成为轻量级锁，因为没有地方存关于偏向锁的信息了
 * 就直接成为轻量级锁，并把hashCode放入记录的线程信息中
 * @author qupeng
 */
public class SynchronizedBottomTest04 {

	public static void main(String[] args) {
		SynchronizedBottomTest04 test=new SynchronizedBottomTest04();
		//这里计算一下hashCode
		test.hashCode();
		System.out.println("------------------------------加锁之前--------------------------------");
		System.out.println(ClassLayout.parseInstance(test).toPrintable());
		synchronized (test) {
			System.out.println("-------------------------------加锁之后------------------------------");
			System.out.println(ClassLayout.parseInstance(test).toPrintable());
		}

	}

}
