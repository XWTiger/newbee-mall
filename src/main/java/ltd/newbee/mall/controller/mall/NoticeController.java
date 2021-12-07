package ltd.newbee.mall.controller.mall;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import ltd.newbee.mall.common.ResultModel;
import ltd.newbee.mall.entity.common.Notice;
import ltd.newbee.mall.service.NoticeService;
import ltd.newbee.mall.util.SystemUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notice")
@Api(description = "用户消息接口")
@Slf4j
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @PostMapping("/notice/user/read")
    @ResponseBody
    public ResultModel read(@RequestParam("notice_id")  int noticeId) {
        ResultModel resultModel = new ResultModel();
        try {
            return noticeService.read(noticeId);
        } catch (Exception e) {
            log.error("notice set read failed", e);
            resultModel.setStatusCode(0);
            if (SystemUtil.isContainChinese(e.getMessage())) {
                resultModel.setResouce(e.getMessage());
            } else {
                resultModel.setStatusMes("设置已读失败");
            }
        }
        return resultModel;
    }
}
