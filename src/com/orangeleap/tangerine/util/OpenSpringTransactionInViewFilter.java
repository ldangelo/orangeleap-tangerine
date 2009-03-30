package com.orangeleap.tangerine.util;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class OpenSpringTransactionInViewFilter extends OncePerRequestFilter {
	
	
    protected final Log logger = LogFactory.getLog(getClass());

	    private Object getBean(HttpServletRequest request, String bean) {
	    	ServletContext servletContext = request.getSession().getServletContext();
	    	WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
	    	return wac.getBean(bean);
	    }
	    
	    private boolean suppressStartTransaction(HttpServletRequest request) {
	    	String url = request.getRequestURL().toString();
	    	return url.endsWith(".gif") 
	    	|| url.endsWith(".jpg") 
	    	|| url.endsWith(".png")
	    	|| url.endsWith(".js")
	    	|| url.endsWith(".css")
	    	|| url.endsWith("/import.htm")
	    	;
	    }

		protected void doFilterInternal(
				HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
				throws ServletException, IOException {
			
		    if (TransactionSynchronizationManager.isActualTransactionActive() 
		    		|| suppressStartTransaction(request)) {
				filterChain.doFilter(request, response);
				return;
		    }
		    
			DataSourceTransactionManager txManager = (DataSourceTransactionManager) getBean(request, "transactionManager");
		    logger.debug(request.getRequestURL() + ", txManager = " + txManager);
		    
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setName("TxName");
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

			TransactionStatus status = null;
			try {
				status = txManager.getTransaction(def);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			
			try {
				filterChain.doFilter(request, response);
			}
			catch (Throwable ex) {
			    try {
			    	txManager.rollback(status);
				} catch (Exception e) {
					e.printStackTrace();
				}
			    throw new RuntimeException(ex);
			}
			
			try {
				txManager.commit(status);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		
		}


}
