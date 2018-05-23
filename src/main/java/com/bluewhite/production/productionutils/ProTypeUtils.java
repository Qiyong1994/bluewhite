package com.bluewhite.production.productionutils;

import java.util.List;

import com.bluewhite.common.Constants;
import com.bluewhite.common.SessionManager;
import com.bluewhite.common.entity.CurrentUser;
import com.bluewhite.production.procedure.entity.Procedure;
/**
 * 生产控制计算工具类
 * @author zhangliang
 *
 */
public  class ProTypeUtils {
	
	/**
	 * 时间常量
	 */
	private final static Integer  TIME = 60;
	
	
	/**
	 * 当部门预计生产价格计算系数 1=一楼质检
	 */
	private final static double  FRIST_QUALITY = 0.00750375;
	/**
	 * 当部门预计生产价格计算系数 2=一楼包装
	 */
	private final static double  FRIST_PACK = 0.00750375;
	/**
	 * 当部门预计生产价格计算系数 3=二楼针工
	 */
	private final static double  TOW_DEEDLE = 0.00750375;
	
	/**
	 * 一楼质检
	 * 当外发价格计算系数1
	 */
	private final static String  QUALITY_STRING = "剪线头";
	
	/**
	 * 一楼质检
	 * 当外发价格计算系数2
	 */
	private final static double  QUALITY_DOUBLE = 0.00621;
	
	/**
	 * 一楼质检
	 * 当外发价格计算系数3
	 */
	private final static double  QUALITY_DOUBLETOW = 0.08;
	
	
	
	/**
	 * 根据不同权限返回工序的不同类型
	 * 1=一楼质检，2=一楼包装，3=二楼针工
	 * @return 
	 */
	public static Integer roleGetProType(){
		CurrentUser cu = SessionManager.getUserSession();
		Integer type = null ;
		// 生产部一楼质检
		if(cu.getRole().contains(Constants.PRODUCT_FRIST_QUALITY)){
			type = 1;
		}
		//生产部一楼打包
		if(cu.getRole().contains(Constants.PRODUCT_FRIST_PACK)){
			type = 2;
		}
		//生产部二楼针工
		if(cu.getRole().contains(Constants.PRODUCT_TOW_DEEDLE)){
			type = 3;
		}
		return type;
	}
	
	/**
	 * 根据不同的部门，计算出当部门预计生产价格
	 * @param price
	 * @param type
	 * @return
	 */
	public static Double sumProTypePrice(Double price,Integer type){
		Double sumPrice = 0.0 ;
		switch (type) {
		case 1:// 生产部一楼质检
			sumPrice = price*ProTypeUtils.FRIST_QUALITY;
			break;
		case 2://生产部一楼打包
			sumPrice = price*ProTypeUtils.FRIST_PACK;
				break;
		case 3://生产部二楼针工
			sumPrice = price*ProTypeUtils.TOW_DEEDLE;
			break;
		default:
			break;
		}
		return sumPrice;
	}
	
	/**
	 * 根据不同的部门，计算出外发价格
	 * @param price
	 * @param type
	 * @return
	 */
	public static Double sumProTypeHairPrice(List<Procedure> procedureList, Integer type) {
		Double sumPrice = 0.0 ;
		switch (type) {
		case 1:// 生产部一楼质检
			for(Procedure procedure : procedureList){
				if(procedure.getName().equals(ProTypeUtils.QUALITY_STRING)){
					sumPrice = procedure.getWorkingTime()*ProTypeUtils.QUALITY_DOUBLE*ProTypeUtils.QUALITY_DOUBLETOW;
				}
			}
			break;
		case 2://生产部一楼打包
			break;
		case 3://生产部二楼针工
			break;
		default:
			break;
		}
		return sumPrice;
	}
	
	/**
	 * 根据不同的部门，计算预计完成时间
	 * @param price
	 * @param type
	 * @return
	 */
	public static Double sumExpectTime(Procedure procedure, Integer type,Integer number) {
		Double sumExpectTime = 0.0 ;
		switch (type) {
		case 1:// 生产部一楼质检
			sumExpectTime = procedure.getWorkingTime()*number*ProTypeUtils.TIME;
			break;
		case 2://生产部一楼打包
			break;
		case 3://生产部二楼针工
			break;
		default:
			break;
		}
		return sumExpectTime;
	}
	
	

}
