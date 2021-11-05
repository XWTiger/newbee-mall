package ltd.newbee.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ltd.newbee.mall.entity.lottery.football.TtgOrder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TtgOrderMapper extends BaseMapper<TtgOrder> {
    int insertList(List<TtgOrder> list);
}
