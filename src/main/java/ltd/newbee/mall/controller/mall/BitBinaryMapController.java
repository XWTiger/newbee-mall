package ltd.newbee.mall.controller.mall;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import ltd.newbee.mall.common.ResultModel;
import ltd.newbee.mall.util.QRCodeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("/bit-binary-map/")
@Api(value = "", description = "二维码")
@Slf4j
public class BitBinaryMapController {

    @GetMapping("user-app")
    @ApiOperation("获取排列五历史列表")
    @ResponseBody
    public ResultModel getUserBitBinMap(@RequestParam("shop_id") String shopId, HttpServletResponse response) {
        String url = "http://192.168.5.159:28089//bit-binary-map/test?shop_id=" + shopId;
        String imgLogo = "E:\\opt\\log.jpg";
        String dePath = "E:\\opt\\";
        try {
           String imgLocation =  QRCodeUtil.encode(url,imgLogo,dePath, true);
            File file = new File(dePath + imgLocation);
            FileInputStream fileInputStream = null;
            OutputStream os = null;
            try {
                fileInputStream = new FileInputStream(file);
                os = response.getOutputStream();
                byte[] buffer = new byte[1024 * 1024];
                int count = 0;
                while ((count = fileInputStream.read(buffer)) != -1) {
                    os.write(buffer, 0, count);
                }
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if ( fileInputStream != null) {
                        fileInputStream.close();
                    }
                    if (os != null) {
                        os.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResultModel();
    }

    @GetMapping("test")
    @ApiOperation("获取排列五历史列表")
    @ResponseBody
    public ResultModel getTest() {
        System.out.println("=======================================");
        return new ResultModel();
    }
}
