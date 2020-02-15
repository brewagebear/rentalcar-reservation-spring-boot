package com.toyota.rentalcar.dev.Board.controller;

import com.toyota.rentalcar.dev.Board.dto.MemberSaveRequestDto;
import com.toyota.rentalcar.dev.Board.dto.PasswordFormForMember;
import com.toyota.rentalcar.dev.Board.service.MemberService;
import com.toyota.rentalcar.dev.Board.service.SecurityService;
import com.toyota.rentalcar.dev.RentalCar.dto.payload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/auth")
public class MemberController {

    private final MemberService memberService;
    private final SecurityService securityService;

    @PostMapping(value = "/registration")
    public ResponseEntity<?> registration(@RequestBody MemberSaveRequestDto requestDto) {
        memberService.saveMember(requestDto);
        return ResponseEntity.accepted().body(new ApiResponse(true, "회원가입이 성공적으로 이루어졌습니다."));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody PasswordFormForMember passwordForm) {
        securityService.login(passwordForm.getUserEmail(), passwordForm.getUserPass());
        return ResponseEntity.ok().body(new ApiResponse(true, "로그인이 성공적으로 이루어졌습니다."));
    }

    @GetMapping("/accessDenied")
    public void accessDenied() {

    }

    @GetMapping("/logout")
    public void logout() {

    }
}

