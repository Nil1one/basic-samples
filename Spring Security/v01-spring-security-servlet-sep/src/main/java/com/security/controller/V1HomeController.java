package com.security.controller;

import com.security.entity.R;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class V1HomeController {
    @PostMapping("/home")
    public R<String> home(){
        SecurityContext context = SecurityContextHolder.getContext();
        System.out.println(context);
        return R.success("Home");
    }
}
