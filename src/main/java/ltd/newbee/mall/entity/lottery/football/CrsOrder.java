package ltd.newbee.mall.entity.lottery.football;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("比分订单")
@Data
@TableName("crs_order")
public class CrsOrder {

    @TableId(type = IdType.UUID)
    private String id;

    private String lotteryOrderId;

    @ApiModelProperty("比分 eg: 1:1 , 胜其它 传 other")
    private String core;

    @ApiModelProperty("比分赔率")
    private Double coreOdds;
}
