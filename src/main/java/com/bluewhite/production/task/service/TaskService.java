package com.bluewhite.production.task.service;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.production.task.entity.Task;
@Service
public interface TaskService extends BaseCRUDService<Task,Long>{
	
	/**
	 * 新增任务
	 * 1,同时计算出 预计完成时间 ， 任务价值(预计成本费用)，b工资净值 入库
	 * @param task
	 * @return
	 */
	public Task addTask(Task task);
	/**
	 * 分页查询所有任务
	 * @param task
	 * @param page
	 * @return
	 */
	public PageResult<Task>  findPages(Task task, PageParameter page);

}