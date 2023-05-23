package com.ssafy.fit.controller;

import com.ssafy.fit.model.dto.Comment;
import com.ssafy.fit.model.dto.Routine;
import com.ssafy.fit.model.service.RoutineService;
import com.ssafy.fit.util.JwtUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api-routine")
@Api(tags = "루틴 컨트롤러")
public class RoutineController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RoutineService routineService;

    //루틴 조회
    @GetMapping("/routine")
    public ResponseEntity<?> selectRoutine(@RequestHeader HttpHeaders header) throws Exception {
        String token = header.get("access-token").toString();
        int requestUserNo = jwtUtil.getUserNoAtToken(token);

        Routine myRoutine = routineService.selectRoutineByUserNo(requestUserNo);
        return new ResponseEntity<Routine>(myRoutine, HttpStatus.OK);
    }
    //루틴 작성
    @PostMapping("/routine")
    public ResponseEntity<String> insertRoutine(Routine routine,@RequestHeader HttpHeaders header) throws Exception {
        String token = header.get("access-token").toString();
        int requestUserNo = jwtUtil.getUserNoAtToken(token);

        routine.setUserNo(requestUserNo);

        routineService.insertRoutine(routine);
        return new ResponseEntity<String>("Insert Complete!", HttpStatus.CREATED);
    }

    //루틴 삭제
    @DeleteMapping("/routine")
    public ResponseEntity<String> deleteRoutine(@RequestHeader HttpHeaders header) throws Exception {
        String token = header.get("access-token").toString();
        int requestUserNo = jwtUtil.getUserNoAtToken(token);

        routineService.deleteRoutine(requestUserNo);
        return new ResponseEntity<String>("Delete Complete!", HttpStatus.OK);


    }

}