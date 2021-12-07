package ltd.newbee.mall.entity.common;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel("通知")
@Data
@TableName("notice")
public class Notice {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @NotEmpty(message = "通知内容不能为空")
    @ApiModelProperty("通知内容")
    private String noticeContent;

    @NotNull(message = "用户类型不能为空")
    @ApiModelProperty("0 普通用户 1 管理员")
    private Byte type;

    @ApiModelProperty("0 未读 1 已读")
    private Byte alreadyRead;

    @ApiModelProperty("关联用户id")
    @NotNull(message = "关联用户id 不能为空")
    private Long relateId;

    @ApiModelProperty("创建时间")
    private Date createTime = new Date();
}
