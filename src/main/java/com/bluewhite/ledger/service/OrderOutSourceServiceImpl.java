package com.bluewhite.ledger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.ledger.dao.OrderOutSourceDao;
import com.bluewhite.ledger.entity.OrderOutSource;

@Service
public class OrderOutSourceServiceImpl extends BaseServiceImpl<OrderOutSource, Long> implements OrderOutSourceService {
	
	@Autowired
	private OrderOutSourceDao dao;
	
	
	
	
	

	@Override
	public void saveOrderOutSource(OrderOutSource orderOutSource) {

		
		
		
	}


}