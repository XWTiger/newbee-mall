package ltd.newbee.mall.entity.lottery.football;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ltd.newbee.mall.annotation.ObjectScan;

@ApiModel("总进球数")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ObjectScan("ttg")
public class Ttg {


    private String goalLine;

    @ApiModelProperty("进球 0")
    private String s0;
    private String s0f;
    private String s1;
    private String s1f;
    private String s2;
    private String s2f;
    private String s3;
    private String s3f;
    private String s4;
    private String s4f;
    private String s5;
    private String s5f;
    private String s6;
    private String s6f;

    @ApiModelProperty("进球7+")
    private String s7;
    private String s7f;
    private String updateDate;
    private String updateTime;
}
