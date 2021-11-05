package ltd.newbee.mall.entity.lottery.football;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ltd.newbee.mall.common.GameType;

import java.util.Date;
import java.util.List;

@ApiModel("订单")
@Data
@TableName("lottery_order")
@AllArgsConstructor
@NoArgsConstructor
public class LotteryOrder {


    @TableId(type = IdType.UUID)
    private String id;

    @ApiModelProperty("订单号")
    private String orderNo;

    /**
     * 订单类型
     */
    private GameType type;

    /**
     * 包括 排列3 排列5 大乐透 七星彩
     */
    @ApiModelProperty("排列3 排列5 大乐透 七星彩")
    private String numbers;

    @ApiModelProperty("主队编码")
    private String homeTeamCode;

    @ApiModelProperty("主队简称")
    private String homeTeamAbbName;

    @ApiModelProperty("客队编码")
    private String awayTeamCode;

    @ApiModelProperty("客队简称")
    private String awayTeamAbbName;

    @ApiModelProperty("比赛时间（周）： 周一001")
    private String matchNumStr;

    @ApiModelProperty("联赛编号， eg： 100")
    private Integer leagueId;




    @ApiModelProperty("倍数")
    private Integer times;

    @ApiModelProperty("玩法： 比如 3串1(3v1)")
    private String rule;

    private Byte uploadedImg;

    private Date orderDate;


    @ApiModelProperty("胜负平/让 胜平负")
    @TableField(exist = false)
    private OddsOrder oddsOrder;


    @ApiModelProperty("比分")
    @TableField(exist = false)
    private List<CrsOrder> crsOrders;


    @ApiModelProperty("进球数")
    @TableField(exist = false)
    private List<TtgOrder> ttgOrders;


    @ApiModelProperty("半全场")
    @TableField(exist = false)
    private List<HalfCourtOrder> halfCourtOrders;





}
