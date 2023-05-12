package wooryung.webFluxExample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import wooryung.webFluxExample.repository.UserHistoryRepository;
import wooryung.webFluxExample.repository.UserRepository;
import wooryung.webFluxExample.vo.IdUsernameVo;
import wooryung.webFluxExample.vo.UsernameHistoryVo;

import java.util.List;
import java.util.Map;

@Service
public class UsernameHistoryService {

    @Autowired
    private UserHistoryRepository userHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    public Mono<List<UsernameHistoryVo>> getUserHistoryList() {
        return userHistoryRepository.findAll().collectList().log("userHistoryList");
    }

    public Mono<Map<Long, String>> getIdUsernameMap(List<Long> userIdList) {
        return userRepository.findByIdIn(userIdList)
                .collectMap(IdUsernameVo::getId, IdUsernameVo::getUsername)
                .log("idUsernameMap");
    }
    
// User 테이블에서 userId에 해당하는 username을 하나씩 가져와 리스트로 생성하는 기존 코드 - DB에 계속 접근해야 하는 문제점
//    public Mono<List<String>> getUsernameList(List<Long> userIdList) {
//        return userRepository.findByIdIn(userIdList)
//                .collectList();
////        return Flux.fromIterable(userIdList)
////                .flatMap(userId -> usernameHistoryRepository.findUsernameById(userId)) // 하나씩 계속 가져오는 거 수정 -> 한 번에 다 가져오도록
////                .collectList();
//    }

}
