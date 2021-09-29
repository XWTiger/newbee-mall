package ltd.newbee.mall.controller.mall;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ltd.newbee.mall.entity.common.PageCL;
import ltd.newbee.mall.entity.lottery.LoterryPl5;
import ltd.newbee.mall.service.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/lottery/sports/")
@Api(value = "体彩接口", description = "体彩接口")
public class SportLotteryController {

    @Autowired
    private LotteryService lotteryService;


    @GetMapping("pl5")
    @ApiOperation("获取排列五历史列表")
    @ResponseBody
    public PageCL<LoterryPl5> getPl5(@RequestParam("gameNo") String gameNo, @RequestParam("provinceId") String provinceId, @RequestParam("isVerify") String isVerify, @RequestParam("termLimits") String termLimits) {
        return  lotteryService.getLotteryPl5(gameNo, provinceId, isVerify, termLimits);
    }






}
