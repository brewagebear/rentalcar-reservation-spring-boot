package com.toyota.rentalcar.dev.commons.controller;

import com.toyota.rentalcar.dev.Board.model.Member;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorizationController {

    @PostMapping
    public void login(@RequestBody Member member){

    }

    @GetMapping("/accessDenied")
    public void accessDenied(){

    }

    @GetMapping("/logout")
    public void logout(){

    }

}
