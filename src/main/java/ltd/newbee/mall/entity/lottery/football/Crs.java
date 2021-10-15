package ltd.newbee.mall.entity.lottery.football;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ltd.newbee.mall.annotation.ObjectScan;

@ApiModel("比分")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ObjectScan("crs")
public class Crs {
   private String goalLine;
   @ApiModelProperty("0:0 赔率")
    private String s00s00;
    private String s00s00f;
    private String s00s01;

    private String s00s01f;
    private String s00s02;
    private String s00s02f;
    private String s00s03;
    private String s00s03f;
    private String s00s04;
    private String s00s04f;
    private String s00s05;
    private String s00s05f;
    private String s01s00;

    private String s01s00f;
    private String s01s01;
    private String s01s01f;
    private String s01s02;
    private String s01s02f;
    private String s01s03;
    private String s01s03f;
    private String s01s04;
    private String s01s04f;
    private String s01s05;
    private String s01s05f;

    @ApiModelProperty("负 其他比分，赔率")
    private String s1sa;
    private String s1saf;

    @ApiModelProperty("平 其他比分，赔率")
    private String s1sd;
    private String s1sdf;

    @ApiModelProperty("胜 其他比分，赔率")
    private String s1sh;
    private String s1shf;
    private String s02s00;
    private String s02s00f;
    private String s02s0;
    private String s02s01f;
    private String s02s02;
    private String s02s02f;
    private String s02s03;
    private String s02s03f;
    private String s02s04;
    private String s02s04f;
    private String s02s05;
    private String s02s05f;
    private String s03s00;
    private String s03s00f;
    private String s03s01;
    private String s03s01f;
    private String s03s02;
    private String s03s02f;
    private String s03s03;
    private String s03s03f;
    private String s04s00;
    private String s04s00f;
    private String s04s01;
    private String s04s01f;
    private String s04s02;
    private String s04s02f;
    private String s05s00;
    private String s05s00f;
    private String s05s01;
    private String s05s01f;
    private String s05s02;
    private String s05s02f;
    private String updateDate;
    private String updateTime;
}
