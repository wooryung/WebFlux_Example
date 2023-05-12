package wooryung.webFluxExample.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import wooryung.webFluxExample.vo.UsernameHistoryVo;

@Repository
public interface UserHistoryRepository extends ReactiveCrudRepository<UsernameHistoryVo, Long> {
}
