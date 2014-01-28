package com.initech.ini.service;

import java.util.List;

import com.initech.ini.model.Delivery;
import com.initech.ini.model.EShopOrder;

/**
 * 
 * OrderSplitting is quit different for different ini clients. But they
 * all have in common that any incoming order is split into one or more
 * deliveries.
 * 
 * @author pahne01
 *
 */
public interface OrderSplitter {

	/**
	 * Split the order according to client specific split criteria.
	 * @param order incoming order from eShop or "Rapid Order Client"
	 * @return list of deliveries containing at least one delivery. The 
	 *     returned deliveries have the splitting status SPLIT_PROCESSED.
	 *       
	 */
	List<Delivery> splitOrder(EShopOrder order);
	
}
