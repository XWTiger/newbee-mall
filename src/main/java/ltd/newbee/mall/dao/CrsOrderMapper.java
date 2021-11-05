package ltd.newbee.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ltd.newbee.mall.entity.lottery.football.CrsOrder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrsOrderMapper extends BaseMapper<CrsOrder> {
    int insertList(List<CrsOrder> list);
}
