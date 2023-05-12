package wooryung.webFluxExample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import wooryung.webFluxExample.dto.GetUsernameHistoryRespDto;
import wooryung.webFluxExample.service.UsernameHistoryService;
import wooryung.webFluxExample.vo.UsernameHistoryVo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UsernameHistoryController {

    @Autowired
    private UsernameHistoryService usernameHistoryService;

    @GetMapping("/history")
    public Mono<GetUsernameHistoryRespDto> getUsernameHistoryList() { // controller에서는 dto에 객체를 넣는 로직만 존재하도록 수정하기 -> service에서 로직 구현
        return usernameHistoryService.getUserHistoryList()
                .flatMap(userHistoryList -> {
                    List<Long> userIdList = userHistoryList.stream()
                            .map(UsernameHistoryVo::getUserId)
                            .collect(Collectors.toList()); // userId를 리스트가 아닌 Set으로 수정하기 -> 중복 제거

                    return usernameHistoryService.getIdUsernameMap(userIdList)
                            .map(idUsernameMap -> {
                                Iterator<UsernameHistoryVo> userHistoryIterator = userHistoryList.iterator();
                                List<UsernameHistoryVo> usernameHistoryList = new ArrayList<>();

                                while (userHistoryIterator.hasNext()) {
                                    UsernameHistoryVo usernameHistoryVo = userHistoryIterator.next();
                                    Long userId = usernameHistoryVo.getUserId();
                                    String username = idUsernameMap.get(userId);

                                    usernameHistoryVo.setUsername(username);
                                    usernameHistoryList.add(usernameHistoryVo);
                                }

                                GetUsernameHistoryRespDto getUsernameHistoryRespDto = new GetUsernameHistoryRespDto();
                                getUsernameHistoryRespDto.setUsernameHistoryList(usernameHistoryList);
                                return getUsernameHistoryRespDto;
                            })
                            .log("in");
                })
                .log("out");

// 변수들을 캡쳐하지 않고 사용하는 기존 코드 - userHistoryListMono가 두 번 subscribe 되어 DB의 UserHistory 테이블을 두 번 가져오는 문제점
//        Mono<List<UsernameHistoryVo>> userHistoryListMono = usernameHistoryService.getUserHistoryList();
//        Mono<Map<Long, String>> idUsernameMapMono = userHistoryListMono
//                .map(userHistoryList ->
//                                userHistoryList.stream()
//                                        .map(UsernameHistoryVo::getUserId)
//                                        .collect(Collectors.toList())
//                )
//                .flatMap(userIdList ->
//                                usernameHistoryService.getIdUsernameMap(userIdList)
//                );

//        return Mono.zip(userHistoryListMono, idUsernameMapMono) // userHistoryListMono가 두 번 subscribe 됨 -> 수정하기
//                .flatMap(tuple -> {
//                    List<UsernameHistoryVo> usernameHistoryList = new ArrayList<>();
//                    Iterator<UsernameHistoryVo> userHistoryIterator = tuple.getT1().iterator();
//                    Map<Long, String> idUsernameMap = tuple.getT2();
//
//                    while (userHistoryIterator.hasNext()) {
//                        UsernameHistoryVo usernameHistoryVo = userHistoryIterator.next();
//                        Long userId = usernameHistoryVo.getUserId();
//                        String username = idUsernameMap.get(userId);
//
//                        usernameHistoryVo.setUsername(username);
//                        usernameHistoryList.add(usernameHistoryVo);
//                    }
//
//                    GetUsernameHistoryRespDto getUsernameHistoryRespDto = new GetUsernameHistoryRespDto();
//                    getUsernameHistoryRespDto.setUsernameHistoryList(usernameHistoryList);
//                    return Mono.just(getUsernameHistoryRespDto);
//                })
//                .onErrorResume(e -> Mono.just(new GetUsernameHistoryRespDto(null, ResCode.UNKNOWN.value(), e.getLocalizedMessage())));
    }

}
