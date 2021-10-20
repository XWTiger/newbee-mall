package ltd.newbee.mall.entity.lottery.football;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("胜负平")
@TableName("win_defeat_order")
public class OddsOrder {

    @ApiModelProperty("主队让球情况 -1 被让 ， 空 不让球， 1 让一球")
    private String goalLine;

    @ApiModelProperty("赔率")
    private String odds;

    @ApiModelProperty("主胜")
    private Boolean homeWin;

    @ApiModelProperty("主胜赔率")
    private Double homeWinOdds;

    @ApiModelProperty("平局")
    private Boolean homeDraw;

    private Double homeDrawOdds;

    @ApiModelProperty("客胜")
    private Boolean visitingWin;

    @ApiModelProperty("客胜赔率")
    private Double visitingWinOdds;

    @ApiModelProperty("比赛日期")
    private String matchNumStr;

    @ApiModelProperty("本场共买数， 最多3 最少0")
    private int count;
}