package ltd.newbee.mall.entity.common;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CheckResult {
    private Claims claims;
    private Boolean success;
    private String errorCode;
}
