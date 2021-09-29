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
@ApiModel("足球场次信息")
public class FootBallList {

    @ApiModelProperty("比赛时间, eg: 2021-09-29")
    private String businessDate;

    @ApiModelProperty("数量")
    private Integer matchCount;

    @ApiModelProperty("星期： eg：周三")
    private String weekday;

    private List<FootBallMatch> subMatchList;
}
