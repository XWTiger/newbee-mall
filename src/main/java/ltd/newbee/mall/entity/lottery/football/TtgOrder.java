package ltd.newbee.mall.entity.lottery.football;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("总进球数")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("total_goal")
public class TtgOrder {
    @TableId(type = IdType.UUID)
    private String id;

    private String lotteryOrderId;

    @ApiModelProperty("进球数 1 一球， 7+ 七球以及以上")
    private String goal;

    @ApiModelProperty("赔率")
    private Double goalOdds;
}
