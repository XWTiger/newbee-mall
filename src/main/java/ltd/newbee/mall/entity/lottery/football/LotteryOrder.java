package ltd.newbee.mall.entity.lottery.football;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import ltd.newbee.mall.common.GameType;

@ApiModel("订单")
@Data
public class LotteryOrder {

    /**
     * 订单类型
     */
    private GameType type;

    /**
     * 包括 排列3 排列5 大乐透 七星彩
     */
    private String numbers;

    @ApiModelProperty("主队编码")
    private String homeTeamCode;

    @ApiModelProperty("主队简称")
    private String homeTeamAbbName;

    @ApiModelProperty("客队编码")
    private String awayTeamCode;

    @ApiModelProperty("客队简称")
    private String awayTeamAbbName;

    @ApiModelProperty("比赛时间（周）： 周一001")
    private String matchNumStr;

    @ApiModelProperty("联赛编号， eg： 100")
    private Integer leagueId;


    @ApiModelProperty("排列3、5 ， 大乐透，七星彩")
    private String numberContent;

    @ApiModelProperty("倍数")
    private Integer times;





}
