package wooryung.webFluxExample.vo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("User")
public class IdUsernameVo {
    @Id
    private Long id;
    private String username;
}
