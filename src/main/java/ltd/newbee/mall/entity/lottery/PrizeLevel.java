package ltd.newbee.mall.entity.lottery;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 奖项
 */
@ApiModel("奖项")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrizeLevel {
    @ApiModelProperty("中奖类型")
     private Integer awardType;
    private  String group;

    @ApiModelProperty("条件")
    private  String lotteryCondition;

    @ApiModelProperty("等级")
    private String prizeLevel;
    private int sort;

    @ApiModelProperty("奖金分配")
    private String stakeAmount;

    @ApiModelProperty("中奖注数")
    private  String stakeCount;

    @ApiModelProperty("中奖总金额")
    private String totalPrizeamount;
}
