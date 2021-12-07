package ltd.newbee.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import ltd.newbee.mall.common.ResultModel;
import ltd.newbee.mall.dao.NoticeMapper;
import ltd.newbee.mall.entity.common.Notice;
import ltd.newbee.mall.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public ResultModel noticeUser(Notice notice) {
        noticeMapper.insert(notice);
        return new ResultModel().generateSuccess(notice);
    }

    @Override
    public ResultModel getNoticeByUserId(Integer userId, Integer type) {
        List<Notice> notices = noticeMapper.selectList(new QueryWrapper<Notice>().eq("relate_id", userId).eq("type", type).eq("already_read", 0));
        ResultModel resultModel = new ResultModel();
        resultModel.setContent(notices);
        return resultModel;
    }

    @Override
    public ResultModel read(int noticeId) {
        Notice notice = noticeMapper.selectById(noticeId);
        if (Objects.nonNull(notice)) {
            noticeMapper.update(notice, new UpdateWrapper<Notice>().eq("relate_id", noticeId).set(true, "already_read", 1));
        }
        return new ResultModel().generateSuccess(notice);
    }
}
