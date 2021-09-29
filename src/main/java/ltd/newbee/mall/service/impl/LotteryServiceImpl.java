package ltd.newbee.mall.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import ltd.newbee.mall.entity.common.PageCL;
import ltd.newbee.mall.entity.lottery.LoterryPl5;
import ltd.newbee.mall.entity.lottery.PrizeLevel;
import ltd.newbee.mall.service.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        String url = lotteryUrl + "/getHistoryPageListV1.qry?gameNo=" + gameNo + "&provinceId=" + provinceId + "&isVerify=" + isVerify + "&termLimits=" + termLimits;
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity responseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), Object.class);
        log.info(JSON.toJSONString(responseEntity.getBody()));
        return mapperToLotteryPl5(responseEntity);
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
