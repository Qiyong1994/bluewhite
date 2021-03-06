package com.bluewhite.ledger.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.ledger.entity.ApplyVoucher;
import com.bluewhite.ledger.service.ApplyVoucherService;
import com.bluewhite.product.product.entity.Product;
import com.bluewhite.system.user.entity.User;

@Controller
public class DispatchAction {

	@Autowired
	private ApplyVoucherService applyVoucherService;
	
	private ClearCascadeJSON clearCascadeJSON;
	{
		clearCascadeJSON = ClearCascadeJSON
				.get()
				.addRetainTerm(ApplyVoucher.class, "id","createdAt","applyNumber", "time", "cause", "applyVoucherType", "applyVoucherKind", "user",
						"passTime", "approvalUser","number","pass","product")
				.addRetainTerm(User.class, "userName","id")
				.addRetainTerm(Product.class, "id", "name", "number")
				.addRetainTerm(BaseData.class, "id", "name");
	}
	
	/**
	 * 新增请求申请
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/ledger/dispatch/saveApplyVoucher", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse saveApplyVoucher(ApplyVoucher applyVoucher) {
		CommonResponse cr = new CommonResponse();
		applyVoucherService.saveApplyVoucher(applyVoucher);
		cr.setMessage("申请成功");
		return cr;
	}
	
	
	/**
	 * 分页查看申请
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/ledger/dispatch/applyVoucherPage", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse applyVoucherPage(PageParameter page, ApplyVoucher applyVoucher) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSON.format(applyVoucherService.findPages(applyVoucher, page)).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}
	
	/**
	 * 通过请求申请
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/ledger/dispatch/passApplyVoucher", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse passApplyVoucher(String ids) {
		CommonResponse cr = new CommonResponse();
		int count = applyVoucherService.passApplyVoucher(ids);
		cr.setMessage("成功通过"+count+"条申请");
		return cr;
	}
	
	/**
	 * 取消通过请求申请
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/ledger/dispatch/cancelApplyVoucher", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse cancelApplyVoucher(String ids) {
		CommonResponse cr = new CommonResponse();
		int count = applyVoucherService.cancelApplyVoucher(ids);
		cr.setMessage("成功");
		return cr;
	}
	
	/**
	 * 撤销请求申请
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/ledger/dispatch/deleteApplyVoucher", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteApplyVoucher(String ids) {
		CommonResponse cr = new CommonResponse();
		int count = applyVoucherService.deleteApplyVoucher(ids);
		cr.setMessage("成功");
		return cr;
	}
	

}
