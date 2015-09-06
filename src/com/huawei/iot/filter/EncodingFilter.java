package com.huawei.iot.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class EncodingFilter implements Filter {
	protected String sEncodingName;

	protected FilterConfig sysFilter;

	protected boolean bEnable;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain arg2)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		if (this.bEnable) {
			try {
				request.setCharacterEncoding(this.sEncodingName);
				response.setContentType("text/html;charset=" + this.sEncodingName);
				response.setCharacterEncoding(this.sEncodingName);
				arg2.doFilter(request, response);

			} catch (Exception e) {
				System.out.println("encoding exception , e: " + e);
			}
			// session.close();
		} else {
			arg2.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		this.sysFilter = arg0;
		this.loadFilterSetting();
	}

	private void loadFilterSetting() {
		this.sEncodingName = this.sysFilter.getInitParameter("encoding");
		String sEnable = this.sysFilter.getInitParameter("enable");
		if (sEnable != null && sEnable.equalsIgnoreCase("true")) {
			this.bEnable = true;
		} else {
			this.bEnable = false;
		}
	}

}
