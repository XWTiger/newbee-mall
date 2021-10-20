package ltd.newbee.mall.service;

import ltd.newbee.mall.common.GameType;
import ltd.newbee.mall.entity.common.PageCL;
import ltd.newbee.mall.entity.lottery.LoterryPl5;
import ltd.newbee.mall.entity.lottery.football.FootBallGames;
import ltd.newbee.mall.entity.lottery.football.FootBallMatchResult;

import java.util.List;

/**
 * this is for lottery service， just request official api
 * 大乐透
 */

public interface LotteryService {


    //排列5

    /**
     * @param gameNo 玩法编号
     * @param provinceId 省份id
     * @param isVerify 1
     * @param termLimits 一次查几个
     * @return
     */
    PageCL<LoterryPl5> getLotteryPl5(String gameNo, String provinceId, String isVerify, String termLimits);


    FootBallGames getVictoryOrDefeatFootBallGames(GameType gameType) throws Exception;


    List<FootBallMatchResult> getFootballMatchResult(String matchBeginDate, String  matchEndDate, String leagueId, Integer pageSize, Integer pageNo) throws Exception;



}
