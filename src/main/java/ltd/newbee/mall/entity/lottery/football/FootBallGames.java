package ltd.newbee.mall.entity.lottery.football;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ltd.newbee.mall.entity.lottery.Base;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("足球场次信息")
public class FootBallGames extends Base {


    @ApiModelProperty("匹配联赛列表")
    private List<FootBallList> matchInfoList;

    @ApiModelProperty("匹配时间列表")
    private List<MatchDate> matchDateList;

    @ApiModelProperty("联赛列表")
    private List<League> leagueList;
}
