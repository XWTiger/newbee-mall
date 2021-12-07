package ltd.newbee.mall.service;

import ltd.newbee.mall.common.ResultModel;
import ltd.newbee.mall.entity.common.Notice;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface NoticeService {
    ResultModel noticeUser(@RequestBody Notice notice);

    ResultModel getNoticeByUserId(@RequestParam("userId") Integer userId, @RequestParam("type") Integer type);

    ResultModel read(int noticeId) throws Exception;
}
