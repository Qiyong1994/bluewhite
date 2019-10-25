package com.bluewhite.personnel.roomboard.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.DateTimePattern;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.personnel.attendance.dao.PersonVariableDao;
import com.bluewhite.personnel.attendance.entity.Attendance;
import com.bluewhite.personnel.attendance.entity.AttendanceTime;
import com.bluewhite.personnel.attendance.entity.PersonVariable;
import com.bluewhite.personnel.roomboard.entity.Meal;
import com.bluewhite.personnel.roomboard.service.MealService;
import com.bluewhite.system.user.entity.TemporaryUser;
import com.bluewhite.system.user.entity.User;

@Controller
public class MealAction {

	@Autowired
	private MealService service;

	@Autowired
	private PersonVariableDao personVariableDao;

	private ClearCascadeJSON clearCascadeJSON;
	{
		clearCascadeJSON = ClearCascadeJSON.get()
				.addRetainTerm(Meal.class, "userId", "userName", "user","temporaryUser", "mode", "tradeDaysTime", "price")
				.addRetainTerm(User.class, "id", "userName", "orgName", "orgNameId")
				.addRetainTerm(TemporaryUser.class, "id", "userName");
	}

	/**
	 * 分页查看报餐
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/personnel/getMeal", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getContact(HttpServletRequest request, PageParameter page, Meal meal) {
		CommonResponse cr = new CommonResponse();
		PageResult<Meal> mealList = service.findPage(meal, page);
		cr.setData(clearCascadeJSON.format(mealList).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}

	/**
	 * 新增修改
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/fince/addMeal", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addConsumption(HttpServletRequest request, Meal meal) {
		CommonResponse cr = new CommonResponse();
		if (meal.getId() != null) {
			Meal meal2 = service.findOne(meal.getId());
			BeanCopyUtils.copyNullProperties(meal2, meal);
			meal.setCreatedAt(meal2.getCreatedAt());
			cr.setMessage("修改成功");
		} else {
			cr.setMessage("添加成功");
		}
		service.addMeal(meal);
		return cr;
	}

	/**
	 * 删除
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/fince/deleteMeal", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteConsumption(HttpServletRequest request, String[] ids) {
		CommonResponse cr = new CommonResponse();
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			for (int i = 0; i < ids.length; i++) {
				Long id = Long.parseLong(ids[i]);
				service.delete(id);
				count++;
			}
		}
		cr.setMessage("成功删除" + count + "条");
		return cr;
	}

	/**
	 * 查看字典表报餐价格
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/personnel/getpersonVariabledao", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getPersonVariabledao(HttpServletRequest request, PageParameter page, Integer type) {
		CommonResponse cr = new CommonResponse();
		PersonVariable personVariable = service.findByType(type);
		cr.setData(clearCascadeJSON.format(personVariable).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}

	/**
	 * 新增修改
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/personnel/addPersonVaiable", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse updatePerson(HttpServletRequest request, PersonVariable personVariable) {
		CommonResponse cr = new CommonResponse();
		if (personVariable.getId() != null) {
			PersonVariable personVariable2 = personVariableDao.findOne(personVariable.getId());
			BeanCopyUtils.copyNullProperties(personVariable2, personVariable);
			personVariable2.setCreatedAt(personVariable2.getCreatedAt());
			cr.setMessage("修改成功");
		} else {
			cr.setMessage("添加成功");
		}
		service.updateperson(personVariable);
		return cr;
	}

	/**
	 * 报餐汇总
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/personnel/getSummaryMeal", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getSummaryMeal(HttpServletRequest request, PageParameter page, Meal meal) {
		CommonResponse cr = new CommonResponse();
		List<Map<String, Object>> list = service.findMealSummary(meal);
		cr.setData(list);
		cr.setMessage("查询成功");
		return cr;
	}

	/**
	 * 水电
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/personnel/getfindElectric", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getfindElectric(HttpServletRequest request, Meal meal) {
		CommonResponse cr = new CommonResponse();
		List<Map<String, Object>> list = service.findElectric(meal);
		cr.setData(list);
		cr.setMessage("查询成功");
		return cr;
	}

	/**
	 * 每天汇总
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/personnel/getSummaryWage", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getSummaryWage(HttpServletRequest request, PageParameter page, Meal meal) {
		CommonResponse cr = new CommonResponse();
		List<Map<String, Object>> list = service.findWage(meal);
		cr.setData(list);
		cr.setMessage("查询成功");
		return cr;
	}

	/**
	 * 同步报餐记录
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 * @throws ParseException
	 * @throws Exception
	 */
	@RequestMapping(value = "/personnel/getEatType", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getEatType(AttendanceTime attendanceTime) throws ParseException {
		CommonResponse cr = new CommonResponse();
		int list = service.initMeal(attendanceTime);
		cr.setMessage("成功同步" + list + "条吃饭记录");
		return cr;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DateTimePattern.DATEHMS.getPattern());
		binder.registerCustomEditor(java.util.Date.class, null, new CustomDateEditor(dateTimeFormat, true));
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}

}
