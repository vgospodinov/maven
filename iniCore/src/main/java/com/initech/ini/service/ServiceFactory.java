package com.initech.ini.service;


/**
 * 
 * ServiceFactory used to select appropriate service implementations in the context
 * of the ini application. 
 * 
 * @author pahne01
 *
 * @param <T> type of the service in question.
 */
public interface ServiceFactory<T> {

	T getService();
	
}
