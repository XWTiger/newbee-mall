package ltd.newbee.mall.entity.common;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("image upload")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("img_order")
public class ImgOrder {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String imgAddress;
    private Byte deleted;
    private String lotteryOrderId;

}
