package com.bluewhite.production.temporarypack;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;
import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.DateTimePattern;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.utils.excel.ExcelListener;
import com.bluewhite.product.product.entity.Product;

@Controller
public class TemporaryPackAction {
	
	@Autowired
	private UnderGoodsService underGoodsService;
	@Autowired
	private QuantitativeService quantitativeService;
	
	private ClearCascadeJSON clearCascadeJSON;
	{
		clearCascadeJSON = ClearCascadeJSON.get()
				.addRetainTerm(UnderGoods.class, "id", "remarks","product","number","bacthNumber","status","allotTime")
				.addRetainTerm(Product.class, "id", "name");
	}
	private ClearCascadeJSON clearCascadeJSONQuantitative;
	{
		clearCascadeJSONQuantitative = ClearCascadeJSON.get()
				.addRetainTerm(Quantitative.class, "id", "underGoods","sumPackageNumber","singleNumber","time")
				.addRetainTerm(UnderGoods.class, "id", "remarks","product","number","bacthNumber","status","allotTime")
				.addRetainTerm(Product.class, "id", "name");
	}
	
	
	/**
	 * 新增下货单
	 * 
	 */
	@RequestMapping(value = "/temporaryPack/saveUnderGoods", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse saveUnderGoods(UnderGoods underGoods) {
		CommonResponse cr = new CommonResponse();
		underGoodsService.saveUnderGoods(underGoods);
		if(underGoods.getId()==null){
			cr.setMessage("新增成功");
		}else{
			cr.setMessage("修改成功");
		}
		return cr;
	}
	
	/**
	 * 查询下货单
	 * 
	 */
	@RequestMapping(value = "/temporaryPack/findPagesUnderGoods", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse findPagesUnderGoods( UnderGoods underGoods ,PageParameter page) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSON.format(underGoodsService.findPages(underGoods, page)).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}
	
	/**
	 * 删除下货单
	 */
	@RequestMapping(value = "/temporaryPack/deleteUnderGoods", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteUnderGoods(String ids) {
		CommonResponse cr = new CommonResponse();
		underGoodsService.delete(ids);
		cr.setMessage("删除成功");
		return cr;
	}
	
	/**
	 * 新增量化单
	 */
	@RequestMapping(value = "/temporaryPack/saveQuantitative", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse saveQuantitative(Quantitative quantitative) {
		CommonResponse cr = new CommonResponse();
		quantitativeService.saveQuantitative(quantitative);
		cr.setMessage("新增成功");
		return cr;
	}
	
	/**
	 * 新增量化单包装物
	 */
	@RequestMapping(value = "/temporaryPack/saveQuantitativeMaterials", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse saveQuantitativeMaterials(Quantitative quantitative) {
		CommonResponse cr = new CommonResponse();
		quantitativeService.saveQuantitativeMaterials(quantitative);
		cr.setMessage("新增成功");
		return cr;
	}
	
	/**
	 * 发货 量化单
	 */
	@RequestMapping(value = "/temporaryPack/auditQuantitative", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse auditQuantitative(String ids) {
		CommonResponse cr = new CommonResponse();
		quantitativeService.auditQuantitative(ids);
		cr.setMessage("成功");
		return cr;
	}
	
	/**
	 * 打印 量化单
	 */
	@RequestMapping(value = "/temporaryPack/printQuantitative", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse printQuantitative(String ids) {
		CommonResponse cr = new CommonResponse();
		quantitativeService.printQuantitative(ids);
		cr.setMessage("成功");
		return cr;
	}
	
	/**
	 * 查询量化单
	 * 
	 */
	@RequestMapping(value = "/temporaryPack/findPagesQuantitative", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse findPagesQuantitative(Quantitative quantitative ,PageParameter page) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSONQuantitative.format(quantitativeService.findPages(quantitative, page)).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}
	
	/**
	 * 删除量化单
	 */
	@RequestMapping(value = "/temporaryPack/deleteQuantitative", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteQuantitative(String ids) {
		CommonResponse cr = new CommonResponse();
		quantitativeService.deleteQuantitative(ids);
		cr.setMessage("删除成功");
		return cr;
	}
	
	/**
	 * 新增下货单(导入)
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/temporaryPack/import/excelUnderGoods", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse excelOutProcurement(@RequestParam(value = "file", required = false) MultipartFile file
			,Long userId ,Long warehouseId) throws IOException {
		CommonResponse cr = new CommonResponse();
		InputStream inputStream = file.getInputStream();
		ExcelListener excelListener = new ExcelListener();
		EasyExcelFactory.readBySax(inputStream, new Sheet(1, 1, UnderGoodsPoi.class), excelListener);
		int count = underGoodsService.excelUnderGoods(excelListener);
		inputStream.close();
		cr.setMessage("成功导入"+count+"条下货单");
		return cr;
	}
	
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DateTimePattern.DATEHMS.getPattern());
		binder.registerCustomEditor(java.util.Date.class, null, new CustomDateEditor(dateTimeFormat, true));
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}
	

}