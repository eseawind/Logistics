package Logistics.Servlet;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import Logistics.Common.Permission;
import Logistics.Common.Tools;

public class MySessionListener implements HttpSessionListener{

	public void sessionCreated(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		Tools.print("Session created");
		
	}

	public void sessionDestroyed(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		Tools.print("Session destroyed");
	}

}
