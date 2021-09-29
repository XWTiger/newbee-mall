package ltd.newbee.mall.service;

import ltd.newbee.mall.entity.common.PageCL;
import ltd.newbee.mall.entity.lottery.LoterryPl5;

/**
 * this is for lottery service， just request official api
 * 大乐透
 */

public interface LotteryService {


    //排列5

    /**
     * @param gameNo 玩法编号
     * @param provinceId 省份id
     * @param isVerify 1
     * @param termLimits 一次查几个
     * @return
     */
    PageCL<LoterryPl5> getLotteryPl5(String gameNo, String provinceId, String isVerify, String termLimits);

}
