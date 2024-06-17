package com.nhnacademy.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/auth/member")
public class TestController {

    @GetMapping("/{id}")
    public ResponseEntity<String> getMember(@PathVariable("id")String id){
        int age = new Random().ints(20,60 ).findFirst().getAsInt();
        return ResponseEntity.ok("" + age);
    }

}