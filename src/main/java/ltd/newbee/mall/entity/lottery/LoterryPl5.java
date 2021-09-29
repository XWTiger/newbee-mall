package ltd.newbee.mall.entity.lottery;

/**
 * 排列5 列表
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * {
 * "dataFrom": "",
 * "emptyFlag": false,
 * "errorCode": "0",
 * "errorMessage": "处理成功",
 * "success": true,
 * "value": {
 * "list": [
 * {
 * "drawFlowFund": "0",
 * "drawFlowFundRj": "",
 * "estimateDrawTime": "",
 * "isGetKjpdf": 1,
 * "isGetXlpdf": 2,
 * "lotteryDrawNum": "21243",
 * "lotteryDrawResult": "0 9 7 4 2",
 * "lotteryDrawStatus": 20,
 * "lotteryDrawTime": "2021-09-10",
 * "lotteryEquipmentCount": 1,
 * "lotteryGameName": "排列5",
 * "lotteryGameNum": "350133",
 * "lotteryGamePronum": 0,
 * "lotteryPaidBeginTime": "2021-09-10 22:30:01",
 * "lotteryPaidEndTime": "2021-11-09 23:59:59",
 * "lotteryPromotionFlag": 0,
 * "lotterySaleBeginTime": "2021-09-09 20:10:00",
 * "lotterySaleEndTimeUnix": 0,
 * "lotterySaleEndtime": "2021-09-10 20:00:00",
 * "lotterySuspendedFlag": 0,
 * "lotteryUnsortDrawresult": "0 9 7 4 2",
 * "matchList": [
 * <p>
 * ],
 * "pdfType": 1,
 * "poolBalanceAfterdraw": "366,326,160.38",
 * "poolBalanceAfterdrawRj": "",
 * "prizeLevelList": [
 * {
 * "awardType": 0,
 * "group": "1010",
 * "lotteryCondition": "",
 * "prizeLevel": "一等奖",
 * "sort": 10,
 * "stakeAmount": "100,000",
 * "stakeCount": "147",
 * "totalPrizeamount": "14,700,000"
 * }
 * ],
 * "prizeLevelListRj": [
 * <p>
 * ],
 * "ruleType": 0,
 * "termList": [
 * <p>
 * ],
 * "totalSaleAmount": "15,832,808",
 * "totalSaleAmountRj": "",
 * "verify": 1,
 * "vtoolsConfig": {
 * <p>
 * }
 * }
 * ],
 * "pageNo": 1,
 * "pageSize": 5,
 * "pages": 1,
 * "total": 5
 * }
 * }
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("排列5")
public class LoterryPl5 extends LotterryResultBase {


    @ApiModelProperty("流动资金")
    private String drawFlowFund;
    private String drawFlowFundRj;
    private String estimateDrawTime;
    private Integer isGetKjpdf;
    private Integer isGetXlpdf;

    private String lotteryDrawStatus;

    private Integer lotteryGamePronum;


    private Integer lotteryPromotionFlag;

    @ApiModelProperty("支付开始时间")
    private String lotteryPaidBeginTime;

    @ApiModelProperty("支付结束时间")
    private String lotteryPaidEndTime;
    private Integer lotterySaleEndTimeUnix;



    private Integer lotterySuspendedFlag;
    private String lotteryUnsortDrawresult;

    private List<String> matchList;


    @ApiModelProperty("中奖情况")
    private List<PrizeLevel> prizeLevelList;

    @ApiModelProperty("期数列表")
    private List<String> termList;


    @ApiModelProperty("总销售金额")
    private String totalSaleAmount;


    private Integer verify;

}
