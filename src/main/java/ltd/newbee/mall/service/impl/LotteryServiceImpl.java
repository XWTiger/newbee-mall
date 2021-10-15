package ltd.newbee.mall.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import ltd.newbee.mall.annotation.ObjectScan;
import ltd.newbee.mall.common.GameType;
import ltd.newbee.mall.entity.common.PageCL;
import ltd.newbee.mall.entity.lottery.LoterryPl5;
import ltd.newbee.mall.entity.lottery.PrizeLevel;
import ltd.newbee.mall.entity.lottery.football.*;
import ltd.newbee.mall.service.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.util.MapUtils;
import org.thymeleaf.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

import static ltd.newbee.mall.common.GameType.VICTORY;

@Service
@Slf4j
public class LotteryServiceImpl implements LotteryService {

    @Autowired
    private RestTemplate restTemplate;
    @Value("${lottery.api.url}")
    private String lotteryUrl;

    @Override
    public PageCL<LoterryPl5> getLotteryPl5(String gameNo, String provinceId, String isVerify, String termLimits) {

        //https://webapi.sporttery.cn/gateway/lottery/getHistoryPageListV1.qry?gameNo=350133&provinceId=0&isVerify=1&termLimits=5
        String url = lotteryUrl + "/lottery/getHistoryPageListV1.qry?gameNo=" + gameNo + "&provinceId=" + provinceId + "&isVerify=" + isVerify + "&termLimits=" + termLimits;
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity responseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), Object.class);
        log.info(JSON.toJSONString(responseEntity.getBody()));
        return mapperToLotteryPl5(responseEntity);
    }

    @Override
    public FootBallGames getVictoryOrDefeatFootBallGames(GameType gameType) throws Exception {
        //https://webapi.sporttery.cn/gateway/jc/football/getMatchCalculatorV1.qry?poolCode=hhad,had&channel=c
        String url = null;
        switch (gameType) {
            case VICTORY:
                url = lotteryUrl + "/jc/football/getMatchCalculatorV1.qry?poolCode=hhad,had&channel=c";
                break;
            case SCORE:
                url = lotteryUrl + "/jc/football/getMatchCalculatorV1.qry?poolCode=crs&channel=c";
                break;
            case TOTAL_SCORE:
                url = lotteryUrl + "/jc/football/getMatchCalculatorV1.qry?poolCode=ttg&channel=c";
                break;
            case HALF_COURT:
                url = lotteryUrl + "/jc/football/getMatchCalculatorV1.qry?poolCode=hafu&channel=c";
                break;
            case MIXED:
                url = lotteryUrl + "/jc/football/getMatchCalculatorV1.qry?poolCode=&channel=c";
                break;
                default:
                    log.error("=============> no game type  matched");
                    url = "";
        }
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity responseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        log.info(JSON.toJSONString(responseEntity.getBody()));
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new Exception("竞彩接口请求失败");
        }

        //support rate

        Map<String, FootBallMatch> matchIds = new HashMap();
        FootBallGames footBallGames = mapperToVictoryOrDefeatFootBallGames(responseEntity, matchIds);
        getSupportRate(matchIds);
        return footBallGames;
    }

    public  void  getSupportRate( Map<String, FootBallMatch> matchIds) {

        if (!MapUtils.isEmpty(matchIds)) {
            String queryParams = "matchIds=";
            StringBuffer stringBuffer = new StringBuffer("matchIds=");

            matchIds.forEach((s, footBallMatch) -> {
                stringBuffer.append(s + ",");
            });

            queryParams = stringBuffer.toString();

            queryParams = queryParams.substring(0, queryParams.length() - 1);

            String rateUrl = lotteryUrl + "/jc/common/getSupportRateV1.qry?" + queryParams + "&poolCode=hhad,had&sportType=1";

            HttpHeaders headers = new HttpHeaders();
            ResponseEntity rateResponse = restTemplate.exchange(rateUrl, HttpMethod.GET, new HttpEntity<>(headers), Object.class);
            log.info(JSON.toJSONString(rateResponse.getBody()));
            if (rateResponse.getStatusCode() == HttpStatus.OK) {

                JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(rateResponse.getBody()));
                JSONObject values = jsonObject.getJSONObject("value");
                matchIds.forEach((s, footBallMatch) -> {
                    JSONObject rateJson = values.getJSONObject("_" + s);
                    if (Objects.isNull(rateJson)) {
                        return;
                    }
                    Odds had = footBallMatch.getHad();
                    JSONObject hadJson = rateJson.getJSONObject("HAD");
                    if (Objects.nonNull(hadJson) && hadJson.size() > 0) {
                        had.setARate(hadJson.getString("aSupportRate"));
                        had.setDRate(hadJson.getString("dSupportRate"));
                        had.setHRate(hadJson.getString("hSupportRate"));
                    } else {
                        footBallMatch.setHad(null);
                    }
                    Odds hhad = footBallMatch.getHhad();
                    JSONObject hhadJson = rateJson.getJSONObject("HHAD");
                    if (Objects.nonNull(hhadJson) && hhadJson.size() > 0) {
                        hhad.setARate(hhadJson.getString("aSupportRate"));
                        hhad.setDRate(hhadJson.getString("dSupportRate"));
                        hhad.setHRate(hhadJson.getString("hSupportRate"));
                    } else {
                        footBallMatch.setHhad(null);
                    }
                });

            }
        }
    }

    public FootBallGames mapperToVictoryOrDefeatFootBallGames(ResponseEntity responseEntity, Map<String, FootBallMatch> matchIds) throws Exception {
        FootBallGames footBallGames = new FootBallGames();
        JSONObject jsonObject = JSON.parseObject((String) responseEntity.getBody());
        if (!"0".equals(jsonObject.getString("errorCode"))) {
            throw new Exception("竞彩接口请求失败");
        }
        JSONObject values = jsonObject.getJSONObject("value");
        JSONArray leagueList = values.getJSONArray("leagueList");
        JSONArray matchDateList = values.getJSONArray("matchDateList");
        JSONArray matchInfoList = values.getJSONArray("matchInfoList");

        List<FootBallList> matchInfoLists = new ArrayList<>();
        List<MatchDate> matchDateList1 = new ArrayList<>();
        List<League> leagues = new ArrayList<>();

        if (matchDateList.size() > 0) {
            for (int i = 0; i < matchDateList.size(); i++) {
                JSONObject jsonObject1 = matchDateList.getJSONObject(i);
                MatchDate matchDate = new MatchDate();
                matchDate.setBusinessDate(jsonObject1.getString("businessDate"));
                matchDate.setBusinessDateCn(jsonObject1.getString("businessDateCn"));
                matchDateList1.add(matchDate);
            }
        }

        if (leagueList.size() > 0) {
            for (int i = 0; i < leagueList.size(); i++) {
                JSONObject jsonObject1 = leagueList.getJSONObject(i);
                League league = new League();
                league.setLeagueId(jsonObject.getString("leagueId"));
                league.setLeagueName(jsonObject.getString("leagueName"));
                league.setLeagueNameAbbr(jsonObject.getString("leagueNameAbbr"));
                leagues.add(league);
            }
        }

        if (matchInfoList.size() > 0) {
            for (int i = 0; i < matchInfoList.size(); i++) {
                JSONObject jsonObject1 = matchInfoList.getJSONObject(i);
                FootBallList footBallList = new FootBallList();
                footBallList.setBusinessDate(jsonObject1.getString("businessDate"));
                footBallList.setMatchCount(jsonObject1.getInteger("matchCount"));
                footBallList.setWeekday(jsonObject1.getString("weekday"));
                List<FootBallMatch> subMatchList = new ArrayList<>();

                JSONArray subMatchs = jsonObject1.getJSONArray("subMatchList");
                for (int j = 0; j < subMatchs.size(); j++) {
                    JSONObject subJsonMatch = subMatchs.getJSONObject(j);
                    FootBallMatch footBallMatch = new FootBallMatch();
                    //query some common properties
                    getBaseSubMatchInfo(footBallMatch, subJsonMatch);
                    JSONArray oddsJson = subJsonMatch.getJSONArray("oddsList");
                    //win or defeat
                    if (oddsJson.size() > 0) {
                        footBallMatch.setOddsList(getOdds(oddsJson));
                    }
                    JSONArray poolList = subJsonMatch.getJSONArray("poolList");
                    if (poolList.size() > 0) {
                        footBallMatch.setPoolList(getPool(poolList));
                    }

                    JSONObject had = subJsonMatch.getJSONObject("had");
                    if (Objects.nonNull(had)) {
                        footBallMatch.setHad(getOdds(had));
                    }

                    JSONObject hhad = subJsonMatch.getJSONObject("hhad");
                    if (Objects.nonNull(hhad)) {
                        footBallMatch.setHhad(getOdds(hhad));
                    }
                    footBallMatch.setCrs((Crs) getObject(Crs.class, subJsonMatch));
                    if (Objects.nonNull(matchIds)) {
                        matchIds.put(footBallMatch.getMatchId(), footBallMatch);
                    }
                    footBallMatch.setTtg(getObject(Ttg.class, subJsonMatch));
                    footBallMatch.setHafu(getObject(HalfCourt.class, subJsonMatch));



                    subMatchList.add(footBallMatch);

                }
                footBallList.setSubMatchList(subMatchList);
                matchInfoLists.add(footBallList);
            }
        }
        footBallGames.setMatchDateList(matchDateList1);
        footBallGames.setLeagueList(leagues);
        footBallGames.setMatchInfoList(matchInfoLists);
        return footBallGames;

    }

    public <T> T getObject(Class clz, JSONObject jsonObject) throws IllegalAccessException, InstantiationException {

        //get object scan value to find destination  value
        ObjectScan objectScan = (ObjectScan) clz.getAnnotation(ObjectScan.class);
        String scanObj = objectScan.value();
        if (!StringUtils.isEmpty(scanObj)) {
            //csr
            JSONObject csrJsonObject = jsonObject.getJSONObject(scanObj);
            if (Objects.isNull(csrJsonObject) || csrJsonObject.size() <= 0) {
                return null;
            }
            T t = (T) clz.newInstance();
            Field[] fields = t.getClass().getDeclaredFields();
            for (Field field : fields) {
                //TODO more type to do
                field.setAccessible(true);
                if (field.getType().getName().equals("java.lang.String")) {
                    field.set(t,  csrJsonObject.getString(field.getName()));
                }
                if (field.getType().getName().equals("java.lang.Integer")) {
                    field.set(t,  csrJsonObject.getInteger(field.getName()));
                }
                if (field.getType().getName().equals("java.lang.Double")) {
                    field.set(t,  csrJsonObject.getDouble(field.getName()));
                }

            }
            return t;
        }
        return null;
    }

    public List<Pool> getPool(JSONArray jsonArray) {
        if (jsonArray.size() <= 0) {
            return Collections.EMPTY_LIST;
        }
        List<Pool> pools = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            Pool pool = new Pool();
            pool.setAllUp(jsonObject1.getInteger("allUp"));
            pool.setBettingAllup(jsonObject1.getInteger("bettingAllup"));
            pool.setBettingSingle(jsonObject1.getInteger("bettingSingle"));
            pool.setCbtAllUp(jsonObject1.getInteger("cbtAllUp"));
            pool.setCbtSingle(jsonObject1.getInteger("cbtSingle"));
            pool.setCbtValue(jsonObject1.getInteger("cbtValue"));
            pool.setFixedOddsgoalLine(jsonObject1.getString("fixedOddsgoalLine"));
            pool.setIntAllUp(jsonObject1.getInteger("intAllUp"));
            pool.setIntSingle(jsonObject1.getInteger("intSingle"));
            pool.setIntValue(jsonObject1.getInteger("intValue"));
            pool.setSellInitialDate(jsonObject1.getString("sellInitialDate"));
            pool.setSellInitialTime(jsonObject1.getString("sellInitialTime"));
            pool.setMatchId(jsonObject1.getInteger("matchId"));
            pool.setMatchNum(jsonObject1.getInteger("matchNum"));
            pool.setPoolCloseDate(jsonObject1.getString("poolCloseDate"));
            pool.setPoolCloseTime(jsonObject1.getString("poolCloseTime"));
            pool.setPoolCode(jsonObject1.getString("poolCode"));
            pool.setPoolId(jsonObject1.getInteger("poolId"));
            pool.setPoolOddsType(jsonObject1.getString("poolOddsType"));
            pool.setPoolStatus(jsonObject1.getString("poolStatus"));
            pool.setSingle(jsonObject1.getInteger("single"));
            pool.setVbtAllUp(jsonObject1.getInteger("vbtAllUp"));
            pool.setVbtSingle(jsonObject1.getInteger("vbtSingle"));
            pool.setVbtValue(jsonObject1.getInteger("vbtValue"));
            pool.setUpdateDate(jsonObject1.getString("updateDate"));
            pool.setUpdateTime(jsonObject1.getString("updateTime"));
            pools.add(pool);
        }
        return pools;
    }

    /**
     * 胜负
     *
     * @param jsonArray
     * @return
     */
    public List<Odds> getOdds(JSONArray jsonArray) {
        if (jsonArray.size() <= 0) {
            return Collections.EMPTY_LIST;
        }
        List<Odds> odds = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            Odds odd = new Odds();
            odd.setA(jsonObject1.getString("a"));
            odd.setD(jsonObject1.getString("d"));
            odd.setH(jsonObject1.getString("h"));
            odd.setAf(jsonObject1.getString("af"));
            odd.setDf(jsonObject1.getString("df"));
            odd.setHf(jsonObject1.getString("hf"));
            odd.setGoalLine(jsonObject1.getString("goalLine"));
            odd.setPoolCode(jsonObject1.getString("poolCode"));
            odd.setPoolId(jsonObject1.getString("poolId"));
            odds.add(odd);
        }
        return odds;
    }

    public Odds getOdds(JSONObject jsonObject1) {
        Odds odd = new Odds();
        odd.setA(jsonObject1.getString("a"));
        odd.setD(jsonObject1.getString("d"));
        odd.setH(jsonObject1.getString("h"));
        odd.setAf(jsonObject1.getString("af"));
        odd.setDf(jsonObject1.getString("df"));
        odd.setHf(jsonObject1.getString("hf"));
        odd.setGoalLine(jsonObject1.getString("goalLine"));
        odd.setPoolCode(jsonObject1.getString("poolCode"));
        odd.setPoolId(jsonObject1.getString("poolId"));
        return odd;
    }

    public void getBaseSubMatchInfo(FootBallMatch footBallMatch, JSONObject subJsonMatch) {
        footBallMatch.setAwayRank(subJsonMatch.getString("awayRank"));
        footBallMatch.setAwayTeamAbbEnName(subJsonMatch.getString("awayTeamAbbEnName"));
        footBallMatch.setAwayTeamAbbName(subJsonMatch.getString("awayTeamAbbName"));
        footBallMatch.setAwayTeamAllName(subJsonMatch.getString("awayTeamAllName"));
        footBallMatch.setAwayTeamCode(subJsonMatch.getString("awayTeamCode"));
        footBallMatch.setAwayTeamId(subJsonMatch.getInteger("awayTeamId"));
        footBallMatch.setBusinessDate(subJsonMatch.getString("businessDate"));
        footBallMatch.setHomeRank(subJsonMatch.getString("homeRank"));
        footBallMatch.setMatchNum(subJsonMatch.getInteger("matchNum"));
        footBallMatch.setMatchNumStr(subJsonMatch.getString("matchNumStr"));
        footBallMatch.setMatchWeek(subJsonMatch.getString("matchWeek"));
        footBallMatch.setLeagueId(subJsonMatch.getInteger("leagueId"));
        footBallMatch.setLeagueCode(subJsonMatch.getString("leagueCode"));
        footBallMatch.setLeagueAbbName(subJsonMatch.getString("leagueAbbName"));
        footBallMatch.setLeagueAllName(subJsonMatch.getString("leagueAllName"));
        footBallMatch.setHomeTeamId(subJsonMatch.getInteger("homeTeamId"));
        footBallMatch.setHomeTeamCode(subJsonMatch.getString("homeTeamCode"));
        footBallMatch.setHomeTeamAbbEnName(subJsonMatch.getString("homeTeamAbbEnName"));
        footBallMatch.setHomeTeamAbbName(subJsonMatch.getString("homeTeamAbbName"));
        footBallMatch.setHomeTeamAllName(subJsonMatch.getString("homeTeamAllName"));
        footBallMatch.setMatchDate(subJsonMatch.getString("matchDate"));
        footBallMatch.setMatchId(subJsonMatch.getString("matchId"));
        footBallMatch.setMatchStatus(subJsonMatch.getString("matchStatus"));
        footBallMatch.setMatchTime(subJsonMatch.getString("matchTime"));
        footBallMatch.setIsHide(subJsonMatch.getInteger("isHide"));
        footBallMatch.setIsHot(subJsonMatch.getInteger("isHot"));
        footBallMatch.setRemark(subJsonMatch.getString("remark"));
        footBallMatch.setSellStatus(subJsonMatch.getInteger("sellStatus"));
    }

    public PageCL<LoterryPl5> mapperToLotteryPl5(ResponseEntity responseEntity) {
        PageCL<LoterryPl5> page = new PageCL<>();
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(responseEntity.getBody()));
        JSONObject values = jsonObject.getJSONObject("value");
        JSONArray jsonArray = values.getJSONArray("list");
        page.setPageNum(values.getInteger("pageNo"));
        page.setPageSize(values.getInteger("pageSize"));
        page.setTotal(values.getInteger("total"));
        if (jsonArray.size() <= 0) {
            page.setList(Collections.emptyList());
            page.setTotal(0);
            page.setPageNum(1);
            return page;
        }
        List<LoterryPl5> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            LoterryPl5 loterryPl5 = new LoterryPl5();
            loterryPl5.setDrawFlowFund(obj.getString("drawFlowFund"));
            loterryPl5.setDrawFlowFundRj(obj.getString("drawFlowFundRj"));
            loterryPl5.setEstimateDrawTime(obj.getString("estimateDrawTime"));
            loterryPl5.setIsGetKjpdf(obj.getInteger("isGetKjpdf"));
            loterryPl5.setIsGetXlpdf(obj.getInteger("isGetXlpdf"));
            loterryPl5.setLotteryDrawNum(obj.getString("lotteryDrawNum"));
            loterryPl5.setLotteryDrawStatus(obj.getString("lotteryDrawStatus"));
            loterryPl5.setLotteryDrawResult(obj.getString("lotteryDrawResult"));
            loterryPl5.setLotteryDrawTime(obj.getString("lotteryDrawTime"));
            loterryPl5.setLotteryEquipmentCount(obj.getString("lotteryEquipmentCount"));
            loterryPl5.setLotteryGameName(obj.getString("lotteryGameName"));
            loterryPl5.setLotteryGameNum(obj.getString("lotteryGameNum"));
            loterryPl5.setLotteryGamePronum(obj.getInteger("lotteryGamePronum"));
            loterryPl5.setLotteryPaidBeginTime(obj.getString("lotteryPaidBeginTime"));
            loterryPl5.setLotteryPaidEndTime(obj.getString("lotteryPaidEndTime"));
            loterryPl5.setLotteryPromotionFlag(obj.getInteger("lotteryPromotionFlag"));
            loterryPl5.setLotterySaleBeginTime(obj.getString("lotterySaleBeginTime"));
            loterryPl5.setLotterySaleEndTimeUnix(obj.getInteger("lotterySaleEndTimeUnix"));
            loterryPl5.setLotterySaleEndtime(obj.getString("lotterySaleEndtime"));
            loterryPl5.setLotterySuspendedFlag(obj.getInteger("lotterySuspendedFlag"));
            loterryPl5.setPoolBalanceAfterdraw(obj.getString("poolBalanceAfterdraw"));
            List<PrizeLevel> prizeLevelList = new ArrayList<>();
            JSONArray prizeLevels = obj.getJSONArray("prizeLevelList");
            for (int j = 0; j < prizeLevels.size(); j++) {
                PrizeLevel prizeLevel = new PrizeLevel();
                JSONObject prizeLevelObj = prizeLevels.getJSONObject(j);
                prizeLevel.setAwardType(prizeLevelObj.getInteger("awardType"));
                prizeLevel.setGroup(prizeLevelObj.getString("group"));
                prizeLevel.setLotteryCondition(prizeLevelObj.getString("lotteryCondition"));
                prizeLevel.setPrizeLevel(prizeLevelObj.getString("prizeLevel"));
                prizeLevel.setSort(prizeLevelObj.getInteger("sort"));
                prizeLevel.setStakeAmount(prizeLevelObj.getString("stakeAmount"));
                prizeLevel.setStakeCount(prizeLevelObj.getString("stakeCount"));
                prizeLevel.setTotalPrizeamount(prizeLevelObj.getString("totalPrizeamount"));
                prizeLevelList.add(prizeLevel);
            }
            loterryPl5.setTotalSaleAmount(obj.getString("totalSaleAmount"));
            loterryPl5.setVerify(obj.getInteger("verify"));
            loterryPl5.setPrizeLevelList(prizeLevelList);
            list.add(loterryPl5);
        }
        page.setList(list);
        return page;
    }
}
