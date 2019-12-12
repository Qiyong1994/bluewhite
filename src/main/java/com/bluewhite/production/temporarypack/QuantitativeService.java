package com.bluewhite.production.temporarypack;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;

public interface QuantitativeService extends BaseCRUDService<Quantitative,Long>{
	
	/**
	 * 分页
	 * @param quantitative
	 * @param page
	 * @return
	 */
	PageResult<Quantitative> findPages(Quantitative quantitative, PageParameter page);
	
	/**
	 * 新增
	 * @param quantitative
	 */
	public void saveQuantitative(Quantitative quantitative);
	
	/**
	 * 发货
	 * @param ids
	 * @return
	 */
	int auditQuantitative(String ids);
	
	/**
	 * 打印
	 * @param ids
	 * @return
	 */
	int printQuantitative(String ids);
	
	/**
	 * 删除
	 * @param ids
	 */
	int deleteQuantitative(String ids);
	
	/**
	 * 审核
	 * @param ids
	 * @return
	 */
	int sendQuantitative(String ids);
	
	/**
	 * 设置发货数量
	 * @param id
	 * @param actualSingleNumber
	 */
	public void setActualSingleNumber(Long id, Integer actualSingleNumber);
	
	/**
	 * 核对成功
	 * @param id
	 */
	void checkNumber(Long id,Integer check);
	
	/**
	 * 修改子单
	 * @param quantitativeChild
	 */
	public void updateActualSingleNumber(QuantitativeChild quantitativeChild);

	

}
