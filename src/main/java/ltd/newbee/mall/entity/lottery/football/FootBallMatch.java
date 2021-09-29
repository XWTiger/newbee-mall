package ltd.newbee.mall.entity.lottery.football;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("足球比赛信息")
public class FootBallMatch  {

    @ApiModelProperty("")
    private  Integer matchNum;

    @ApiModelProperty("主队编码")
    private String homeTeamCode;

    @ApiModelProperty("客队编码")
    private String awayTeamCode;

    @ApiModelProperty("匹配时间（周）： 周一001")
    private String matchNumStr;

    @ApiModelProperty("星期： 周一")
    private String matchWeek;

    @ApiModelProperty("联赛编号， eg： 100")
    private Integer leagueId;

    @ApiModelProperty("联赛代码， eg: RPL")
    private String leagueCode;

    @ApiModelProperty("联赛简称， eg: 俄超")
    private String leagueAbbName;

    @ApiModelProperty("联赛全称, eg: 俄罗斯超级联赛")
    private String  leagueAllName;

    @ApiModelProperty("主队编号， eg：293")
    private Integer homeTeamId;

    @ApiModelProperty("主队简称")
    private String homeTeamAbbName;

    @ApiModelProperty("主队全称")
    private String homeTeamAllName;

    @ApiModelProperty("主队代码： CSK")
    private String homeTeamAbbEnName;

    @ApiModelProperty("客队编号： 290")
    private Integer awayTeamId;

    @ApiModelProperty("客队简称")
    private String awayTeamAbbName;

    @ApiModelProperty("客队全称")
    private String awayTeamAllName;

    @ApiModelProperty("客队代码: SPM")
    private String awayTeamAbbEnName;

    @ApiModelProperty("比赛时间")
    private String matchDate;

    @ApiModelProperty("可能是售卖截至日期")
    private String businessDate;

    @ApiModelProperty("比赛时间: 00:30")
    private String matchTime;

    @ApiModelProperty("比赛编号")
    private String matchId;

    @ApiModelProperty("1 界面能计算购买, 2是不能计算购买")
    private Integer sellStatus;

    @ApiModelProperty("比赛状态")
    private String matchStatus;


    @ApiModelProperty("备注")
    private String remark;

    private Integer isHot;

    private Integer isHide;

    @ApiModelProperty("主场等级, eg: [俄超9] ")
    private String homeRank;


    @ApiModelProperty("客场， eg: [俄超8]")
    private String awayRank;


    @ApiModelProperty("赔率列表")
    private List<Odds> oddsList;


    private List<Pool> poolList;


}
