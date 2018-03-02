
package com.event.inject.test;

import com.event.inject.Inject;
import com.event.inject.anno.EventInject;

public class test{
	
	@EventInject("10086")
	int name;
	
	@EventInject("32032030")
	int age;
	
	public test() {
		// TODO Auto-generated constructor stub
		Inject.InjectBind(this);
	}
	
	public void  logName() {
		System.out.println(name);
		System.out.println(age);
	}
	
	public static void main(String[] args) {
		new test().logName();
	}
}