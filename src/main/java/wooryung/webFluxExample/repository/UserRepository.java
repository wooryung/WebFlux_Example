package wooryung.webFluxExample.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import wooryung.webFluxExample.vo.IdUsernameVo;

import java.util.List;

@Repository
public interface UserRepository extends ReactiveCrudRepository<IdUsernameVo, Long> {
    Flux<IdUsernameVo> findByIdIn(List<Long> userIdList);
}
