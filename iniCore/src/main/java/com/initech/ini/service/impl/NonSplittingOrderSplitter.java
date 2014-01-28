package com.initech.ini.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.initech.ini.model.Delivery;
import com.initech.ini.model.EShopOrder;
import com.initech.ini.service.OrderSplitter;
import com.initech.ini.util.Acme;
import com.initech.ini.util.Coyote;
import com.initech.ini.util.Daffy;
import com.initech.ini.util.Default;

/**
 * This is a default implementation of an {@link OrderSplitter}. The order will not be splitted.
 * This implementation is useful for clients that don't need any order splitting or for any orders
 * that contain one single order item. 
 * 
 * @author pahne01
 *
 */
@Service
@Daffy
@Acme
@Coyote
@Default
public class NonSplittingOrderSplitter implements OrderSplitter {

	
	@Override
	public List<Delivery> splitOrder(EShopOrder order) {
		ArrayList<Delivery> deliveries = new ArrayList<Delivery>();
		deliveries.add(new Delivery(order));
		return deliveries;
	}
	
	
	
}
