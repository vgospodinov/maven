package com.initech.ini.service.impl;

import org.springframework.stereotype.Service;

import com.initech.ini.service.OrderSplitter;
import com.initech.ini.service.ServiceFactory;


/**
 * 
 * Select an appropriate OrderSplitter service. OrderSplitter is selected
 * on client basis and therefore reuses the existing AbstractServiceFactory
 *
 */
@Service
public class OrderSplitterFactory extends AbstractServiceFactory<OrderSplitter> 
    implements ServiceFactory<OrderSplitter>{

}
