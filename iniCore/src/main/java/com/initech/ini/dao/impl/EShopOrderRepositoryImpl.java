package com.initech.ini.dao.impl;

import org.springframework.stereotype.Repository;

import com.initech.ini.dao.EShopOrderRepository;
import com.initech.ini.dao.IniDaoSupport;
import com.initech.ini.model.EShopOrder;

@Repository
public class EShopOrderRepositoryImpl extends IniDaoSupport implements EShopOrderRepository{

	
	@Override
	public void save(EShopOrder eShopOrder) {
		getHibernateTemplate().save(eShopOrder);
	}
	
	
}
