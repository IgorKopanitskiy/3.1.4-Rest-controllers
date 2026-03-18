package com.kopanitskiy.security.controllers;

import com.kopanitskiy.security.services.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;


import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @GetMapping(value = "/user")
    public String getUser(ModelMap modelMap, Principal principal) {
        modelMap.addAttribute("user", userService.loadUserByUsername(principal.getName()));
        return "userView";
    }

}
