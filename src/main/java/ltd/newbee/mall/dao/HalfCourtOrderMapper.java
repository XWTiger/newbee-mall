package ltd.newbee.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ltd.newbee.mall.entity.lottery.football.HalfCourtOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HalfCourtOrderMapper extends BaseMapper<HalfCourtOrder> {
    int insertList(@Param("list") List<HalfCourtOrder> list);
}
