package wooryung.webFluxExample.vo;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Table("UserHistory")
public class UsernameHistoryVo {
    @Column("userId")
    private Long userId;
    private String username;
    private LocalDateTime date;
    private String stat;
    private Instant created;
}
