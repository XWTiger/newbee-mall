package ltd.newbee.mall.entity.lottery.football;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("（让）胜负平赔率")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Odds {

    @ApiModelProperty("负， 赔率")
    private String a;

    @ApiModelProperty("负赔率趋势： -1 下跌， 1 上涨， 0 不变")
    private String af;

    @ApiModelProperty("负赔率趋势： -1 下跌， 1 上涨， 0 不变")
    private String df;
    @ApiModelProperty("负赔率趋势： -1 下跌， 1 上涨， 0 不变")
    private String hf;

    @ApiModelProperty("负支持率")
    private String aRate;

    @ApiModelProperty("平， 赔率")
    private String d;
    @ApiModelProperty("平支持率")
    private String dRate;

    @ApiModelProperty("胜， 赔率")
    private String h;

    @ApiModelProperty("胜支持率")
    private String hRate;

    @ApiModelProperty("HHAD 代表客队， HAD 代表主队")
    private String poolCode;

    private String poolId;

    @ApiModelProperty("更新日期 eg: 2021-09-20")
    private String updateDate;


    @ApiModelProperty("更新时间 eg: 21:36:14")
    private String updateTime;

    @ApiModelProperty("让球数")
    private String goalLine;

}
