package com.bluewhite.system.user.service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.system.user.entity.TemporaryUser;
import com.bluewhite.system.user.entity.User;

public interface TemporaryUserService  extends BaseCRUDService<TemporaryUser, Long> {
	
	/**
	 * 分页查询 临时人员
	 * 
	 * @param page
	 * @param user
	 * @return
	 */
	public PageResult<TemporaryUser> getPagedUser(PageParameter page, TemporaryUser temporaryUser);
	
	/**
	 * 新增临时人员
	 * @param temporaryUser
	 */
	public void addTemporaryUser(TemporaryUser temporaryUser);

}
