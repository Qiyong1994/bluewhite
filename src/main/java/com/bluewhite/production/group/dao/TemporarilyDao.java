package com.bluewhite.production.group.dao;

import java.util.Date;
import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.production.group.entity.Temporarily;

public interface TemporarilyDao extends BaseRepository<Temporarily, Long> {

	/**
	 * 根据ids,日期,查询
	 * 
	 * @param type
	 * @param orderTimeBegin
	 * @param orderTimeEnd
	 * @return
	 */
	List<Temporarily> findByIdInAndTemporarilyDateAndType(List<Long> Ids, Date orderTimeBegin, Integer type);

	/**
	 * 根据分组id，员工id，日期查询
	 * 
	 * @param userId
	 * @param getfristDayOftime
	 * @return
	 */
	public Temporarily findByUserIdAndTemporarilyDateAndTypeAndGroupId(Long userId, Date getfristDayOftime,
			Integer type, Long groupId);

	/**
	 * 根据分组id，员工id，日期查询
	 * 
	 * @param userId
	 * @param getfristDayOftime
	 * @return
	 */
	public Temporarily findByTemporaryUserIdAndTemporarilyDateAndTypeAndGroupId(Long temporaryUserId,
			Date getfristDayOftime, Integer type, Long groupId);

	/**
	 * 根据日期和类型查找
	 * 
	 * @param type
	 * @param temporarilyDate
	 * @return
	 */
	public List<Temporarily> findByTypeAndTemporarilyDate(Integer type, Date temporarilyDate);

	/**
	 * 根据日期区间和类型查找
	 * 
	 * @param type
	 * @param temporarilyDate
	 * @return
	 */
	public List<Temporarily> findByTypeAndTemporarilyDateBetween(Integer type, Date orderTimeBegin, Date orderTimeEnd);
	
	/**
	 * 根据日期区间查找
	 * 
	 * @param type
	 * @param temporarilyDate
	 * @return
	 */
	public List<Temporarily> findByTemporarilyDateBetween(Date orderTimeBegin, Date orderTimeEnd);

	/**
	 * 根据日期和类型和分组id查找
	 * 
	 * @param type
	 * @param temporarilyDate
	 * @return
	 */
	public List<Temporarily> findByTypeAndGroupIdAndTemporarilyDateBetween(Integer type, Long id, Date stratDate,
			Date endDate);

	/**
	 * 根据分组查询
	 * 
	 * @param type
	 * @return
	 */
	public Temporarily findByIdAndUserId(Long id, Long userId);

}
