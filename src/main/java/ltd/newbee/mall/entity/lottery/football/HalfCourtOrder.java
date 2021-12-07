package ltd.newbee.mall.entity.lottery.football;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("半全场")
@Data
@TableName("half_court_order")
public class HalfCourtOrder {

    @TableId(type = IdType.UUID)
    private String id;

    private String lotteryOrderId;


    @ApiModelProperty("半全场： 胜w， 平d 负l； ww（胜胜） ， wd (胜平)， dl(平负) ， ll(负负), 类推")
    private String detail;

    @ApiModelProperty("半全场赔率")
    private Double detailOdds;

}
