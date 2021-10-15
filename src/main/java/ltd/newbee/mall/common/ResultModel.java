package ltd.newbee.mall.common;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * 自定义返回结果
 */
public class ResultModel<T> {

    //状态码 1：成功 0：失败
    @ApiModelProperty(value = "状态码")
    @JsonProperty("status_code")
    private Integer statusCode = 1;
    //执行结果消息
    @ApiModelProperty(value = "执行结果消息")
    @JsonProperty("status_mes")
    private String statusMes = "执行成功";
    @ApiModelProperty(value = "返回内容")
    private T content;
    @ApiModelProperty(value = "资源")
    private String resouce;

    public Integer getStatusCode() {
        return statusCode;
    }
    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
        if(0 == statusCode){
            this.statusMes = "执行失败";
        }
    }
    public String getStatusMes() {
        return statusMes;
    }
    public void setStatusMes(String statusMes) {
        this.statusMes = statusMes;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }


    public String getResouce() {
        return resouce;
    }

    public void setResouce(String resouce) {
        this.resouce = resouce;
    }
}
