package ltd.newbee.mall.controller.mall;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import ltd.newbee.mall.common.GameType;
import ltd.newbee.mall.common.ResultModel;
import ltd.newbee.mall.entity.common.PageCL;
import ltd.newbee.mall.entity.lottery.LoterryPl5;
import ltd.newbee.mall.entity.lottery.football.FootBallGames;
import ltd.newbee.mall.entity.lottery.football.FootBallMatchResult;
import ltd.newbee.mall.service.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResultModel<FootBallGames> getWin() {
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
    public ResultModel<FootBallGames> getScore() {
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
    public ResultModel<FootBallGames> getMixed() {
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
    public ResultModel<FootBallGames> getTotalScore() {
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
    public ResultModel<FootBallGames> getHalfCourt() {
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


    @GetMapping("jc/result")
    @ApiOperation("足球比赛结果")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "matchBeginDate", value = "比赛开始时间", required = true, paramType = "query"),
            @ApiImplicitParam(name = "matchEndDate", value = "比赛结束时间", required = true, paramType = "query"),
            @ApiImplicitParam(name = "leagueId", value = "联赛编号", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "页数", required = true, paramType = "query")
    })
    public ResultModel<List<FootBallMatchResult>> getFootballMatchResult(@RequestParam(" matchBeginDate") String matchBeginDate, @RequestParam("matchEndDate") String  matchEndDate,
                                                                         @RequestParam(value = "leagueId", required = false) String leagueId, @RequestParam("pageSize") Integer pageSize, @RequestParam("pageNo") Integer pageNo) {
        ResultModel<List<FootBallMatchResult>> resultModel = new ResultModel();
        try {
            resultModel.setContent(lotteryService.getFootballMatchResult(matchBeginDate, matchEndDate, leagueId, pageSize, pageNo));
            return resultModel;
        } catch (Exception e) {
            log.error("getScore  failed", e);
            resultModel.setStatusMes("接口请求失败");
        }
        return resultModel;
    }



}
