package com.event.inject;

import com.event.inject.adapter.InjectAdapter;


public class Inject {
	
	public static String suffix = "$InjectAdapter";
	
	
	
     public static void InjectBind(Object event) {
    	 String eclz = event.getClass().getName() + suffix;
    	 try {
			Class<?> clz = Class.forName(eclz);
			InjectAdapter adapter  = (InjectAdapter)clz.newInstance();
		    adapter.injects(event);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
