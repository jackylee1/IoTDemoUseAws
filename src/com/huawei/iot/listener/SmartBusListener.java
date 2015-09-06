package com.huawei.iot.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.huawei.iot.service.BusLineService;

public class SmartBusListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		//do nothing
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Starting load xml file to DB");
		BusLineService.initBusLines();
		System.out.println("Ending load xml file to DB");
	}

}
