package ltd.newbee.mall.entity.lottery.football;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("比赛时间")
public class MatchDate {

    @ApiModelProperty("比赛时间")
    private String businessDate;

    @ApiModelProperty("星期几， eg： 周一")
    private String businessDateCn;
}
