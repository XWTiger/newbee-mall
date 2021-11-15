package ltd.newbee.mall.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ltd.newbee.mall.entity.common.ImgOrder;
import ltd.newbee.mall.entity.lottery.football.LotteryOrder;

import java.util.List;

@Data
@ApiModel("彩票订单")
@AllArgsConstructor
@NoArgsConstructor
public class LotteryOrderVO extends  NewBeeMallOrder {

    private List<LotteryOrder> lotteryOrders;

    @ApiModelProperty(hidden = true)
    private List<ImgOrder> imgOrders;

}
