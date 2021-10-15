package ltd.newbee.mall.controller.mall;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import ltd.newbee.mall.common.GameType;
import ltd.newbee.mall.common.ResultModel;
import ltd.newbee.mall.entity.common.PageCL;
import ltd.newbee.mall.entity.lottery.LoterryPl5;
import ltd.newbee.mall.service.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/lottery/sports/")
@Api(value = "体彩接口", description = "体彩接口")
@Slf4j
public class SportLotteryController {

    @Autowired
    private LotteryService lotteryService;


    @GetMapping("pl5")
    @ApiOperation("获取排列五历史列表")
    @ResponseBody
    public ResultModel<PageCL<LoterryPl5>> getPl5(@RequestParam("gameNo") String gameNo, @RequestParam("provinceId") String provinceId, @RequestParam("isVerify") String isVerify, @RequestParam("termLimits") String termLimits) {
        ResultModel resultModel = new ResultModel();
        try {
            resultModel.setContent(lotteryService.getLotteryPl5(gameNo, provinceId, isVerify, termLimits));
            return resultModel;
        } catch (Exception e) {
            log.error("lottery pl5 failed", e);
            resultModel.setStatusMes("接口请求失败");
        }
        return resultModel;
    }


    @GetMapping("jc/victory")
    @ApiOperation("足球比赛胜负")
    @ResponseBody
    public ResultModel getWin() {
        ResultModel resultModel = new ResultModel();
        try {
            resultModel.setContent(lotteryService.getVictoryOrDefeatFootBallGames(GameType.VICTORY));
            return resultModel;
        } catch (Exception e) {
            log.error("VictoryOrDefeat  failed", e);
            resultModel.setStatusMes("接口请求失败");
        }
        return resultModel;
    }


    @GetMapping("jc/score")
    @ApiOperation("足球比赛比分")
    @ResponseBody
    public ResultModel getScore() {
        ResultModel resultModel = new ResultModel();
        try {
            resultModel.setContent(lotteryService.getVictoryOrDefeatFootBallGames(GameType.SCORE));
            return resultModel;
        } catch (Exception e) {
            log.error("getScore  failed", e);
            resultModel.setStatusMes("接口请求失败");
        }
        return resultModel;
    }

    @GetMapping("jc/mixed")
    @ApiOperation("混合过关")
    @ResponseBody
    public ResultModel getMixed() {
        ResultModel resultModel = new ResultModel();
        try {
            resultModel.setContent(lotteryService.getVictoryOrDefeatFootBallGames(GameType.MIXED));
            return resultModel;
        } catch (Exception e) {
            log.error("getScore  failed", e);
            resultModel.setStatusMes("接口请求失败");
        }
        return resultModel;
    }

    @GetMapping("jc/total-score")
    @ApiOperation("总进球")
    @ResponseBody
    public ResultModel getTotalScore() {
        ResultModel resultModel = new ResultModel();
        try {
            resultModel.setContent(lotteryService.getVictoryOrDefeatFootBallGames(GameType.TOTAL_SCORE));
            return resultModel;
        } catch (Exception e) {
            log.error("getScore  failed", e);
            resultModel.setStatusMes("接口请求失败");
        }
        return resultModel;
    }

    @GetMapping("jc/half-court")
    @ApiOperation("半场胜平负")
    @ResponseBody
    public ResultModel getHalfCourt() {
        ResultModel resultModel = new ResultModel();
        try {
            resultModel.setContent(lotteryService.getVictoryOrDefeatFootBallGames(GameType.HALF_COURT));
            return resultModel;
        } catch (Exception e) {
            log.error("getScore  failed", e);
            resultModel.setStatusMes("接口请求失败");
        }
        return resultModel;
    }






}
