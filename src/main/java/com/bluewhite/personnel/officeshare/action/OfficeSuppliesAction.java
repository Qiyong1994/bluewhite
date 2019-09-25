package com.bluewhite.personnel.officeshare.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.ledger.entity.Order;
import com.bluewhite.personnel.attendance.entity.Attendance;
import com.bluewhite.personnel.officeshare.entity.OfficeSupplies;
import com.bluewhite.personnel.officeshare.service.OfficeSuppliesService;
import com.bluewhite.product.primecostbasedata.entity.Materiel;
import com.bluewhite.system.user.entity.User;

@Controller
public class OfficeSuppliesAction {
	
	@Autowired
	private OfficeSuppliesService officeSuppliesService;
	
	
	private ClearCascadeJSON clearCascadeJSON;
	{
		clearCascadeJSON = ClearCascadeJSON.get()
				.addRetainTerm(OfficeSupplies.class, "number", "name", "price", "unit"
						, "inventoryNumber","location")
				.addRetainTerm(BaseData.class, "id", "name");
	}
	
	
	/**
	 * 办公用品列表
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/personnel/getOfficeSupplies", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getOfficeSupplies(OfficeSupplies officeSupplies,PageParameter page) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSON.format(officeSuppliesService.findPages(officeSupplies, page)).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}
	
	/**
	 * 新增修改办公用品
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/personnel/addOfficeSupplies", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addOfficeSupplies(OfficeSupplies officeSupplies) {
		CommonResponse cr = new CommonResponse();
		officeSuppliesService.addOfficeSupplies(officeSupplies);
		cr.setMessage("新增成功");
		return cr;
	}
	
	
	/**
	 * 删除办公用品
	 * 
	 */
	@RequestMapping(value = "/product/deleteOfficeSupplies", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteOfficeSupplies(String ids) {
		CommonResponse cr = new CommonResponse();
		int count = officeSuppliesService.deleteOfficeSupplies(ids);
		cr.setMessage("成功删除"+count+"件办公用品");
		return cr;
	}
	
	
	


}
