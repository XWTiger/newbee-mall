package ltd.newbee.mall.entity.lottery.football;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("pool list obj")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pool {
    private Integer matchId;
    private Integer matchNum;
    private Integer poolId;

    private String poolCode;

    private String poolOddsType;

    private String poolStatus;

    private String fixedOddsgoalLine;

    private String poolCloseDate;

    private String poolCloseTime;

    private String sellInitialDate;

    private String sellInitialTime;

    private Integer cbtAllUp;

    private Integer cbtSingle;

    private Integer cbtValue;

    private Integer intAllUp;

    private Integer intSingle;

    private Integer intValue;

    private Integer vbtAllUp;

    private Integer vbtSingle;

    private Integer vbtValue;

    private Integer bettingSingle;

    private Integer bettingAllup;

    private Integer single;

    private Integer allUp;

    private String updateDate;

    private String updateTime;

}
