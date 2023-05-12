package wooryung.webFluxExample.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import wooryung.webFluxExample.consts.ResCode;
import wooryung.webFluxExample.vo.UsernameHistoryVo;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetUsernameHistoryRespDto {
    private List<UsernameHistoryVo> usernameHistoryList;
    private int code = ResCode.SUCCESS.value();
    private String message;
}
