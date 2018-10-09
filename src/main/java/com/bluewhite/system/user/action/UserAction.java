package com.bluewhite.system.user.action;

import java.text.SimpleDateFormat;

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

import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.DateTimePattern;
import com.bluewhite.common.SessionManager;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.CurrentUser;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.utils.BankUtil;
import com.bluewhite.production.group.entity.Group;
import com.bluewhite.system.user.entity.Role;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.entity.UserContract;
import com.bluewhite.system.user.service.UserService;

@Controller
@RequestMapping("/system/user")
public class UserAction {
	

	@Autowired
	private UserService userService;
	

	private ClearCascadeJSON clearCascadeJSON;

	{
		clearCascadeJSON = ClearCascadeJSON
				.get()
				.addRetainTerm(User.class,"id","fileId","price","status","workTime","number","pictureUrl", "userName", "phone","position","orgName","idCard",
						"nation","email","gender","birthDate","group","idCard","permanentAddress","livingAddress","marriage","procreate","education"
						,"school","major","contacts","information","entry","estimate","actua","socialSecurity","bankCard1","bankCard2","agreement"
						,"promise","contract","contractDate","frequency","quitDate","quit","reason","train","remark","userContract")
				.addRetainTerm(Group.class, "id","name", "type", "price")
				.addRetainTerm(Role.class, "name", "role", "description","id")
				.addRetainTerm(BaseData.class, "id","name", "type")
				.addRetainTerm(UserContract.class, "id","number", "username","archives","pic","IdCard","bankCard","physical",
						"qualification","formalSchooling","agreement","secrecyAgreement","contract","remark","quit");
	}
	
	/**
	 *  查看用户列表
	 *  按不同部门显示的不同的人员
	 * @param request
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/pages", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse userPages(HttpServletRequest request, User user,PageParameter page) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSON.format(userService.getPagedUser(page,user)).toJSON());
		return cr;
	}
	
	
	
	
	/**
	 * 新增一个用户
	 * 
	 * @param request 请求
	 * @param user 用户实体类
	 * @return cr
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse createUser(HttpServletRequest request, User user) {
		CommonResponse cr = new CommonResponse();
		user.setPassword("123456");
		user.setForeigns(0);
		user.setLoginName(user.getUserName());
		if(!StringUtils.isEmpty(user.getPhone())){
			User u = userService.findByPhone(user.getPhone());
			if(u != null){
				cr.setMessage("该用户手机号已存在");
				return cr;
			}
		}
		cr.setData(clearCascadeJSON.format(userService.save(user)).toJSON());
		return cr;
	}
	
	
	
	/**
	 * 修改用户信息
	 * @param request 请求
	 * @param user 用户实体类
	 * @return cr
	 */	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse changeUser(HttpServletRequest request, User user) {
		CommonResponse cr = new CommonResponse();
		if(user.getId() == null){
			cr.setMessage("id为空");
			return cr;
		}
		User oldUser = userService.findOne(user.getId());
		BeanCopyUtils.copyNotEmpty(user,oldUser);
		cr.setData(clearCascadeJSON.format(userService.save(oldUser)).toJSON());
		cr.setMessage("修改成功");
		return cr;
	}
	
	
	
	/**
	 * 查询用户详细信息
	 * @param request 请求
	 * @return cr
	 */
	@RequestMapping(value = "/userInfo", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse userInfo(HttpServletRequest request,Long id) {
		User user = userService.findOne(id);
		CommonResponse cr = new CommonResponse(clearCascadeJSON.format(user).toJSON());
		return cr;
	}
	

	/**
	 * 查询当前用户信息
	 * @param request 请求
	 * @return cr
	 */
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getUser(HttpServletRequest request) {
		CurrentUser cu = SessionManager.getUserSession();
		User user = userService.findOne(cu.getId());
		CommonResponse cr = new CommonResponse(clearCascadeJSON.format(user).toJSON());
		return cr;
	}
	
	/**
	 * 通过银行卡号得到银行名称
	 * @param request 请求
	 * @return cr
	 */
	@RequestMapping(value = "/getbank", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getbank(HttpServletRequest request,String idCard) {
		CommonResponse cr = new CommonResponse();
		String bankName = BankUtil.getNameOfBank(idCard);
		cr.setMessage("查询成功");
		cr.setData(bankName);
		return cr;
	}
	
	

	
	/**
	 * 
	 * 测试
	 */
	@RequestMapping(value = "/oooxxx", method = RequestMethod.GET)
	@ResponseBody
	private CommonResponse oooxxx(User user) {
		CommonResponse cr = new CommonResponse();
		userService.oooxxx();
		return cr;
	}
	
	
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(
				DateTimePattern.DATEHMS.getPattern());
		binder.registerCustomEditor(java.util.Date.class, null,
				new CustomDateEditor(dateTimeFormat, true));
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
	}


}
