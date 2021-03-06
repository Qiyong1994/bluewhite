 package com.bluewhite.production.temporarypack;

import java.util.Date;
import java.util.List;

import com.bluewhite.base.BaseRepository;

/**
 * 
 * @author zhangliang
 * @date 2020/01/10
 */
public interface MantissaLiquidationDao  extends BaseRepository<MantissaLiquidation, Long>{
    
    /**
     * 根据贴包日期
     * @param time
     * @return
     */
    List<MantissaLiquidation> findByTimeBetween(Date startTime ,Date endTime);
    
    
    /**
     * 根据下单
     * @param id
     * @return
     */
    List<MantissaLiquidation>  findByUnderGoodsId(Long id);
}
