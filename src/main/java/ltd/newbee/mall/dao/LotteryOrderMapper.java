package ltd.newbee.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ltd.newbee.mall.entity.lottery.football.LotteryOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LotteryOrderMapper extends BaseMapper<LotteryOrder> {
    int insertList(@Param("list") List<LotteryOrder> list);
}
