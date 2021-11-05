package ltd.newbee.mall.entity.common;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("通知")
@Data
@TableName("notice")
public class Notice {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("通知内容")
    private String noticeContent;

    @ApiModelProperty("0 普通用户 1 管理员")
    private Byte type;

    @ApiModelProperty("0 未读 1 已读")
    private Byte already_read;

    @ApiModelProperty("关联用户id")
    private Long relate_id;

}
