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
@ApiModel("普通用户欢乐豆")
@NoArgsConstructor
@TableName("user_happy_value")
public class UserHappyNum {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("欢乐豆")
    private  Integer happyNum;

    @ApiModelProperty("用户id")
    private Long relateId;

}
