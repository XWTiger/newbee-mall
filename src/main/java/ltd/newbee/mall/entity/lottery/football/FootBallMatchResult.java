package ltd.newbee.mall.entity.lottery.football;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ltd.newbee.mall.annotation.ObjectScan;

@ApiModel("足球比赛结果")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ObjectScan(value = "matchResult", isArray = true)
public class FootBallMatchResult {

    @ApiModelProperty("负赔率")
    private String a;
    @ApiModelProperty("客队全称")
    private String allAwayTeam;

    @ApiModelProperty("主队全称")
    private String allHomeTeam;

    @ApiModelProperty("客队")
    private String awayTeam;

    @ApiModelProperty("客队编号")
    private Integer awayTeamId;


    private Integer bettingSingle;

    @ApiModelProperty("平赔率")
    private String d;

    @ApiModelProperty("主队让球数 +1 让一球, -1 被让球")
    private String goalLine;

    @ApiModelProperty("胜赔率")
    private String h;

    @ApiModelProperty("主队")
    private String homeTeam;

    @ApiModelProperty("主队编号")
    private Integer homeTeamId;

    @ApiModelProperty("联赛背景颜色")
    private String leagueBackColor;

    @ApiModelProperty("联赛编号")
    private Integer leagueId;

    @ApiModelProperty("联赛名称")
    private String leagueName;

    @ApiModelProperty("联赛简称")
    private String leagueNameAbbr;

    @ApiModelProperty("比赛时间")
    private String matchDate;

    @ApiModelProperty("比赛编号")
    private Integer matchId;

    @ApiModelProperty("比赛号")
    private String matchNum;

    @ApiModelProperty("比赛号中文")
    private String matchNumStr;

    @ApiModelProperty("1 待开奖， 2 已完成")
    private String matchResultStatus;

    @ApiModelProperty("Close 关闭， Payout 完成")
    private String poolStatus;

    @ApiModelProperty("半场比分")
    private String sectionsNo1;

    @ApiModelProperty("全场比分")
    private String sectionsNo999;

    @ApiModelProperty("A 负， D 平，  H 胜")
    private String winFlag;
}
