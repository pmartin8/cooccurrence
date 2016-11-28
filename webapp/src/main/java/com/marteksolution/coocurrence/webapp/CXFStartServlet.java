package com.marteksolution.coocurrence.webapp;

import javax.servlet.annotation.WebServlet;

import org.apache.cxf.transport.servlet.CXFServlet;

@WebServlet(urlPatterns="/*")
public class CXFStartServlet extends CXFServlet {
	
}
