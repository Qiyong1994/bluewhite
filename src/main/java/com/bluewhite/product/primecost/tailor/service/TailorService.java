package com.bluewhite.product.primecost.tailor.service;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.product.primecost.tailor.entity.Tailor;

@Service
public interface TailorService extends BaseCRUDService<Tailor,Long> {
	
	
	/**
	 * 
	 * 新增裁剪
	 * @param tailor
	 * @return
	 */
	public Tailor  saveTailor(Tailor tailor) throws Exception;
	
	/**
	 * 
	 * @param tailor
	 * @param page
	 * @return
	 */
	public PageResult<Tailor> findPages(Tailor tailor, PageParameter page);;

}