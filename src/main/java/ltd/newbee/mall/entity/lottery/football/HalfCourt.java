package ltd.newbee.mall.entity.lottery.football;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ltd.newbee.mall.annotation.ObjectScan;

@ApiModel("半场胜平负")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ObjectScan("hafu")
public class HalfCourt {

    @ApiModelProperty("负负")
    private String aa;

    private String aaf;

    @ApiModelProperty("负平")
    private String ad;

    private String adf;

    @ApiModelProperty("负胜")
    private String ah;
    private String ahf;

    @ApiModelProperty("平负")
    private String da;
    private String daf;

    @ApiModelProperty("平平")
    private String dd;
    private String ddf;

    @ApiModelProperty("平胜")
    private String dh;
    private String dhf;
    private String goalLine;

    @ApiModelProperty("胜负")
    private String ha;
    private String haf;

    @ApiModelProperty("胜平")
    private String hd;
    private String hdf;

    @ApiModelProperty("胜胜")
    private String hh;
    private String hhf;
    private Integer id;
    private String updateDate;
    private String updateTime;
}
