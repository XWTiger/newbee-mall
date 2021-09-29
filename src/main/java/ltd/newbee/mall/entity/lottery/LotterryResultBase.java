package ltd.newbee.mall.entity.lottery;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LotterryResultBase {

    @ApiModelProperty("期数")
    private String lotteryDrawNum;

    @ApiModelProperty("开奖结果")
    private String lotteryDrawResult;

    @ApiModelProperty("开奖时间")
    private String lotteryDrawTime;

    @ApiModelProperty("彩票名称： 排列5")
    private String lotteryGameName;

    @ApiModelProperty("彩票类型编号")
    private String lotteryGameNum;


    @ApiModelProperty("开售时间")
    private String lotterySaleBeginTime;

    @ApiModelProperty("停售时间")
    private String lotterySaleEndtime;

    @ApiModelProperty("奖池")
    private String poolBalanceAfterdraw;

    private String lotteryEquipmentCount;
}
