package ltd.newbee.mall.entity.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("基础信息")
public class Base {
    @ApiModelProperty("总条数")
    private Integer totalCount;

    @ApiModelProperty("竞彩奖金更新时间")
    private String lastUpdateTime;
}
