package ltd.newbee.mall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@ApiModel("管理员积分")
@NoArgsConstructor
@TableName("admin_integral")
public class AdminIntegral {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("管理员id")
    private Long admin_user_id;

    @ApiModelProperty("积分")
    private Long integral;

    @ApiModelProperty("备注")
    private String comment;

}
